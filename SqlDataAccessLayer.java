import dataquery.DataQuery;
import dataquery.SqlDataQueryAdapter;

public class SqlDataAccessLayer extends DataAccessLayer {
	private SqlDataQueryAdapter _dataQueryAdapter;

	public void SqlDataAccessLayer() {
		_dataQueryAdapter = new SqlDataQueryAdapter();
	}

	public void execute(DataQuery query) {
		// TODO Sql things to execute the query
	}

	public void execute(DataQuery query, DataAdapter adapter) {
		// TODO Sql things to execute the query
	}

	public void addDataSource(DataSource datasource) {
	}
}
