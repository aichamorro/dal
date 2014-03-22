package com.aichamorro.dal;

import com.aichamorro.dal.dataquery.result.DataQueryResult;
import com.aichamorro.dal.dataquery.result.DataQueryResultIterator;
import com.aichamorro.dal.model.Model;

public class EmptyOkDataQueryResult implements DataQueryResult {

	static final private class InstanceHolder {
		static private final EmptyOkDataQueryResult _instance = new EmptyOkDataQueryResult();		
	}
	
	private EmptyOkDataQueryResult() {}
	
	static public final EmptyOkDataQueryResult getInstance() {
		return InstanceHolder._instance;
	}
	
	public boolean isError() {
		return false;
	}

	public Object getError() {
		return null;
	}

	public <T extends Model> DataQueryResultIterator<T> iterator(Class<T> forClass) {
		return (DataQueryResultIterator<T>) EmptyDataQueryResultIterator.getInstance();
	}
}