package com.aichamorro.dal.dataquery;

public abstract class DataQueryFilter {
	public enum DataQueryFilterType {
		WHERE, AND, OR, UNTYPED, NOT
	}
	
	abstract public boolean isComposed();
	abstract public DataQueryFilterType getType();
	abstract public Iterator iterator();
	
	public boolean isUnary() {
		return getType() == DataQueryFilterType.WHERE;
	}
	
	public class Iterator implements java.util.Iterator<DataQueryFilter>{
		private DataQueryFilter[] _statements;
		private int _currentIndex;
		
		public Iterator(DataQueryFilter statement) {
			this(new DataQueryFilter[]{statement});
		}
		
		public Iterator(DataQueryFilter[] statements) {
			_currentIndex = 0;
			_statements = statements;
		}

		public boolean hasNext() {
			return _currentIndex < _statements.length;
		}

		public DataQueryFilter next() {
			return _statements[_currentIndex++];
		}

		public void remove() {
			// Do nothing
		}
		
	}
}
