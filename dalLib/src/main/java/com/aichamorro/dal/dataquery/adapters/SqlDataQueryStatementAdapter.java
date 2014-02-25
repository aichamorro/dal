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
			return andAdapter(stmnt);
		case OR:
			return orAdapter(stmnt);
		case NOT:
			return notAdapter(stmnt);
		case UNTYPED:
			return stmnt.toString();
		default:
			assert false : "DataQueryStatement.getType() not recognized";
		}
		
		return null;
	}
	
	private String notAdapter(DataQueryStatement stmnt) {
		assert stmnt.getType() == DataQueryStatementType.NOT : "The statement is not an NOT statement";
		
		String result = "(";
		Iterator iterator = stmnt.iterator();
		while(iterator.hasNext()) {
			DataQueryStatement subStatement = iterator.next();
			
			result += NOT_STRING + statementAdapter(subStatement) + " ";
		}
		
		return result.trim() + ")";
	}
	
	private String andAdapter(DataQueryStatement stmnt) {
		assert stmnt.getType() == DataQueryStatementType.AND : "The statement is not an AND statement";

		String result = "(";
		Iterator iterator = stmnt.iterator();
		while(iterator.hasNext()) {
			DataQueryStatement subStmnt = iterator.next();
			
			result += statementAdapter(subStmnt) + AND_STRING;
		}
		
		return result.substring(0, result.length() - AND_STRING.length()) + ")";
	}
	
	private String orAdapter(DataQueryStatement stmnt) {
		assert stmnt.getType() == DataQueryStatementType.OR : "The statement is not an OR statement";

		String result = "(";
		Iterator iterator = stmnt.iterator();
		while(iterator.hasNext()) {
			DataQueryStatement subStmnt = iterator.next();
			
			result += statementAdapter(subStmnt) + OR_STRING;
		}
		
		return result.substring(0, result.length() - OR_STRING.length()) + ")";
	}
}