package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.DataQueryResultIterator;
import com.aichamorro.dal.Model;

public interface DataQueryResult {
	boolean isErrorResult();
	Object getError();
	DataQueryResultIterator iterator(Class<? extends Model> forClass);
}
