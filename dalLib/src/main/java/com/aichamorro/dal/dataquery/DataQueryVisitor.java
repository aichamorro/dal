package com.aichamorro.dal.dataquery;

public interface DataQueryVisitor extends DataQueryStatementVisitor {
	void setType(DataQuery.QueryType queryType);
	void setModel(Class className);
	void setPayload(Queryable payload);
	public void addFilter(DataQueryStatement.Iterator iterator);
}
