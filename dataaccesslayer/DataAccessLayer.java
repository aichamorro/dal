package dataaccesslayer;

import dataaccesslayer.dataquery.DataQuery;
import dataaccesslayer.datasource.DataSource;

import java.util.HashMap;

public class DataAccessLayer {
	private HashMap<String, DataSource> _datasources;

	public DataAccessLayer() {
		_datasources = new HashMap<String, DataSource>();
	}

	public boolean execute(DataQuery query, String dataSource) {
		assert query.getType() != -1 : "Query type not set";
		assert _datasources.containsKey(dataSource) : "DataSource used not valid";

		if( query.getType() == -1 ||  !_datasources.containsKey(dataSource) ) {
			return false;
		}

		DataSource ds = _datasources.get(dataSource);
		ds.executeQuery(query);

		return true;
	}

	public DataAccessLayer addDataSource(DataSource datasource, String idDataSource) {
		_datasources.put(idDataSource, datasource);

		return this;
	}
}
