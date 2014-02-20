package com.aichamorro.dal.datasource;

import com.aichamorro.dal.dataquery.DataQuery;

abstract public class DataSource {
	abstract public boolean executeQuery(DataQuery query);
}
