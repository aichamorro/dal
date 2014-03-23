package com.aichamorro.dal.dataquery.result.iterators;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.aichamorro.dal.model.Model;

final public class ResultSetIterator<T extends Model> implements DataQueryResultIterator<T> {
	private ResultSet _data;
	private Class<? extends Model> _modelClass;
	
	public ResultSetIterator(ResultSet resultSet, Class<? extends Model> modelClass) {
		_data = resultSet;
		_modelClass = modelClass;
	}
	
	@SuppressWarnings("unchecked")
	public T next() {
		T result = null;
		
		try {
			if( _data.next() ) {
				result = (T) _modelClass.newInstance();
				
				Set<String> modelFields = result.modelFields();
				for( String modelField : modelFields ) {
					String type = result.getTypeFor(modelField).getSimpleName();
					
					if( type.equals("String") ) {
						result.set(modelField, _data.getString(modelField));
					}else if( type.equals("int") || type.equals("Integer") ) {
						result.set(modelField, _data.getInt(modelField));
					}else if( type.equals("float") || type.equals("Float") ) {
						result.set(modelField, _data.getFloat(modelField));
					}else if( type.equals("double") || type.equals("Double") ) {
						result.set(modelField, _data.getDouble(modelField));
					}else if( type.equals("long") || type.equals("Long") ) {
						result.set(modelField, _data.getLong(modelField));
					}else if( type.equals("short") || type.equals("Short") ) {
						result.set(modelField, _data.getShort(modelField));
					}else if( type.equals("boolean") || type.equals("Boolean") ) {
						result.set(modelField, _data.getBoolean(modelField));
					}else if( type.equals("byte") || type.equals("Byte") ) {
						result.set(modelField, _data.getByte(modelField));
					}else if( type.equals("char") || type.equals("Char") ){
						try {
							Reader reader = _data.getCharacterStream(modelField);
							assert (null != reader) : "There is no char data in the result set obtained";
							
							if( null != reader ) {
								result.set(modelField, (char)reader.read());
								reader.close();
							}
						} catch (IOException e) {
							assert false : "Exception: " + e.toString();
						}
					}else{
						result.set(modelField, _data.getObject(modelField));
					}
				}
			}
		} catch (InstantiationException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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
}