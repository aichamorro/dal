package com.aichamorro.dal.dataquery;

class ComposedDataQueryFilter extends DataQueryFilter {

	private DataQueryFilter[] _statements;
	private DataQueryFilterType _type;

	public ComposedDataQueryFilter(DataQueryFilterType type, DataQueryFilter... statements) {
		_type = type;
		_statements = statements;
	}
	
	public void visit(DataQueryStatementVisitor visitor) {
	}

	public DataQueryFilterType getType() {
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
