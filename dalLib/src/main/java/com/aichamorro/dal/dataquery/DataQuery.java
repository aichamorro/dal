package com.aichamorro.dal.dataquery;

import java.util.HashMap;
import java.util.ArrayList;

public class DataQuery {
	public enum QueryType {
		SELECT,
		INSERT,
		UPDATE,
		DELETE
	}

	QueryType _queryType;
	Queryable _payload;

	DataQuery(QueryType queryType) {
		this(queryType, null);
	}

	DataQuery(QueryType queryType, Queryable object) {
		_queryType = queryType;
		_payload = object;
	}

	public QueryType getType() {
		return _queryType;
	}
}
