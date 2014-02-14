import dataaccesslayer.DataAccessLayer;
import dataaccesslayer.dataquery.DataQuery;
import dataaccesslayer.datasource.DataSource;
import dataaccesslayer.dataquery.DataQueryAdapter;

class Main {
	private static final String DataSourceRest = "kDataSourceRest";

	public static void main(String[] args) {
		RestDataSource rds = new RestDataSource("http://localhost:8888/dal/");
//			rds.setOAuth();
//			rds.setUsername();
//			rds.setPassword();

		DataAccessLayer dal = new DataAccessLayer();
			dal.addDataSource(rds, DataSourceRest);

//		DataQuery query = DataQuery.from("students").select().field("studentId").field("name");
		DataQuery query = DataQuery.from("students").select();
			dal.execute(query, DataSourceRest);
	}

}

class RestDataSource extends DataSource {
	private RestDataQueryAdapter _queryAdapter;

	public RestDataSource(String url) {
		_queryAdapter = new RestDataQueryAdapter(url);
	}

	public boolean executeQuery(DataQuery query) {
		HttpOperation operation = _queryAdapter.objectForQuery(query);

		return true;
	}
}

class RestDataQueryAdapter implements DataQueryAdapter<HttpOperation> {
	private String _url;

	public RestDataQueryAdapter(String url) {
		_url = url;
	}

	public HttpOperation objectForQuery(DataQuery query) {
		HttpOperation operation = new HttpOperation(_url);

		operation.setMethod(methodFromQuery(query));
		// TODO Add the parameters to the operation
		// TODO Execute the HttpOperation

		return operation;
	}

	public int methodFromQuery(DataQuery query) {
		switch(query.getType()) {
			case DataQuery.QUERY_TYPE_SELECT:
				return HttpOperation.GET;
			case DataQuery.QUERY_TYPE_INSERT: 
				return HttpOperation.PUT;
			case DataQuery.QUERY_TYPE_UPDATE: 
				return HttpOperation.POST;
			case DataQuery.QUERY_TYPE_DELETE: 
				return HttpOperation.DELETE;
			default:
				assert false : "Query type not recognized " + query.getType();
				return -1;
		}
	}
}

class HttpOperation {
	static public final int GET = 100;
	static public final int POST = 101;
	static public final int PUT = 102;
	static public final int DELETE = 103;

	private String _url;
	private int _type;

	public HttpOperation(String url) {
		_url = url;
		_type = HttpOperation.GET;
	}

	public void setMethod(int type) {
		_type = type;
	}
}
