package com.aichamorro.dal.dataquery;

public class SingleDataQueryStatement extends DataQueryStatement {
	private String _statement;
	DataQueryStatementType _type;
	
	public SingleDataQueryStatement(DataQueryStatementType type, String statement) {
		_type = type;
		_statement = statement;
	}
	
	public DataQueryStatementType getType() {
		return _type;
	}

	@Override
	public String toString() {
		return _statement;
	}
	
	public boolean isComposed() {
		return false;
	}

	@Override
	public Iterator iterator() {
		return new Iterator(this);
	}
}
