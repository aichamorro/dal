package com.aichamorro.dal;

import java.util.HashMap;

import com.aichamorro.dal.datasource.DataSource;

public class DataAccessLayer {
	private HashMap<String, DataSource> _datasources;

	public DataAccessLayer() {
		_datasources = new HashMap<String, DataSource>();
	}

	public DataSource on(String dataSource) {
		assert _datasources.containsKey(dataSource) : "DataSource used not valid";

		return _datasources.get(dataSource);
	}

	public DataAccessLayer addDataSource(DataSource datasource, String idDataSource) {
		_datasources.put(idDataSource, datasource);

		return this;
	}
}
