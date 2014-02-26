package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQueryStatement;
import com.aichamorro.dal.dataquery.DataQueryStatement.DataQueryStatementType;
import com.aichamorro.dal.dataquery.DataQueryStatement.Iterator;

public class SqlDataQueryStatementAdapter {
	private static final String AND_STRING = " AND ";
	private static final String OR_STRING = " OR ";
	private static final String NOT_STRING = "NOT ";
	
	public String statementAdapter(DataQueryStatement stmnt) {
		switch( stmnt.getType() ) {
		case AND:
			return composeString("", stmnt, SqlStatements.AND);
		case OR:
			return composeString("", stmnt, SqlStatements.OR);
		case NOT:
			return composeString(SqlStatements.NOT, stmnt, "");
		case UNTYPED:
		default:
			return stmnt.toString();
		}
	}

	private String composeString(String prefix, DataQueryStatement stmnt, String suffix ) {
		assert null != prefix : "The prefix string must be not null, use an empty string instead";
		assert null != suffix : "The postfix string must be not null, use an empty string instead";
		
		String result = "(";

		Iterator iterator = stmnt.iterator();
		while(iterator.hasNext()) {
			DataQueryStatement subStatement = iterator.next();
			
			result += prefix + statementAdapter(subStatement) + suffix;
		}
		
		return result.substring(0, result.length() - suffix.length()) + ")";
	}
}
