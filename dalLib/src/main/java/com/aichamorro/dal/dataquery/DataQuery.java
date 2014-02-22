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
	DataQuery(QueryType queryType, Class modelClass) {
		this(queryType, modelClass, null);
	}
	
	@SuppressWarnings("rawtypes")
	DataQuery(QueryType queryType, Class modelClass, Queryable object) {
		_queryType = queryType;
		_payload = object;
		_modelClass = modelClass;
		_where = null;
	}

	public QueryType getType() {
		return _queryType;
	}

	public DataQuery where(String statement) {
		_where = whereStatement(statement);
		
		return this;
	}
	
	private void where(DataQueryStatement statement) {
		_where = statement;
	}
	
//	public DataQuery and(String string) {
//		if( _where != null ) {
//			_where = whereStatement(andStatement(untypedStatement(_where.getStatement()), untypedStatement(string)));
//		}
//		
//		return this;
//	}
	
//	public DataQuery and(String andStatement) {
//		addFilter(FilterType.AND, andStatement);
//
//		return this;
//	}
//	
//	public DataQuery or(String orStatement) {
//		addFilter(FilterType.OR, orStatement);
//		
//		return this;
//	}
	
//	private void addFilter(FilterType filter, String statement) {
//		if( filter != FilterType.WHERE ) {
//			assert _where != null : "To add an AND or OR statement first you need to add a WHERE condition";
//			
//			if( null == _where ) {
//				throw new NullPointerException("To add an AND or OR statement first you need to add a WHERE condition");
//			}
//			
//		}else{
//			_where = "";
//		}
//		
//		_where += " " + filter.name() + " " + statement;
//	}
	
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
