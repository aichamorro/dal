package com.aichamorro.dal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import com.aichamorro.dal.datasource.DataSource;

public class DataAccessLayerTest extends TestCase {
	public DataAccessLayerTest( String testName ) {
		super( testName );
	}

	public static Test suite() {
		return new TestSuite( DataAccessLayerTest.class );
	}

	public void testDataAccessLayerAddDataSource() {
		DataSource ds = mock(DataSource.class);
		DataAccessLayer dal = new DataAccessLayer();
			dal.addDataSource(ds, "DataSourceId");

		assertEquals(dal.on("DataSourceId"), ds);
	}

	public void testUseAWrongDataSourceReturnsNull() {
		DataAccessLayer dal = new DataAccessLayer();
		
		assertNull(dal.on("DataSourceId"));
	}

}
