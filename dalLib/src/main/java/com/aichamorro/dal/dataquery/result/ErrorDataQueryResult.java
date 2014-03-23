package com.aichamorro.dal.dataquery.result;

import com.aichamorro.dal.dataquery.result.iterators.DataQueryResultIterator;
import com.aichamorro.dal.dataquery.result.iterators.EmptyDataQueryResultIterator;
import com.aichamorro.dal.model.Model;

public final class ErrorDataQueryResult implements DataQueryResult {
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
