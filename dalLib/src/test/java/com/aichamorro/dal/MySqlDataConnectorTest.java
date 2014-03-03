package com.aichamorro.dal;

import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit3.PowerMockSuite;

import com.mysql.jdbc.Connection;

import static org.mockito.Mockito.*;

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

		new SqlConnector("localhost", "sampleDatabase", "test", "");
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost/sampleDatabase", "test", "");
	}
	
	public void testCreateMysqlConectionWithSpecificPort() throws SQLException {
		PowerMockito.mockStatic(DriverManager.class);

		new SqlConnector("localhost:3333", "sampleDatabase", "test", "");
		
		PowerMockito.verifyStatic();
		DriverManager.getConnection("jdbc:mysql://localhost:3333/sampleDatabase", "test", "");
	}
}
