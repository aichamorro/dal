package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQueryResult;
import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;
import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;

public class SqlConnector {
	private final String dbProtocol = "jdbc:mysql://";
	private String _url;
	private String _username;
	private String _password;
	private Connection _connection;

	public SqlConnector(String host, String database, String username) {
		this(host, database, username, "");
	}
	
	public SqlConnector(String host, String database, String username, String password) {
		assert null != host && !host.isEmpty() : "Host cannot be neither null nor empty";
		assert null != database && !database.isEmpty() : "database cannot be neither null nor empty";
		assert null != username && !username.isEmpty() : "username cannot be neither null nor empty";

		StringBuilder sb = new StringBuilder(dbProtocol);
			sb.append(host);
			sb.append("/");
			sb.append(database);
			
		_url = sb.toString();
		
		// TODO I am a bad guy, I know. I must make this more secure and I will, I promise
		_username = username;
		_password = password;
		
		try {
			_connection = DriverManager.getConnection(_url, _username, _password);
		} catch (SQLException e) {
			// TODO call a delegate or throw an exception perhaps?
			assert false : "Do something with the error";
		}
	}

	public String getUrl() {
		return _url;
	}

	public DataQueryResult executeQuery(DataQuery dataQuery) {
		assert _connection != null : "The connection has not been initialized!!!";
		
		String sqlQuery = new SqlDataQueryAdapter().objectForQuery(dataQuery);
		try {
			ResultSet resultSet = _connection.createStatement().executeQuery(sqlQuery);
			
			return new ResultSetDataQueryResult(resultSet);
		} catch (SQLException e) {
			assert false : "Exception occurred: " + e.toString();
		
			return new ErrorDataQueryResultImpl(e.toString());
		}
	}

	public void close() throws SQLException {
		_connection.close();
	}
}

class ResultSetDataQueryResult implements DataQueryResult {

	private ResultSet _resultSet;

	public ResultSetDataQueryResult(ResultSet resultSet) {
		_resultSet = resultSet;
	}

	public boolean isError() {
		return false;
	}

	public Object getError() {
		assert true == isError() : "This is not an error. Hence, you cannot retrieve the error object";
		
		return null;
	}

	public Iterator<?> iterator(Class<?> forClass) {
		return new ResultSetIteratorImpl(_resultSet, forClass);
	}
}

interface ResultSetIterator extends Iterator<Object>{}
interface ErrorDataQueryResult extends DataQueryResult {}

class ErrorDataQueryResultImpl implements ErrorDataQueryResult {
	private String _errorMessage;

	public ErrorDataQueryResultImpl(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public boolean isError() {
		return true;
	}

	public Object getError() {
		return _errorMessage;
	}

	public Iterator<?> iterator(Class<?> forClass) {
		assert false : "Don't try to iterate over an ErrorDataQueryResult!";
	
		return null;
	}
}

class ResultSetIteratorImpl implements ResultSetIterator {

	private ResultSet _data;
	private HashMap<String, String> _fields;
	private HashMap<String, String> _types;
	private Class<?> _modelClass;
	
	public ResultSetIteratorImpl(ResultSet resultSet, Class<?> modelClass) {
		_data = resultSet;
		_fields = new HashMap<String, String>();
		_modelClass = modelClass;
		
		boolean idFieldSaved = false;
		for (Field f : modelClass.getFields()) {
			if( f.isAnnotationPresent(ModelField.class) ) {
				String annotationValue = f.getAnnotation(ModelField.class).value();
				String name = (null == annotationValue || annotationValue.isEmpty()) ? f.getName() : annotationValue;
				String type = f.getType().getSimpleName();
				
				_fields.put(name, f.getName());
				_types.put(name, type);
			}else if( !idFieldSaved && f.isAnnotationPresent(ModelId.class) ) {
				String annotationValue = f.getAnnotation(ModelId.class).value();
				String name = (null == annotationValue || annotationValue.isEmpty()) ? f.getName() : annotationValue;
				String type = f.getType().getSimpleName();
				
				_fields.put(name, type);
			}
		}
		
		assert !_fields.isEmpty() : "Seems the modelClass provided does not have either @ModelField nor @ModelId annotations";
	}
	
	public boolean hasNext() {
		return true;
	}

	public Object next() {
		Object result = null;
		
		try {
			result = _modelClass.newInstance();
			
			for( String modelField : _fields.keySet() ) {
				Field f = _modelClass.getField(_fields.get(modelField));
				String type = _types.get(modelField);
				
				if( type.equals("String") ) {
					f.set(result, _data.getString(modelField));
				}else if( type.equals("int") || type.equals("Integer") ) {
					f.set(result, _data.getInt(modelField));
				}else if( type.equals("float") || type.equals("Float") ) {
					f.set(result, _data.getFloat(modelField));
				}else if( type.equals("double") || type.equals("Double") ) {
					f.set(result, _data.getDouble(modelField));
				}else {
					f.set(result, _data.getObject(modelField));
				}
			}
		} catch (InstantiationException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (SecurityException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (SQLException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove() {
	}
	
}
