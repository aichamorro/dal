package com.aichamorro.dal.dataquery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetDataQueryResult implements DataQueryResult {
	
	private boolean _isEmpty;
	private ResultSet _resultSet;
	
	public ResultSetDataQueryResult(ResultSet resultSet) throws SQLException {
		assert null != resultSet : "Result set cannot be null";
		
		_resultSet = resultSet;
		_isEmpty = _resultSet.next();
	}

	public boolean isEmpty() {
		return _isEmpty;
	}

	public String getStringForField(String field) {
		try {
			return _resultSet.getString("name");
		} catch (SQLException e) {
			// TODO Proper error handling is needed here
			assert false : "TODO. Handle the exception properly. " + e.toString();
		
			return null;
		}
	}

}
