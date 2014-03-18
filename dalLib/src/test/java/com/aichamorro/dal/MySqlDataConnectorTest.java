package com.aichamorro.dal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Test;
import junit.framework.TestCase;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit3.PowerMockSuite;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQueryFactory;
import com.aichamorro.dal.dataquery.result.DataQueryResult;
import com.aichamorro.dal.dataquery.result.DataQueryResultIterator;
import com.mysql.jdbc.Connection;

@PrepareForTest(SqlConnector.class)
public class MySqlDataConnectorTest extends TestCase {
	public MySqlDataConnectorTest(String suiteName) {
		super( suiteName );
	}
	
	public static Test suite() throws Exception {
		return new PowerMockSuite(MySqlDataConnectorTest.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testCreateMysqlConectionWithSpecificPort() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);

		new SqlConnector("localhost:3333", "sampleDatabase", "test", "");
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost:3333/sampleDatabase", "test", "");
	}
	
	public void testCloseSqlConnection() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);
		Connection mockConnection = mock(Connection.class);
		
		when(DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockConnection);
		
		SqlConnector connector = new SqlConnector("localhost:8888", "sampleDatabase", "test", "");
			connector.close();
			
		PowerMockito.verifyStatic();
		DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		verify(mockConnection).close();
	}
	
	public void testExecuteSelectDataQuery() throws SQLException {
		final String FAKE_ID_1 = "ACB001";
		final String FAKE_ID_2 = "ACB002";
		
		PowerMockito.mockStatic(DriverManager.class);
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		ResultSet mockResultSet = mock(ResultSet.class);
		
		when(DriverManager.getConnection("jdbc:mysql://localhost:8888/sampleDatabase", "test", "")).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.executeQuery("SELECT * FROM TestModel")).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true, true, false);
		when(mockResultSet.getString("_id")).thenReturn(FAKE_ID_1, FAKE_ID_2);
		when(mockResultSet.getString("_name")).thenReturn("Alberto", "Paul");
		
		DataQuery dataQuery = DataQueryFactory.select(TestModel.class).createQuery();
		SqlConnector connector = new SqlConnector("localhost:8888", "sampleDatabase", "test", "");
		DataQueryResult result = connector.executeQuery(dataQuery);
		DataQueryResultIterator iterator = result.iterator(TestModel.class);
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost:8888/sampleDatabase", "test", "");
		
		verify(mockConnection).createStatement();
		verify(mockStatement).executeQuery("SELECT * FROM TestModel");
		
		assertFalse(result.isErrorResult());
		assertEquals(new TestModel("ACB001", "Alberto"), iterator.next());
		assertEquals(new TestModel("ACB002", "Paul"), iterator.next());
		assertNull(iterator.next());
	}
	
	public void testErrorDataQueryResult() {
		fail("Not Implemented");
	}
}
