package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQuery.QueryType;
import com.aichamorro.dal.dataquery.DataQueryStatement;
import com.aichamorro.dal.dataquery.DataQueryStatement.DataQueryStatementType;
import com.aichamorro.dal.dataquery.DataQueryStatementVisitor;
import com.aichamorro.dal.dataquery.DataQueryVisitor;
import com.aichamorro.dal.dataquery.Queryable;

public class SqlDataQueryAdapter implements DataQueryAdapter<String> {
	public String objectForQuery(DataQuery query) {
		Visitor visitor = new Visitor();
		
		query.visit(visitor);
		
		return visitor.getString();
	}
	
	private class Visitor implements DataQueryVisitor {
		String result;
		
		Visitor() {
			result = new String();
		}
		
		public void setType(QueryType queryType) {
			result += queryType.name() + " *";
		}

		public void setModel(Class className) {
			result += " FROM " + className.getSimpleName(); 
		}

		public void setPayload(Queryable payload) {
			
		}
		
		public String getString() {
			return result;
		}

		private String addFilter(DataQueryStatement.Iterator iterator, String operator) {
			String result = "";
			
			boolean hasNext = iterator.hasNext();
			while(hasNext) {
				DataQueryStatement statement = iterator.next();
				
				result += statement;
				
				hasNext = iterator.hasNext();
				if( hasNext ) {
					result += " " + operator + " ";
				}
			}
			
			return result;
		}
		
		public void addFilter(DataQueryStatement.Iterator iterator) {
			result += " WHERE";
			
			while(iterator.hasNext()) {
				DataQueryStatement statement = iterator.next();
				
				if( statement.isComposed() ) {
					result += " " + addFilter(statement.iterator(), statement.getType().name());
				}else{
					result += " " + statement.toString();
				}
			}
		}

	}
}
