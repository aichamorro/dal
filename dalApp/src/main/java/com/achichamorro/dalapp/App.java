package com.aichamorro.dalapp;

import com.aichamorro.dal.DataAccessLayer;
import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.datasource.RestDataSource;

class App {
	private static final String DataSourceRest = "kDataSourceRest";

	public static void main(String[] args) {
		RestDataSource rds = new RestDataSource("http://localhost:8888/dal/");
//			rds.setOAuth();
//			rds.setUsername();
//			rds.setPassword();

		DataAccessLayer dal = new DataAccessLayer();
			dal.addDataSource(rds, DataSourceRest);

//		DataQuery query = DataQuery.from("students").select().field("studentId").field("name");
		DataQuery query = DataQuery.from("students").select();
			dal.execute(query, DataSourceRest);
	}

}
