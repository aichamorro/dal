package com.aichamorro.dal.dataquery.adapters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataQueryResultTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(DataQueryResultTest.class.getName());
		//$JUnit-BEGIN$

		//$JUnit-END$
		return suite;
	}
	
	public void testEmptyDataQueryResult() {
		fail("Not implemented");
	}
	
//	public void testDataQueryResultWithOneRecord() {
//		DataQueryResult result = mock(DataQueryResult.class);
//		DataQueryResult.Record record = mock(DataQueryResult.Record.class);
//		
//		when(record.getStringFor("name")).thenReturn("Alberto");
//		when(result.hasNext()).thenReturn(true);
//		when(result.next()).thenReturn(record);
//
//		assertFalse(result.hasNext());
//	}

}
