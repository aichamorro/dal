package com.aichamorro.dal.dataquery.result;

import java.sql.ResultSet;

import com.aichamorro.dal.dataquery.result.iterators.DataQueryResultIterator;
import com.aichamorro.dal.dataquery.result.iterators.ResultSetIterator;
import com.aichamorro.dal.model.Model;

final public class ResultSetDataQueryResult implements DataQueryResult {
	private ResultSet _resultSet;

	public ResultSetDataQueryResult(ResultSet resultSet) {
		_resultSet = resultSet;
	}

	public boolean isError() {
		return false;
	}

	public Object getError() {
		assert true == isError() : "This is not an error. Hence, you cannot retrieve the error object";
		
		return null;
	}

	public <T extends Model> DataQueryResultIterator<T> iterator(Class<T> forClass) {
		return new ResultSetIterator<T>(_resultSet, forClass);
	}
}
