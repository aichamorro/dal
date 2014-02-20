package com.aichamorro.dal.dataquery;

import java.util.HashMap;
import java.util.ArrayList;

public class DataQuery {
	public static final int QUERY_TYPE_SELECT = 504748;
	public static final int QUERY_TYPE_INSERT = 504749;
	public static final int QUERY_TYPE_UPDATE = 504750;
	public static final int QUERY_TYPE_DELETE = 504751;

	int _queryType;

	DataQuery(int queryType) {
		_queryType = queryType;
	}

	public int getType() {
		return _queryType;
	}
}
