package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.adapters.DataQueryAdapter;
import com.aichamorro.dal.http.HttpOperation;

public class RestDataQueryAdapter implements DataQueryAdapter<HttpOperation> {
	private String _url;

	public RestDataQueryAdapter(String url) {
		_url = url;
	}

	public HttpOperation objectForQuery(DataQuery query) {
		HttpOperation operation = new HttpOperation(_url);

		operation.setMethod(methodFromQuery(query));
		// TODO Add the parameters to the operation
		// TODO Execute the HttpOperation

		return operation;
	}

	public int methodFromQuery(DataQuery query) {
		switch(query.getType()) {
			case SELECT:
				return HttpOperation.GET;
			case INSERT: 
				return HttpOperation.PUT;
			case UPDATE: 
				return HttpOperation.POST;
			case DELETE: 
				return HttpOperation.DELETE;
			default:
				assert false : "Query type not recognized " + query.getType();
				return -1;
		}
	}
}
