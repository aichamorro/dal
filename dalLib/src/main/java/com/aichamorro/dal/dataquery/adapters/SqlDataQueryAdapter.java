package com.aichamorro.dal.dataquery.adapters;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQuery.QueryType;
import com.aichamorro.dal.dataquery.DataQueryStatement;
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
			result += SqlStatements.FROM + className.getSimpleName(); 
		}

		public void setPayload(Queryable payload) {
			
		}
		
		public String getString() {
			return result;
		}
		
		public void addFilter(DataQueryStatement.Iterator iterator) {
			assert iterator.hasNext() : "WTF? Emtpy iterator? Really?";
			
			result += SqlStatements.WHERE + new SqlDataQueryStatementAdapter().statementAdapter(iterator.next());
		}
	}
}
