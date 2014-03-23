package com.aichamorro.dal.dataquery;

class SingleDataQueryFilter extends DataQueryFilter {
	private String _statement;
	DataQueryFilterType _type;
	
	public SingleDataQueryFilter(DataQueryFilterType type, String statement) {
		_type = type;
		_statement = statement;
	}
	
	public DataQueryFilterType getType() {
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
