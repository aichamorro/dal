package com.aichamorro.dal.dataquery;

public interface DataQueryAdapter<E> {
	public E objectForQuery(DataQuery query);
}
