package com.aichamorro.dal.datasource;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.adapters.RestDataQueryAdapter;
import com.aichamorro.dal.dataquery.result.DataQueryResult;
import com.aichamorro.dal.http.HttpOperation;

public class RestDataSource implements DataSource {
	private RestDataQueryAdapter _queryAdapter;

	public RestDataSource(String url) {
		_queryAdapter = new RestDataQueryAdapter(url);
	}

	public DataQueryResult executeQuery(DataQuery query) {
		HttpOperation operation = _queryAdapter.objectForQuery(query);

		return DataQueryResult.EMPTY_SUCCESS;
	}
}
