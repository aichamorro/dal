package com.aichamorro.dal.datasource;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.result.DataQueryResult;

public interface DataSource {
	DataQueryResult executeQuery(DataQuery query);
}
