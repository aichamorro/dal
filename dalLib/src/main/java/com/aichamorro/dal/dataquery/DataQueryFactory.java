package com.aichamorro.dal.dataquery;

public class DataQueryFactory {
	public static DataQuery select() {
		DataQuery result = new DataQuery(DataQuery.QUERY_TYPE_SELECT);

		return result;
	}

	public static DataQuery insert(Object object) {
		assert null != object : "You cannot insert a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QUERY_TYPE_INSERT);

		return result;
	}

	public static DataQuery delete(Object object) {
		assert null != object : "You cannot delete a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QUERY_TYPE_DELETE);

		return result;
	}

	public static DataQuery update(Object object) {
		assert null != object : "You cannot update a null object";

		if( null == object ) { throw new NullPointerException(); }

		DataQuery result = new DataQuery(DataQuery.QUERY_TYPE_UPDATE);

		return result;
	}
}
