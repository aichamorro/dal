package com.aichamorro.dal.dataquery;

import java.util.HashMap;
import java.util.ArrayList;

public class DataQuery {
	public static final String ALL_FIELDS = "*";

	public static final int QUERY_TYPE_SELECT = 504748;
	public static final int QUERY_TYPE_INSERT = 504749;
	public static final int QUERY_TYPE_UPDATE = 504750;
	public static final int QUERY_TYPE_DELETE = 504751;

	int _queryType;

	private DataQuery(int queryType) {
		_queryType = queryType;
	}

	public int getType() {
		return _queryType;
	}

	public static DataQuery select(String[] fields) {
		assert null != fields : "fields must not be null, if you don't want to filter the fields use selectAll";
		DataQuery result = new DataQuery(QUERY_TYPE_SELECT);

		return result;
	}

	public static DataQuery selectAll() {
		DataQuery result = new DataQuery(QUERY_TYPE_SELECT);

		return result;
	}

	public static DataQuery insert() {
		DataQuery result = new DataQuery(QUERY_TYPE_INSERT);

		return result;
	}

	public static DataQuery delete() {
		DataQuery result = new DataQuery(QUERY_TYPE_DELETE);

		return result;
	}

	public static DataQuery update() {
		DataQuery result = new DataQuery(QUERY_TYPE_UPDATE);

		return result;
	}

}
