package com.aichamorro.dal.dataquery;

import java.sql.ResultSet;

import com.aichamorro.dal.dataquery.adapters.DataQueryResultImpl;

public class DataQueryResultFactory {
	static public DataQueryResult wrapperFor(ResultSet resultSet) {
		return null;
	}

	public static DataQueryResult emptyResult() {
		return new DataQueryResultImpl();
	}
}
