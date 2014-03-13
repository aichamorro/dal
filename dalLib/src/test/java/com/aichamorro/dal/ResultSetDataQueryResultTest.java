package com.aichamorro.dal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.dataquery.result.DataQueryResultIterator;
import com.aichamorro.dal.dataquery.result.ResultSetDataQueryResult;
import com.aichamorro.dal.model.ModelImpl;
import com.aichamorro.dal.model.annotations.ModelField;
import com.aichamorro.dal.model.annotations.ModelId;

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
	
	public void testTwoRecordsDataQueryResultSet() throws SQLException {
		ResultSet set = mock(ResultSet.class);
			when(set.next()).thenReturn(true, true, false);
			when(set.getString("_id")).thenReturn("ACB001", "ACB002");
			when(set.getString("_name")).thenReturn("Alberto", "Rocio");
			
		ResultSetDataQueryResult queryResult = new ResultSetDataQueryResult(set);
		DataQueryResultIterator iterator = queryResult.iterator(TestModel.class);
		
		assertTrue(new TestModel("ACB001", "Alberto").equals(iterator.next()));
		assertTrue(new TestModel("ACB002", "Rocio").equals(iterator.next()));
		assertNull(iterator.next());
	}
	
	public void testModelWithIntPrimitiveFields() throws SQLException {
		ResultSet set = mock(ResultSet.class);
			when(set.next()).thenReturn(true, false);
			when(set.getInt("int")).thenReturn(5);
			when(set.getLong("long")).thenReturn(10L);
			when(set.getFloat("float")).thenReturn(200.43f);
			when(set.getDouble("double")).thenReturn(2.04321748363128976423);
			
		ResultSetDataQueryResult queryResult = new ResultSetDataQueryResult(set);
		DataQueryResultIterator iterator = queryResult.iterator(PrimitiveFieldsModel.class);
		
		assertTrue(new PrimitiveFieldsModel(5, 10L, 200.43f, 2.04321748363128976423).equals(iterator.next()));
		assertNull(iterator.next());
	}
	
	public void testModelWithObjectsInside() throws SQLException {
		PrimitiveFieldsModel instance = new PrimitiveFieldsModel();
		ResultSet set = mock(ResultSet.class);
			when(set.next()).thenReturn(true, false);
			when(set.getObject("Object")).thenReturn(instance);
			
		ResultSetDataQueryResult queryResult = new ResultSetDataQueryResult(set);
		DataQueryResultIterator iterator = queryResult.iterator(PrimitiveFieldsModel.class);
		
		assertSame(instance, ((PrimitiveFieldsModel)iterator.next()).object);
	}
}
