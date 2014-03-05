package com.aichamorro.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.adapters.ResultSetAdapter;
import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;

public class SqlConnector {
	private final String dbProtocol = "jdbc:mysql://";
	private String _url;
	private String _username;
	private String _password;
	private Connection _connection;

	public SqlConnector(String host, String database, String username) {
		this(host, database, username, "");
	}
	
	public SqlConnector(String host, String database, String username, String password) {
		assert null != host && !host.isEmpty() : "Host cannot be neither null nor empty";
		assert null != database && !database.isEmpty() : "database cannot be neither null nor empty";
		assert null != username && !username.isEmpty() : "username cannot be neither null nor empty";

		StringBuilder sb = new StringBuilder(dbProtocol);
			sb.append(host);
			sb.append("/");
			sb.append(database);
			
		_url = sb.toString();
		
		// TODO I am a bad guy, I know. I must make this more secure and I will, I promise
		_username = username;
		_password = password;
		
		try {
			_connection = DriverManager.getConnection(_url, _username, _password);
		} catch (SQLException e) {
			// TODO call a delegate or throw an exception perhaps?
			assert false : "Do something with the error";
		}
	}

	public String getUrl() {
		return _url;
	}

	public void executeQuery(DataQuery dataQuery) throws SQLException {
		assert _connection != null : "The connection has not been initialized!!!";
		
		_connection.createStatement().executeQuery(new SqlDataQueryAdapter().objectForQuery(dataQuery));
	}

	public void close() throws SQLException {
		_connection.close();
	}

}
