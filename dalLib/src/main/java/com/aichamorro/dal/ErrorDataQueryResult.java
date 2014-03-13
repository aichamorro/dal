package com.aichamorro.dal;

import com.aichamorro.dal.dataquery.DataQueryResult;

class ErrorDataQueryResult implements DataQueryResult {
	private String _errorMessage;

	public ErrorDataQueryResult(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public boolean isErrorResult() {
		return true;
	}

	public Object getError() {
		return _errorMessage;
	}

	public DataQueryResultIterator iterator(Class<? extends Model> forClass) {
		assert false : "Don't try to iterate over an ErrorDataQueryResult!";
	
		return new DataQueryResultIterator() {
			public Object next() {
				return null;
			}
		};
	}
}
