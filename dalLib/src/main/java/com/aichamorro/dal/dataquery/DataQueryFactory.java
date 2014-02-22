package com.aichamorro.dal.dataquery;

public class DataQueryFactory {
	public static DataQuery select(Class objectClass) {
		DataQuery result = new DataQuery(DataQuery.QueryType.SELECT, objectClass);

		return result;
	}

	public static DataQuery insert(Queryable object) {
		assert null != object : "You cannot insert a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QueryType.INSERT, object.getClass(), object);

		return result;
	}

	public static DataQuery delete(Queryable object) {
		assert null != object : "You cannot delete a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QueryType.DELETE, object.getClass(), object);

		return result;
	}

	public static DataQuery update(Queryable object) {
		assert null != object : "You cannot update a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QueryType.UPDATE, object.getClass(), object);

		return result;
	}
}
