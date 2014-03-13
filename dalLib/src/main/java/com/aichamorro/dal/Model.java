package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.util.HashMap;

public interface Model {
	Field getIdField();
	HashMap<String, Field> getModelFields();
	Class<?> getTypeFor(String modelField);
	String getModelName();
	String getModelIdName();
	Object getModelIdValue();
}
