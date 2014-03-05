package com.aichamorro.dal.dataquery;

import java.sql.SQLException;

public class FailedDataQueryResult implements DataQueryResult {

	public FailedDataQueryResult(SQLException e) {
	}

	public boolean isEmpty() {
		return true;
	}

	public String getStringForField(String field) {
		return null;
	}

}
