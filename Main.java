import dataaccesslayer.DataAccessLayer;
import dataaccesslayer.dataquery.DataQuery;
import dataaccesslayer.datasource.DataSource;

class Main {
	private static final String DATASOURCE_DB = "Database";

	public static void main(String[] args) {
		DataAccessLayer dal = new DataAccessLayer();
			dal.addDataSource(new DBDataSource("dbUrl", 8080, "alberto", "alberto"), DATASOURCE_DB);

		DataQuery query = DataQuery.from("students").select().field("studentId").field("name");
			dal.execute(query, DATASOURCE_DB);
	}

}

class DBDataSource extends DataSource {
	public DBDataSource(String url, long port, String username, String password) {
	}
}
