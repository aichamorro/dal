package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.dataquery.DataQueryStatement.DataQueryStatementType;

public class DataQueryStatementFactory {
	static DataQueryStatement whereStatement(String statement) {
		return new SingleDataQueryStatement(DataQueryStatementType.WHERE, statement);
	}

	static DataQueryStatement whereStatement(DataQueryStatement statement) {
		return new ComposedDataQueryStatement(DataQueryStatementType.WHERE, statement);
	}
	
	public static DataQueryStatement untypedStatement(String statement) {
		return new SingleDataQueryStatement(DataQueryStatementType.UNTYPED, statement);
	}

	public static DataQueryStatement andStatement(DataQueryStatement... statements) {
		return new ComposedDataQueryStatement(DataQueryStatementType.AND, statements);
	}
}
