package com.aichamorro.dal.dataquery;

import java.lang.reflect.Field;

import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;

public class DataQueryFactory {
	private DataQuery.QueryType _type;
	private Class<?> _objectClass;
	private Queryable<?> _payload;
	private DataQueryStatement _where;
	
	private DataQueryFactory(DataQuery.QueryType type, Class<?> objectClass) {
		this(type, objectClass, null);
	}
	
	private DataQueryFactory(DataQuery.QueryType type, Class<?> objectClass, Queryable<?> payload) {
		_type = type;
		_objectClass = objectClass;
		_payload = payload;
	}
	
	public DataQuery createQuery() {
		return new DataQuery(_type, _objectClass, _payload, _where);
	}
	
	public DataQueryFactory where(String statement) {
		_where = DataQueryStatementFactory.where(statement);
		
		return this;
	}
	
	public DataQueryFactory where(DataQueryStatement statement) {
		_where = DataQueryStatementFactory.where(statement);
		
		return this;
	}
	
	public static DataQueryFactory select(Class<?> objectClass) {
		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.SELECT, objectClass);
		
		return result;
	}

	public static DataQueryFactory insert(Queryable<?> object) {
		assert null != object : "You cannot insert a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.INSERT, object.getClass(), object);
		
		return result;
	}

	public static DataQueryFactory delete(Queryable<?> object) {
		assert null != object : "You cannot delete a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.DELETE, object.getClass(), object).where(getIdFieldNameFor(object) + "='" + object.getId() + "'");

		return result;
	}
	
	private static String getIdFieldNameFor(Queryable<?> object) {
		for( Field f : object.getClass().getDeclaredFields() ) {
			if( f.isAnnotationPresent(ModelId.class) ) {
				ModelId annotation = (ModelId)f.getAnnotation(ModelId.class);
				
				return "".equals(annotation.value()) ? f.getName() : annotation.value();
			}
		}

		return "id";
	}

	public static DataQueryFactory update(Queryable<?> object) {
		assert null != object : "You cannot update a null object";

		if( null == object ) { throw new NullPointerException(); }
				
		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.UPDATE, object.getClass(), object).where(getIdFieldNameFor(object) + "='" +  object.getId() + "'");

		return result;
	}
}
