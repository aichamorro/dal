package com.aichamorro.dal.dataquery;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

public class DataQueryTest extends TestCase {
	public DataQueryTest( String testName ) {
		super( testName );
	}

	public Test suite() {
		return new TestSuite(DataQueryTest.class);
	}

	public void testCreateSelectAllQuery() {
		assertEquals( DataQuery.selectAll().getType(), DataQuery.QUERY_TYPE_SELECT );
	}

	public void testCreateSelectQuery() {
		assertEquals( DataQuery.select(null).getType(), DataQuery.QUERY_TYPE_SELECT );
	}

	public void testCreateInsertQuery() {
		assertEquals( DataQuery.insert().getType(), DataQuery.QUERY_TYPE_INSERT );
	}

	public void testCreateUpdateQuery() {
		assertEquals( DataQuery.update().getType(), DataQuery.QUERY_TYPE_UPDATE );
	}

	public void testCreateDeleteQuery() {
		assertEquals( DataQuery.delete().getType(), DataQuery.QUERY_TYPE_DELETE );
	}

}
