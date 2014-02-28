package com.aichamorro.dal.dataquery;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DataQuery {
	public enum QueryType {
		SELECT,
		INSERT,
		UPDATE,
		DELETE;
	};
	
	QueryType _queryType;
	Queryable _payload;
	DataQueryStatement _where;
	@SuppressWarnings("rawtypes")
	Class _modelClass;
	
	@SuppressWarnings("rawtypes")
	DataQuery(QueryType queryType, Class modelClass, Queryable object, DataQueryStatement where) {
		_queryType = queryType;
		_payload = object;
		_modelClass = modelClass;
		_where = where;
	}

	public QueryType getType() {
		return _queryType;
	}
	
	public HashMap<String, String> payloadAsHashMap() {
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		
		int i=0;
		for( Field f : _modelClass.getDeclaredFields() ) {
			if( f.isAnnotationPresent(DataField.class) ) {
				boolean isAccessible = f.isAccessible();
				f.setAccessible(true);
				
				try {
					result.put(f.getAnnotation(DataField.class).value(), f.get(_payload).toString());
				}catch(Exception ex) {
					assert false : "Exception occured: " + f.getName() + " " + ex.toString();
				}
				
				f.setAccessible(isAccessible);	
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void visit(DataQueryVisitor visitor) {
		visitor.setType(_queryType);
		visitor.setModel(_modelClass);
		
		if( null != _payload ) {
			visitor.setPayload(payloadAsHashMap());
		}
		if( _where != null ) {
			visitor.addFilter(_where.iterator());		
		}
	}
}
