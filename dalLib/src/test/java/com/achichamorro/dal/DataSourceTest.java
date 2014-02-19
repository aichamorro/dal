package com.aichamorro.dal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import com.aichamorro.dal.datasource.DataSource;

public class DataSourceTest extends TestCase {
	public DataSourceTest(String testName) {
		super( testName );
	}

	public Test suite() {
		return new TestSuite(DataSourceTest.class);
	}

	public void testSelectQuery() {
	}
}
