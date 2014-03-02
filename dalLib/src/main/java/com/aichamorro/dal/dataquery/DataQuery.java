package com.aichamorro.dal.dataquery;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.aichamorro.dal.dataquery.annotations.ModelName;
import com.aichamorro.dal.dataquery.annotations.ModelField;

public class DataQuery {
	public enum QueryType {
		SELECT,
		INSERT,
		UPDATE,
		DELETE;
	};
	
	QueryType _queryType;
	Queryable<?> _payload;
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
		
		for( Field f : _modelClass.getDeclaredFields() ) {
			if( f.isAnnotationPresent(ModelField.class) ) {
				String value = ((ModelField)f.getAnnotation(ModelField.class)).value();
				
				boolean isAccessible = f.isAccessible();
				f.setAccessible(true);
				
				try {
					String fieldName = (value != null && !value.isEmpty()) ? value : f.getName();
					
					result.put(fieldName, f.get(_payload).toString());
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
		
		ModelName modelName = ((ModelName)_modelClass.getAnnotation(ModelName.class));
		visitor.setModel( (null != modelName && modelName.value().length() > 0) ? modelName.value() : _modelClass.getSimpleName());			
		
		if( null != _payload ) {
			visitor.setPayload(payloadAsHashMap());
		}
		if( _where != null ) {
			visitor.addFilter(_where.iterator());		
		}
	}
}
