package com.aichamorro.dal;

import java.sql.ResultSet;

import com.aichamorro.dal.dataquery.DataQueryResult;

class ResultSetDataQueryResult implements DataQueryResult {
	private ResultSet _resultSet;

	public ResultSetDataQueryResult(ResultSet resultSet) {
		_resultSet = resultSet;
	}

	public boolean isErrorResult() {
		return false;
	}

	public Object getError() {
		assert true == isErrorResult() : "This is not an error. Hence, you cannot retrieve the error object";
		
		return null;
	}

	public DataQueryResultIterator iterator(Class<? extends Model> forClass) {
		return new ResultSetIterator(_resultSet, forClass);
	}
}
