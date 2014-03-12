package com.aichamorro.dal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.DriverManager;
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
import com.aichamorro.dal.dataquery.Queryable;
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
	
	public void testCreateAMysqlConnection() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);
		
		new SqlConnector("localhost", "sampleDatabase", "test");
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost/sampleDatabase", "test", "");
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
	
	public void testExecuteSelectQuery() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		
		when(DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		
		DataQuery dataQuery = DataQueryFactory.select(TestModel.class).createQuery();
		SqlConnector connector = new SqlConnector("localhost:8888", "sampleDatabase", "test", "");
			connector.executeQuery(dataQuery);
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		verify(mockConnection).createStatement();
		verify(mockStatement).executeQuery("SELECT * FROM TestModel");
		
		// TODO Should the database closed? I think so
	}
	
	private class TestModel implements Queryable<Long>{
		public Long getId() {
			return 10L;
		}
	}
}
