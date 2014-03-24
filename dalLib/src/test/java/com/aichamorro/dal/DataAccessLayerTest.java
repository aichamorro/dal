package com.aichamorro.dal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQueryFactory;
import com.aichamorro.dal.dataquery.result.DataQueryResult;
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
	
	public void testExecuteQueryOnADataSource() {
		DataQuery query = DataQueryFactory.insert(new TestModel()).createQuery();
		DataSource ds = mock(DataSource.class);
		when(ds.executeQuery(query)).thenReturn(DataQueryResult.EMPTY_SUCCESS);

		DataAccessLayer dal = new DataAccessLayer();
		dal.addDataSource(ds, "DataSourceId");
		dal.on("DataSourceId").executeQuery(query);
		
		Mockito.verify(ds).executeQuery(query);
	}

}
