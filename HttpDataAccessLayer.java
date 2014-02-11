import dataquery.DataQuery;
import dataquery.HttpDataQueryAdapter;

public class HttpDataAccessLayer extends DataAccessLayer {
	private HttpDataQueryAdapter _dataQueryAdapter;

	public HttpDataAccessLayer() {
		_dataQueryAdapter = new HttpDataQueryAdapter();
	}

	public void addDataSource(DataSource datasource) {
	}

	public void execute(DataQuery query) {
	}

	public void execute(DataQuery query, DataAdapter adpater) {
	}
}
