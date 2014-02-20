package com.aichamorro.dal.dataquery;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

public class DataQueryFactoryTest extends TestCase {
	public DataQueryFactoryTest( String testName ) {
		super( testName );
	}

	public Test suite() {
		return new TestSuite( DataQueryFactoryTest.class );
	}

	public void testCreateASelectDataQuery() {
		assertEquals(DataQuery.QUERY_TYPE_SELECT, DataQueryFactory.select().getType());
	}

	public void testCreateAInsertDataQuery() {
		assertEquals(DataQuery.QUERY_TYPE_INSERT, DataQueryFactory.insert(new Object()).getType());
	}

	public void testCreateADeleteDataQuery() {
		assertEquals(DataQuery.QUERY_TYPE_DELETE, DataQueryFactory.delete(new Object()).getType());
	}

	public void testCreateAUpdateDataQuery() {
		assertEquals(DataQuery.QUERY_TYPE_UPDATE, DataQueryFactory.update(new Object()).getType());
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
