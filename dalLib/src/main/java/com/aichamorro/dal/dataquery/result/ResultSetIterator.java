package com.aichamorro.dal.dataquery.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.aichamorro.dal.model.Model;

class ResultSetIterator implements DataQueryResultIterator {
	private ResultSet _data;
	private Class<? extends Model> _modelClass;
	
	public ResultSetIterator(ResultSet resultSet, Class<? extends Model> modelClass) {
		_data = resultSet;
		_modelClass = modelClass;
	}
	
	public Object next() {
		Model result = null;
		
		try {
			if( _data.next() ) {
				result = (Model) _modelClass.newInstance();
				
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
					}else {
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