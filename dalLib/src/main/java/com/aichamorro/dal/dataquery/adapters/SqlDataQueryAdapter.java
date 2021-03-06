package com.aichamorro.dal.dataquery.adapters;

import java.util.HashMap;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQuery.QueryType;
import com.aichamorro.dal.dataquery.sql.SqlStatements;
import com.aichamorro.dal.dataquery.DataQueryFilter;
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

		public void setModel(String modelName) {
			String sqlModelName = '`' + modelName + '`';
			
			switch(_queryType) {
			case SELECT: result += " *";
			case DELETE: result += SqlStatements.FROM + sqlModelName; break;
			case UPDATE: result += " " + sqlModelName; break;
			case INSERT: result += SqlStatements.INTO + sqlModelName; break;
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
				sb.append('\'');
				sb.append(payload.get(key));
				sb.append("',");
			}

			assert sb.length() > 0 : "No payload received!!";
			return sb.substring(0, sb.length() - 1).toString();		
		}

		public void setPayload(HashMap<String, String>payload) {
			StringBuilder sb = new StringBuilder();
			
			switch(_queryType) {
			case INSERT: 
				sb.append("(");
				sb.append(getKeysAsString(payload));
				sb.append(") ");
				sb.append(SqlStatements.VALUES);
				sb.append("(");
				sb.append(getValuesAsString(payload));
				sb.append(")");
				
				result += sb.toString();
				break;
			case UPDATE: 
				sb.append(SqlStatements.SET);
				for(String key : payload.keySet()) {
					sb.append(key);	sb.append("='"); sb.append(payload.get(key)); sb.append("',");
				}

				assert sb.length() > 0 : "It seems the payload is empty, which is not possible when updating a record";
				sb.deleteCharAt(sb.length() - 1);
				
				result += sb.toString();
				break;
			default:
			}
		}

		public String getString() {
			return result;
		}
		
		public void addFilter(DataQueryFilter.Iterator iterator) {
			assert iterator.hasNext() : "WTF? Emtpy iterator? Really?";
			
			result += SqlStatements.WHERE + new SqlDataQueryFilterAdapter().statementAdapter(iterator.next());
		}
	}
}
