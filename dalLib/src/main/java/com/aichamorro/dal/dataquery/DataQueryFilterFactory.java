package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.dataquery.DataQueryFilter.DataQueryFilterType;

public class DataQueryFilterFactory {
	static DataQueryFilter where(String statement) {
		return new SingleDataQueryFilter(DataQueryFilterType.WHERE, statement);
	}

	static DataQueryFilter where(DataQueryFilter statement) {
		return new ComposedDataQueryFilter(DataQueryFilterType.WHERE, statement);
	}
	
	public static DataQueryFilter statement(String statement) {
		return new SingleDataQueryFilter(DataQueryFilterType.UNTYPED, statement);
	}
	
	public static DataQueryFilter and(DataQueryFilter... statements) {
		return new ComposedDataQueryFilter(DataQueryFilterType.AND, statements);
	}

	public static DataQueryFilter and(String... statements) {
		DataQueryFilter[] dqStatements = new DataQueryFilter[statements.length];
		
		for(int i=0; i<statements.length; i++) {
			dqStatements[i] = new SingleDataQueryFilter(DataQueryFilterType.UNTYPED, statements[i]);
		}

		return and(dqStatements);
	}
	
	public static DataQueryFilter or(DataQueryFilter... statements) {
		return new ComposedDataQueryFilter(DataQueryFilterType.OR, statements);
	}
	
	public static DataQueryFilter or(String... statements) {
		DataQueryFilter[] dqStatements = new DataQueryFilter[statements.length];
		
		for(int i=0; i<statements.length; i++) {
			dqStatements[i] = new SingleDataQueryFilter(DataQueryFilterType.UNTYPED, statements[i]);
		}

		return or(dqStatements);
	}
	
	public static DataQueryFilter not(String statement) {
		return new ComposedDataQueryFilter(DataQueryFilterType.NOT, statement(statement));
	}
	
	public static DataQueryFilter not(DataQueryFilter statement) {
		return new ComposedDataQueryFilter(DataQueryFilterType.NOT, statement);
	}
}