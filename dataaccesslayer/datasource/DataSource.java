package dataaccesslayer.datasource;

import dataaccesslayer.dataquery.DataQuery;

abstract public class DataSource {
	abstract public boolean executeQuery(DataQuery query);
}
