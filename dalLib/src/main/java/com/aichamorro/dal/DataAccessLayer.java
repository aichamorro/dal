package com.aichamorro.dal;

import java.util.HashMap;

import com.aichamorro.dal.datasource.DataSource;

/**
 * Used for storing DataSource objects. An unique identifier is stored along
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
	 * Access to a stored DataSource.
	 * @param dataSource String identifier used when the DataSource was stored.
	 * @return The requested DataSource instance or <tt>null</tt> in case there is no DataSource stored with that identifier.
	 */
	public DataSource on(String dataSource) {
		assert _datasources.containsKey(dataSource) : "DataSource used not valid";

		return _datasources.get(dataSource);
	}

	/**
	 * Add a DataSource instance.
	 * @param datasource The DataSource to be stored.
	 * @param idDataSource An unique identifier to distinguish the DataSource instance among other stored instances.
	 * @return <tt>this</tt> for method chaining.
	 */
	public DataAccessLayer addDataSource(DataSource datasource, String idDataSource) {
		_datasources.put(idDataSource, datasource);

		return this;
	}
}
