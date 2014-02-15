package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQuery;

public interface DataQueryAdapter<E> {
	public E objectForQuery(DataQuery query);
}
