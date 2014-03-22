package com.aichamorro.dal;

import com.aichamorro.dal.dataquery.result.DataQueryResult;
import com.aichamorro.dal.dataquery.result.DataQueryResultIterator;
import com.aichamorro.dal.model.Model;

class ErrorDataQueryResult implements DataQueryResult {
	private String _errorMessage;

	public ErrorDataQueryResult(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public boolean isError() {
		return true;
	}

	public Object getError() {
		return _errorMessage;
	}

	public <T extends Model> DataQueryResultIterator<T> iterator(Class<T> forClass) {
		assert false : "Don't try to iterate over an ErrorDataQueryResult!";

		return (DataQueryResultIterator<T>) EmptyDataQueryResultIterator.getInstance();
	}
}
