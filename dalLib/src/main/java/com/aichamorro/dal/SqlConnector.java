package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import com.aichamorro.dal.dataquery.DataQuery;
import com.aichamorro.dal.dataquery.DataQueryResult;
import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;
import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;

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

	public DataQueryResult executeQuery(DataQuery dataQuery) {
		assert _connection != null : "The connection has not been initialized!!!";
		
		String sqlQuery = new SqlDataQueryAdapter().objectForQuery(dataQuery);
		try {
			ResultSet resultSet = _connection.createStatement().executeQuery(sqlQuery);
			
			return new ResultSetDataQueryResult(resultSet);
		} catch (SQLException e) {
			assert false : "Exception occurred: " + e.toString();
		
			return new ErrorDataQueryResult(e.toString());
		}
	}

	public void close() throws SQLException {
		_connection.close();
	}
}