import dataquery.*;

class Main {
	public static void main(String[] args) {
		DataQuery query = DataQuery.select().field("studentId").field("name").as("n");

		SqlDataQueryAdapter sqlAdapter = new SqlDataQueryAdapter();
			sqlAdapter.print(query);
	}
}
