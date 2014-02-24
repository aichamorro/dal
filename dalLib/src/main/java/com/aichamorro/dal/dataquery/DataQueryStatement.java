package com.aichamorro.dal.dataquery;

public abstract class DataQueryStatement {
	public enum DataQueryStatementType {
		WHERE, AND, OR, UNTYPED
	}
	
	abstract public boolean isComposed();
	abstract public DataQueryStatementType getType();
	abstract public Iterator iterator();
	
	public boolean isUnary() {
		return getType() == DataQueryStatementType.WHERE;
	}
	
	public class Iterator implements java.util.Iterator<DataQueryStatement>{
		private DataQueryStatement[] _statements;
		private int _currentIndex;
		
		public Iterator(DataQueryStatement statement) {
			this(new DataQueryStatement[]{statement});
		}
		
		public Iterator(DataQueryStatement[] statements) {
			_currentIndex = 0;
			_statements = statements;
		}

		public boolean hasNext() {
			return _currentIndex < _statements.length;
		}

		public DataQueryStatement next() {
			return _statements[_currentIndex++];
		}

		public void remove() {
			// Do nothing
		}
		
	}
}
