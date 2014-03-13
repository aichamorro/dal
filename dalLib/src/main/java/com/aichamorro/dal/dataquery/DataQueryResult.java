package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.DataQueryResultIterator;

public interface DataQueryResult {
	boolean isErrorResult();
	Object getError();
	DataQueryResultIterator<?> iterator(Class<?> forClass);
}
