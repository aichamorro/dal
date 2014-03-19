package com.aichamorro.dal.dataquery.result;

import com.aichamorro.dal.model.Model;

public interface DataQueryResultIterator<T extends Model> {
	public T next();
}
