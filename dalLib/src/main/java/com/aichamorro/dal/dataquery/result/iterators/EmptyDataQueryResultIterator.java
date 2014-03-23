package com.aichamorro.dal.dataquery.result.iterators;

import com.aichamorro.dal.model.Model;

public class EmptyDataQueryResultIterator implements DataQueryResultIterator<Model> {

	static private class InstanceHolder {
		static private final EmptyDataQueryResultIterator _instance = new EmptyDataQueryResultIterator();
	}
	
	private EmptyDataQueryResultIterator() {
		
	}
	
	static public final EmptyDataQueryResultIterator getInstance() {
		return InstanceHolder._instance;
	}
	
	public Model next() {
		return null;
	}
}
