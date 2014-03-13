package com.aichamorro.dal.dataquery;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.Model;
import com.aichamorro.dal.ModelImpl;
import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;

public class DataQueryFactoryTest extends TestCase {
	public DataQueryFactoryTest( String testName ) {
		super( testName );
	}

	public static Test suite() {
		return new TestSuite( DataQueryFactoryTest.class );
	}

	public void testCreateASelectDataQuery() {
		Model object = new TestModel();
		
		assertEquals(DataQuery.QueryType.SELECT, DataQueryFactory.select(object.getClass()).createQuery().getType());
	}

	public void testCreateAInsertDataQuery() {
		Model object = new TestModel();

		assertEquals(DataQuery.QueryType.INSERT, DataQueryFactory.insert(object).createQuery().getType());
	}

	public void testCreateADeleteDataQuery() {
		Model object = new TestModel();

		assertEquals(DataQuery.QueryType.DELETE, DataQueryFactory.delete(object).createQuery().getType());
	}

	public void testCreateAUpdateDataQuery() {
		Model object = new TestModel();

		assertEquals(DataQuery.QueryType.UPDATE, DataQueryFactory.update(object).createQuery().getType());
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

class TestModel extends ModelImpl {
	@ModelId
	private String _id = "5";
	
	@ModelField
	private String _name;
}
