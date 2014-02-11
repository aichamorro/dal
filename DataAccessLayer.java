import dataquery.DataQuery;
import java.util.HashMap;

public abstract class DataAccessLayer {
	private HashMap<String, DataSource> _datasources;

	abstract public void execute(DataQuery query);

	protected DataAccessLayer() {
		_datasources = new HashMap<String, DataSource>();
	}

	public DataAccessLayer addDataSource(DataSource datasource, String idDataSource) {
		// TODO add the datasource into the hash map
		return this;
	}
}
