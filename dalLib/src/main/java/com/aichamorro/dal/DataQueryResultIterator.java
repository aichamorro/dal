package com.aichamorro.dal;

public interface DataQueryResultIterator<T extends Model> {
	public T next();
}
