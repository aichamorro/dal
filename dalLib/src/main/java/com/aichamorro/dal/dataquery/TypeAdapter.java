package com.aichamorro.dal.dataquery;

public interface TypeAdapter {
	public Object getValueFor(Class<?> type, Object value);
}
