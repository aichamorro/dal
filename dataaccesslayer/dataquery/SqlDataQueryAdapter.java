package dataaccesslayer.dataquery;

public class SqlDataQueryAdapter extends DataQueryAdapter {
	public void print(DataQuery query) {
		String result = "";

		switch(query._queryType) {
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

		for(String field : query._fields) {
			String alias = query._alias.get(field);

			result += field + (alias != null ? " AS " + alias : "");
			result += ",";
		}

		System.out.println(result.substring(0, result.length() - 1));
	}
}
