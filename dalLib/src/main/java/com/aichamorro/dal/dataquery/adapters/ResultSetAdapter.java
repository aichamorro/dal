package com.aichamorro.dal.dataquery.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aichamorro.dal.dataquery.DataQueryResult;
import com.aichamorro.dal.dataquery.DataQueryResultFactory;

public class ResultSetAdapter {

	public static DataQueryResult getDataQueryResultFor(ResultSet result) {
		try {
			if(! result.next() ) {
				return DataQueryResultFactory.emptyResult();
			}else{
				return DataQueryResultFactory.wrapperFor(result);
			}
		} catch (SQLException e) {
			assert false : "Exception occurred: " + e.toString();
			// TODO Return a DataQueryResultError
		}
		
		assert false : "Case not handled.";
		return null;
	}

}
