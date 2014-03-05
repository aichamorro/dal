package com.aichamorro.dal.dataquery.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aichamorro.dal.dataquery.DataQueryResult;
import com.aichamorro.dal.dataquery.DataQueryResultFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.mockito.Mockito.*;

public class ResultSetAdapterTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(ResultSetAdapterTest.class);

		return suite;
	}
	
	public void testEmptyResultSet() throws SQLException {
		ResultSet resultSet = mock(ResultSet.class);
		when(resultSet.next()).thenReturn(false);
		
		assertTrue(ResultSetAdapter.getDataQueryResultFor(resultSet).isEmpty());
	}
	
	public void testGetAValueFromResultSetOfOneRecord() throws SQLException {
		ResultSet resultSet = mock(ResultSet.class);
		when(resultSet.next()).thenReturn(true, false);
		when(resultSet.getString("name")).thenReturn("Alberto");
		
		assertEquals("Alberto", ResultSetAdapter.getDataQueryResultFor(resultSet).getStringForField("name"));
	}

}
