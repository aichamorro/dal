package com.aichamorro.dal.dataquery;

import java.util.HashMap;

public interface DataQueryVisitor extends DataQueryStatementVisitor {
	void setType(DataQuery.QueryType queryType);
	void setModel(Class className);
	void setPayload(HashMap<String, String> payload);
	public void addFilter(DataQueryStatement.Iterator iterator);
}
