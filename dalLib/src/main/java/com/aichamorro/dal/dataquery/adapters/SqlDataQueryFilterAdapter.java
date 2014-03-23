package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQueryFilter;
import com.aichamorro.dal.dataquery.DataQueryFilter.Iterator;
import com.aichamorro.dal.dataquery.sql.SqlStatements;

class SqlDataQueryFilterAdapter {
	public String statementAdapter(DataQueryFilter stmnt) {
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

	private String composeString(String prefix, DataQueryFilter stmnt, String suffix ) {
		assert null != prefix : "The prefix string must be not null, use an empty string instead";
		assert null != suffix : "The postfix string must be not null, use an empty string instead";
		
		String result = "(";

		Iterator iterator = stmnt.iterator();
		while(iterator.hasNext()) {
			DataQueryFilter subStatement = iterator.next();
			
			result += prefix + statementAdapter(subStatement) + suffix;
		}
		
		return result.substring(0, result.length() - suffix.length()) + ")";
	}
}
