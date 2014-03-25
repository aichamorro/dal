package com.aichamorro.dal;

import java.util.HashMap;

import com.aichamorro.dal.datasource.DataSource;

/**
 * Is an object used for storing DataSource objects. An unique identifier is stored along
 * with the DataSource to be used for accessing the DataSource through the DataAccessLayer.
 * @author achamorro
 *
 */
public class DataAccessLayer {
	/**
	 * Stored DataSources
	 */
	private HashMap<String, DataSource> _datasources;

	/**
	 * Constructor
	 */
	public DataAccessLayer() {
		_datasources = new HashMap<String, DataSource>();
	}

	/**
	 * Access a DataSource stored in the DataAccessLayer instance.
	 * @param dataSource String identifier used when the DataSource was stored.
	 * @return The requested DataSource instance or null in case no DataSource with that identifier was stored.
	 */
	public DataSource on(String dataSource) {
		assert _datasources.containsKey(dataSource) : "DataSource used not valid";

		return _datasources.get(dataSource);
	}

	/**
	 * Add a DataSource instance to the DataAccessLayer instance.
	 * @param datasource The DataSource instance to be stored.
	 * @param idDataSource An unique identifier to distinguish the DataSource instance among other stored instances.
	 * @return this for method chaining.
	 */
	public DataAccessLayer addDataSource(DataSource datasource, String idDataSource) {
		_datasources.put(idDataSource, datasource);

		return this;
	}
}
