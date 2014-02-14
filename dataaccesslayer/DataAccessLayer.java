package dataaccesslayer;

import dataaccesslayer.dataquery.DataQuery;
import dataaccesslayer.datasource.DataSource;

import java.util.HashMap;

public class DataAccessLayer {
	private HashMap<String, DataSource> _datasources;

	public void execute(DataQuery query, String dataSource) {
	}

	protected DataAccessLayer() {
		_datasources = new HashMap<String, DataSource>();
	}

	public DataAccessLayer addDataSource(DataSource datasource, String idDataSource) {
		// TODO add the datasource into the hash map
		return this;
	}
}
