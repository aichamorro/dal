package com.aichamorro.dal.dataquery;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import static org.mockito.Mockito.*;

public class DataQueryFactoryTest extends TestCase {
	public DataQueryFactoryTest( String testName ) {
		super( testName );
	}

	public Test suite() {
		return new TestSuite( DataQueryFactoryTest.class );
	}

	public void testCreateASelectDataQuery() {
		assertEquals(DataQuery.QueryType.SELECT, DataQueryFactory.select().getType());
	}

	public void testCreateAInsertDataQuery() {
		Queryable object = mock(Queryable.class);

		assertEquals(DataQuery.QueryType.INSERT, DataQueryFactory.insert(object).getType());
	}

	public void testCreateADeleteDataQuery() {
		Queryable object = mock(Queryable.class);

		assertEquals(DataQuery.QueryType.DELETE, DataQueryFactory.delete(object).getType());
	}

	public void testCreateAUpdateDataQuery() {
		Queryable object = mock(Queryable.class);

		assertEquals(DataQuery.QueryType.UPDATE, DataQueryFactory.update(object).getType());
	}

	public void testCreateInsertDataQueryWithNullObjectRaisesNullPointerException() {
		try{
			DataQueryFactory.insert(null);
			fail("Should've occurred a NullPointerException");
		}catch(NullPointerException ex) {
			assertTrue("NullPointerException caught when using null in DataQueryFactory.insert",true);
		}
	}

	public void testCreateUpdateDataQueryWithNullObjectRaisesNullPointerException() {
		try{
			DataQueryFactory.update(null);
			fail("Should've occurred a NullPointerException");
		}catch(NullPointerException ex) {
			assertTrue("NullPointerException caught when using null in DataQueryFactory.insert",true);
		}
	}

	public void testCreateDeleteDataQueryWithNullObjectRaisesNullPointerException() {
		try{
			DataQueryFactory.delete(null);
			fail("Should've occurred a NullPointerException");
		}catch(NullPointerException ex) {
			assertTrue("NullPointerException caught when using null in DataQueryFactory.insert",true);
		}
	}
}
