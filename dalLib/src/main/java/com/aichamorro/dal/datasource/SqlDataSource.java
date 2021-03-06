package com.aichamorro.dal.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;
import com.aichamorro.dal.dataquery.result.DataQueryResult;
import com.aichamorro.dal.dataquery.result.ErrorDataQueryResult;
import com.aichamorro.dal.dataquery.result.ResultSetDataQueryResult;

public class SqlDataSource implements DataSource {
	private final String dbProtocol = "jdbc:mysql://";
	private String _url;
	private String _username;
	private String _password;
	private Connection _connection;
	private SQLException _error;

	public SqlDataSource(String host, String database, String username, SqlDataQueryAdapter queryAdapter) {
		this(host, database, username, "", queryAdapter);
	}
	
	public SqlDataSource(String host, String database, String username, String password, SqlDataQueryAdapter queryAdapter) {
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
			_error = e;
		}
	}

	public String getUrl() {
		return _url;
	}

	public DataQueryResult executeQuery(DataQuery dataQuery) {
		assert _connection != null : "The connection has not been initialized!!!";
		if( null != _error ) {
			return new ErrorDataQueryResult(_error.toString());
		}
		
		String sqlQuery = new SqlDataQueryAdapter().objectForQuery(dataQuery);
		try {
			Statement statement = _connection.createStatement();
			DataQueryResult result = null;
			
			if( statement.execute(sqlQuery) ) {
				
				result = new ResultSetDataQueryResult(statement.getResultSet());
			}else{
				statement.close();
				
				result = DataQueryResult.EMPTY_SUCCESS;
			}
			
			return result;
		} catch (SQLException e) {
			assert false : "Exception occurred: " + e.toString();
		
			return new ErrorDataQueryResult(e.toString());
		}
	}

	public void close() throws SQLException {
		_connection.close();
	}
}