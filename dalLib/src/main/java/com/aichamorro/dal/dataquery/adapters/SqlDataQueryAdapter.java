package com.aichamorro.dal.dataquery.adapters;

import java.util.HashMap;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQuery.QueryType;
import com.aichamorro.dal.dataquery.DataQueryStatement;
import com.aichamorro.dal.dataquery.DataQueryVisitor;

public class SqlDataQueryAdapter implements DataQueryAdapter<String> {
	public String objectForQuery(DataQuery query) {
		Visitor visitor = new Visitor();
		
		query.visit(visitor);
		
		return visitor.getString();
	}
	
	private class Visitor implements DataQueryVisitor {
		String result;
		QueryType _queryType;
		
		Visitor() {
			result = new String();
		}
		
		public void setType(QueryType queryType) {
			_queryType = queryType;
			
			result += queryType.name();
		}

		public void setModel(Class className) {
			switch(_queryType) {
			case SELECT: result += " *";
			case DELETE: result += SqlStatements.FROM + className.getSimpleName(); break;
			case UPDATE: result += " " + className.getSimpleName(); break;
			case INSERT: result += SqlStatements.INTO + className.getSimpleName(); break;
			default:
			}
		}
		
		private String getKeysAsString(HashMap<String, String>payload) {
			StringBuilder sb = new StringBuilder();

			for( String key : payload.keySet() ) {
				sb.append(key + ",");
			}

			assert sb.length() > 0 : "No payload received!!";
			return sb.substring(0, sb.length() - 1).toString();
		}
		
		private String getValuesAsString(HashMap<String, String> payload) {
			StringBuilder sb = new StringBuilder();

			for( String key : payload.keySet() ) {
				sb.append(payload.get(key) + ",");
			}

			assert sb.length() > 0 : "No payload received!!";
			return sb.substring(0, sb.length() - 1).toString();		
		}

		public void setPayload(HashMap<String, String>payload) {
			switch(_queryType) {
			case INSERT: result += "(" + getKeysAsString(payload) + ") "+ SqlStatements.VALUES + "(" + getValuesAsString(payload) + ")";
			case UPDATE:
			case DELETE:
			case SELECT:
			default:
			}
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
