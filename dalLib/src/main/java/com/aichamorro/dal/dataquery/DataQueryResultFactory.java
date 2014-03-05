package com.aichamorro.dal.dataquery;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aichamorro.dal.dataquery.adapters.DataQueryResultImpl;

public class DataQueryResultFactory {
	static public DataQueryResult wrapperFor(ResultSet resultSet) {
		try {
			return new ResultSetDataQueryResult(resultSet);
		} catch (SQLException e) {
			assert false : "Exception " + e.toString();

		// TODO Error handling
			return new FailedDataQueryResult(e);
		}
	}

	public static DataQueryResult emptyResult() {
		return new DataQueryResultImpl();
	}
}
