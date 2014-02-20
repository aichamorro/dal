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
	String[] _selectFields;

	private DataQuery(int queryType) {
		_queryType = queryType;
	}

	public static DataQuery select(String[] fields) {
		assert null != fields : "fields must not be null, if you don't want to filter the fields use selectAll";

		_selectFields = fields;
		DataQuery result = new DataQuery(QUERY_TYPE_SELECT);
			result.setSelectFields( fields );

		return result;
	}

	public static DataQuery selectAll() {
		DataQuery result = new DataQuery(QUERY_TYPE_SELECT);

		return result;
	}

	public static DataQuery insert(Object object) {
		DataQuery result = new DataQuery(QUERY_TYPE_INSERT);

		return result;
	}

	public static DataQuery delete(Object object) {
		DataQuery result = new DataQuery(QUERY_TYPE_DELETE);

		return result;
	}

	public static DataQuery update(Object object) {
		DataQuery result = new DataQuery(QUERY_TYPE_UPDATE);

		return result;
	}

	public int getType() {
		return _queryType;
	}

	private void setSelectFields(String[] fields) {
		assert null != fields : "fields must not be null, if you don't want to filter the fields use selectAll";
		assert fields.length > 0 : "fields must contain one or more elements";

		_selectFields = fields;
	}

}
