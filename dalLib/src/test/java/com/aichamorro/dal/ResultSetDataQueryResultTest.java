package com.aichamorro.dal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.ModelImpl;
import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;

public class ResultSetDataQueryResultTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(ResultSetDataQueryResultTest.class);
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
	
	public void testEmptyDataQueryResultSet() throws SQLException {
		ResultSet set = mock(ResultSet.class);
			when(set.next()).thenReturn(false);
			
		ResultSetDataQueryResult queryResult = new ResultSetDataQueryResult(set);
		
		assertFalse(queryResult.isErrorResult());
		assertNull(queryResult.iterator(TestModel.class).next());
	}
	
	public void testOneRecordDataQueryResultSet() throws SQLException {
		ResultSet set = mock(ResultSet.class);
			when(set.next()).thenReturn(true);
			when(set.getString("_id")).thenReturn("ACB001");
			when(set.getString("_name")).thenReturn("Alberto");
			
		ResultSetDataQueryResult queryResult = new ResultSetDataQueryResult(set);
			
		TestModel expected = new TestModel("ACB001", "Alberto");
		assertTrue(expected.equals(queryResult.iterator(TestModel.class).next()));

		verify(set).next();
		verify(set).getString("_id");
		verify(set).getString("_name");
	}
}