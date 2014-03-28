package com.aichamorro.dal.dataquery;

import java.util.HashMap;

public interface DataQueryVisitor extends DataQueryStatementVisitor, TypeAdapter {
	void setType(DataQuery.QueryType queryType);
	void setModel(String modelName);
	void setPayload(HashMap<String, Object> payload);
	public void addFilter(DataQueryFilter.Iterator iterator);
}
