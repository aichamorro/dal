package com.aichamorro.dal.dataquery;

import static com.aichamorro.dal.dataquery.DataQueryStatementFactory.*;

public class DataQuery {
	public enum QueryType {
		SELECT,
		INSERT,
		UPDATE,
		DELETE;
	};
	
	public enum FilterType {
		AND,
		OR,
		WHERE
	}

	QueryType _queryType;
	Queryable _payload;
	DataQueryStatement _where;
	@SuppressWarnings("rawtypes")
	Class _modelClass;
	
	@SuppressWarnings("rawtypes")
	DataQuery(QueryType queryType, Class modelClass, Queryable object, DataQueryStatement where) {
		_queryType = queryType;
		_payload = object;
		_modelClass = modelClass;
		_where = where;
	}

	public QueryType getType() {
		return _queryType;
	}
	
	@SuppressWarnings("unchecked")
	public void visit(DataQueryVisitor visitor) {
		visitor.setType(_queryType);
		visitor.setModel(_modelClass);
		visitor.setPayload(_payload);
		
		if( _where != null ) {
			visitor.addFilter(_where.iterator());		
		}
	}
}
