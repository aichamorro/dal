package dataaccesslayer.dataquery;

import java.util.HashMap;
import java.util.ArrayList;

abstract public class DataQuery {
	public static final String ALL_FIELDS = "*";

	static final int QUERY_TYPE_SELECT = 504748;
	static final int QUERY_TYPE_INSERT = 504749;
	static final int QUERY_TYPE_UPDATE = 504750;
	static final int QUERY_TYPE_DELETE = 504751;

	WhereClause _whereClause;
	ArrayList<String> _fields;
	HashMap<String, String> _alias;
	int _queryType;

	protected DataQuery() {
		_fields = new ArrayList<String>();
		_alias = new HashMap<String, String>();
		_whereClause = new WhereClause();
		_queryType = -1;
	}

	private DataQuery setType(int queryType) {
		_queryType = queryType;

		return this;
	}

	public DataQuery select() {
		assert -1 == _queryType : "The query type has already been set";

		return setType(QUERY_TYPE_SELECT);
	}

	public DataQuery insert() {
		assert -1 == _queryType : "The query type has already been set";

		return setType(QUERY_TYPE_INSERT);
	}

	public DataQuery delete() {
		assert -1 == _queryType : "The query type has already been set";

		return setType(QUERY_TYPE_DELETE);
	}

	public DataQuery update() {
		assert -1 == _queryType : "The query type has already been set";

		return setType(QUERY_TYPE_UPDATE);
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

	static public FromDataQuery from(String entity) {
		return new FromDataQuery();
	}
}
