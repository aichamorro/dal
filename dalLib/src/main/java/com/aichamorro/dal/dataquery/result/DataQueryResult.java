package com.aichamorro.dal.dataquery.result;

import com.aichamorro.dal.model.Model;

public interface DataQueryResult {
	boolean isErrorResult();
	Object getError();
	DataQueryResultIterator iterator(Class<? extends Model> forClass);
}
