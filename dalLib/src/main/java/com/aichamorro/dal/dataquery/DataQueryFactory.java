package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.model.Model;

/**
 * Creates {@link DataQuery} objects.
 * @author achamorro
 *
 */
public class DataQueryFactory {
	private DataQuery.QueryType _type;
	private Class<? extends Model> _objectClass;
	private Model _payload;
	private DataQueryFilter _where;
	
	private DataQueryFactory(DataQuery.QueryType type, Class<? extends Model> objectClass) {
		this(type, objectClass, null);
	}
	
	private DataQueryFactory(DataQuery.QueryType type, Class<? extends Model> objectClass, Model payload) {
		_type = type;
		_objectClass = objectClass;
		_payload = payload;
	}
	
	public DataQuery createQuery() {
		return (_payload != null) ? 
				new DataQuery(_type, _payload, _where) : 
				new DataQuery(_type, _objectClass, _where);
	}
	
	public DataQueryFactory where(String statement) {
		_where = DataQueryFilterFactory.where(statement);
		
		return this;
	}
	
	public DataQueryFactory where(DataQueryFilter statement) {
		_where = DataQueryFilterFactory.where(statement);
		
		return this;
	}
	
	public static DataQueryFactory select(Class<? extends Model> objectClass) {
		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.SELECT, objectClass);
		
		return result;
	}

	public static DataQueryFactory insert(Model object) {
		assert null != object : "You cannot insert a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.INSERT, object.getClass(), object);
		
		return result;
	}

	public static DataQueryFactory delete(Model object) {
		assert null != object : "You cannot delete a null object";
		assert null != object.getIdField() : "You cannot delete an object with a null id";
		
		if( null == object ) { throw new NullPointerException(); }

		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.DELETE, object.getClass(), object).where(object.getModelIdName() + "='" + object.getModelIdValue().toString() + "'");

		return result;
	}
	
	public static DataQueryFactory update(Model object) {
		assert null != object : "You cannot update a null object";
		assert null != object.getModelIdValue() : "You cannot update a Model with a null id";
		
		if( null == object ) { throw new NullPointerException(); }
				
		DataQueryFactory result = new DataQueryFactory(DataQuery.QueryType.UPDATE, object.getClass(), object).where(object.getModelIdName() + "='" +  object.getModelIdValue().toString() + "'");

		return result;
	}
}
