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

import com.aichamorro.Drawing;
import com.aichamorro.dal.connectors.SqlConnector;
import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQueryFactory;
import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;
import com.aichamorro.dal.dataquery.result.DataQueryResult;
import com.aichamorro.dal.dataquery.result.iterators.DataQueryResultIterator;
import com.mysql.jdbc.Connection;

@PrepareForTest(SqlConnector.class)
public class MySqlDataConnectorTest extends TestCase {
	final SqlDataQueryAdapter queryAdapter = new SqlDataQueryAdapter();

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

		new SqlConnector("localhost:3333", "sampleDatabase", "test", "", queryAdapter);
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost:3333/sampleDatabase", "test", "");
	}
	
	public void testCloseSqlConnection() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);
		Connection mockConnection = mock(Connection.class);
		
		when(DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockConnection);
		
		SqlConnector connector = new SqlConnector("localhost:8888", "sampleDatabase", "test", "", queryAdapter);
			connector.close();
			
		PowerMockito.verifyStatic();
		DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		verify(mockConnection).close();
	}
	
	public void testErrorDataQueryResult() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);
		Connection mockConnection = mock(Connection.class);
		Statement mockStatement = mock(Statement.class);
		
		when(DriverManager.getConnection("jdbc:mysql://localhost:8888/sampleDatabase", "test", "")).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.execute("SELECT * FROM `TestModel`")).thenThrow(new SQLException("An exception occurred"));
		
		DataQuery dataQuery = DataQueryFactory.select(TestModel.class).createQuery();
		SqlConnector connector = new SqlConnector("localhost:8888", "sampleDatabase", "test", "", queryAdapter);
		DataQueryResult result = connector.executeQuery(dataQuery);
		DataQueryResultIterator<TestModel> iterator = result.iterator(TestModel.class);
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost:8888/sampleDatabase", "test", "");
		
		verify(mockConnection).createStatement();
		verify(mockStatement).execute("SELECT * FROM `TestModel`");
		
		assertTrue(result.isError());
		assertNull(iterator.next());	
	}
	
	public void testCrudProcess() throws SQLException {		
		SqlConnector connector = new SqlConnector("localhost:3306", "rocxis", "rocxis", queryAdapter);
		Drawing drawing = new Drawing(-1, "Alberto");
		DataQuery queryInsert = DataQueryFactory.insert(drawing).createQuery();
		DataQuery querySelect = DataQueryFactory.select(Drawing.class).where("`name`='Alberto'").createQuery();
		DataQuery querySelectUpdated = DataQueryFactory.select(Drawing.class).where("`name`='Alberto Chamorro'").createQuery();
		
		DataQueryResult result = connector.executeQuery(queryInsert);
			assertFalse(result.isError());
			assertNull(result.iterator(Drawing.class).next());
		
		result = connector.executeQuery(querySelect);
			assertFalse(result.isError());
		
		Drawing selectResult = result.iterator(Drawing.class).next();
			assertNotNull(selectResult);
			assertTrue(-1 != selectResult.getId());
			assertEquals("Alberto", selectResult.getName());
		
		selectResult.set("name", "Alberto Chamorro");
		DataQuery queryUpdate = DataQueryFactory.update(selectResult).createQuery();
		result = connector.executeQuery(queryUpdate);
			assertFalse(result.isError());
			
		result = connector.executeQuery(querySelectUpdated);
			assertFalse(result.isError());
			assertNotNull(result.iterator(Drawing.class).next());
			
		DataQuery queryDelete = DataQueryFactory.delete(selectResult).createQuery();
		result = connector.executeQuery(queryDelete);
			assertFalse(result.isError());
			assertNull(result.iterator(Drawing.class).next());
			
		result = connector.executeQuery(querySelectUpdated);
			assertFalse(result.isError());
			assertNull(result.iterator(Drawing.class).next());
			
		connector.close();
	}
}
