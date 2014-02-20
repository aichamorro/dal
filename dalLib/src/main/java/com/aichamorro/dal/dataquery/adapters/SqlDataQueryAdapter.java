package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQuery;

public class SqlDataQueryAdapter implements DataQueryAdapter<String> {
	public void print(DataQuery query) {
		String result = "";

		switch(query.getType()) {
			case DataQuery.QUERY_TYPE_SELECT:
				result += "SELECT ";
				break;
			case DataQuery.QUERY_TYPE_INSERT:
				result += "INSERT ";
				break;
			case DataQuery.QUERY_TYPE_UPDATE:
				result += "UPDATE ";
				break;
			case DataQuery.QUERY_TYPE_DELETE:
				result += "DELETE ";
				break;
			default: 
				assert false : "Not implemented (yet)";
		}

//		TODO
//		for(String field : query._fields) {
//			String alias = query._alias.get(field);
//
//			result += field + (alias != null ? " AS " + alias : "");
//			result += ",";
//		}
//
//		System.out.println(result.substring(0, result.length() - 1));
	}

	public String objectForQuery(DataQuery query) {
		return null;
	}
}
