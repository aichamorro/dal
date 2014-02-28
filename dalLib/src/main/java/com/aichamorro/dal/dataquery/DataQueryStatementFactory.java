package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.dataquery.DataQueryStatement.DataQueryStatementType;

public class DataQueryStatementFactory {
	static DataQueryStatement where(String statement) {
		return new SingleDataQueryStatement(DataQueryStatementType.WHERE, statement);
	}

	static DataQueryStatement where(DataQueryStatement statement) {
		return new ComposedDataQueryStatement(DataQueryStatementType.WHERE, statement);
	}
	
	public static DataQueryStatement statement(String statement) {
		return new SingleDataQueryStatement(DataQueryStatementType.UNTYPED, statement);
	}

	public static DataQueryStatement and(DataQueryStatement... statements) {
		return new ComposedDataQueryStatement(DataQueryStatementType.AND, statements);
	}

	public static DataQueryStatement and(String... statements) {
		DataQueryStatement[] dqStatements = new DataQueryStatement[statements.length];
		
		for(int i=0; i<statements.length; i++) {
			dqStatements[i] = new SingleDataQueryStatement(DataQueryStatementType.UNTYPED, statements[i]);
		}

		return and(dqStatements);
	}
	
	public static DataQueryStatement or(DataQueryStatement... statements) {
		return new ComposedDataQueryStatement(DataQueryStatementType.OR, statements);
	}
	
	public static DataQueryStatement or(String... statements) {
		DataQueryStatement[] dqStatements = new DataQueryStatement[statements.length];
		
		for(int i=0; i<statements.length; i++) {
			dqStatements[i] = new SingleDataQueryStatement(DataQueryStatementType.UNTYPED, statements[i]);
		}

		return or(dqStatements);
	}
	
	public static DataQueryStatement not(String statement) {
		return new ComposedDataQueryStatement(DataQueryStatementType.NOT, statement(statement));
	}
	
	public static DataQueryStatement not(DataQueryStatement statement) {
		return new ComposedDataQueryStatement(DataQueryStatementType.NOT, statement);
	}
}