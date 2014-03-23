package com.aichamorro.dal.dataquery.result.iterators;

import com.aichamorro.dal.model.Model;

public interface DataQueryResultIterator<T extends Model> {
	public T next();
}
