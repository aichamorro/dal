package com.aichamorro.dal.dataquery.result;

import com.aichamorro.dal.dataquery.result.iterators.DataQueryResultIterator;
import com.aichamorro.dal.dataquery.result.iterators.EmptyDataQueryResultIterator;
import com.aichamorro.dal.model.Model;

public interface DataQueryResult {
	public static final DataQueryResult EMPTY_SUCCESS = new EmptyOkDataQueryResult();
	
	boolean isError();
	Object getError();
	<T extends Model>DataQueryResultIterator<T> iterator(Class<T> forClass);
}

final class EmptyOkDataQueryResult implements DataQueryResult {

	public EmptyOkDataQueryResult() {}
	
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