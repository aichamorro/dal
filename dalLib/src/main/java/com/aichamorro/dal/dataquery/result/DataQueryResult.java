package com.aichamorro.dal.dataquery.result;

import com.aichamorro.dal.model.Model;

public interface DataQueryResult {
	boolean isError();
	Object getError();
	<T extends Model>DataQueryResultIterator<T> iterator(Class<T> forClass);
}
