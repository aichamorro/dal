package com.aichamorro.dal.dataquery;

public class ComposedDataQueryStatement extends DataQueryStatement {

	private DataQueryStatement[] _statements;
	private DataQueryStatementType _type;

	public ComposedDataQueryStatement(DataQueryStatementType type, DataQueryStatement... statements) {
		_type = type;
		_statements = statements;
	}
	
	public void visit(DataQueryStatementVisitor visitor) {
	}

	public DataQueryStatementType getType() {
		return _type;
	}

	public Object getStatement() {
		return _statements;
	}

	public boolean isComposed() {
		return true;
	}

	@Override
	public Iterator iterator() {
		return new Iterator(_statements);
	}

}
