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

	public DataQueryResultIterator<?> iterator(Class<?> forClass) {
		assert false : "Don't try to iterate over an ErrorDataQueryResult!";
	
		return new DataQueryResultIterator<NullModel>() {
			public NullModel next() {
				return null;
			}
		};
	}
}

abstract class NullModel implements Model {}