package com.aichamorro.dal.dataquery;

public class DataQueryFactory {
	public static DataQuery select() {
		DataQuery result = new DataQuery(DataQuery.QueryType.SELECT);

		return result;
	}

	public static DataQuery insert(Queryable object) {
		assert null != object : "You cannot insert a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QueryType.INSERT, object);

		return result;
	}

	public static DataQuery delete(Queryable object) {
		assert null != object : "You cannot delete a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QueryType.DELETE, object);

		return result;
	}

	public static DataQuery update(Queryable object) {
		assert null != object : "You cannot update a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QueryType.UPDATE, object);

		return result;
	}
}
