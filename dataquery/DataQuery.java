package dataquery;

import java.util.HashMap;
import java.util.ArrayList;

public class DataQuery {
	public static final String ALL_FIELDS = "*";

	static final int QUERY_TYPE_SELECT = 504748;
	static final int QUERY_TYPE_INSERT = 504749;
	static final int QUERY_TYPE_UPDATE = 504750;
	static final int QUERY_TYPE_DELETE = 504751;

	WhereClause _whereClause;
	FromClause _fromClause;
	ArrayList<String> _fields;
	HashMap<String, String> _alias;
	int _queryType;

	private DataQuery() {
		_fields = new ArrayList<String>();
		_alias = new HashMap<String, String>();
		_whereClause = new WhereClause();
		_fromClause = new FromClause();
	}

	private DataQuery setType(int queryType) {
		_queryType = queryType;

		return this;
	}

	static public DataQuery select() {
		return new DataQuery().setType(QUERY_TYPE_SELECT);
	}

	static public DataQuery insert() {
		return new DataQuery().setType(QUERY_TYPE_INSERT);
	}

	static public DataQuery delete() {
		return new DataQuery().setType(QUERY_TYPE_DELETE);
	}

	static public DataQuery update() {
		return new DataQuery().setType(QUERY_TYPE_UPDATE);
	}

	public DataQuery field(String fieldName) {
		_fields.add(fieldName);

		return this;
	}

	public DataQuery as(String alias) {
		_alias.put(_fields.get(_fields.size() - 1), alias);

		return this;
	}

	public WhereClause where() {
		return _whereClause;
	}

	public FromClause from() {
		return _fromClause;
	}

}
