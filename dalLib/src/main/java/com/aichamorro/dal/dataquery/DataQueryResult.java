package com.aichamorro.dal.dataquery;

import java.util.Iterator;

public interface DataQueryResult {
	boolean isError();
	Object getError();
	Iterator<?> iterator(Class<?> forClass);

	public interface Record {
		public String getStringFor(String name);
		public int getIntegerFor(String name);
		public long getLongFor(String name);
		public double getDoubleFor(String name);
		public float getFloatFor(String name);
	}
}
