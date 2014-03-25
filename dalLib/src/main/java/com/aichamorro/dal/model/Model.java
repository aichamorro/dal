package com.aichamorro.dal.model;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Interface that must be implemented by all data models 
 * in the application used with the DataAccessLayer.
 * @author achamorro
 *
 */
public interface Model {
	Field getIdField();
	Set<String> modelFields();
	Class<?> getTypeFor(String modelField);
	String getModelName();
	String getModelIdName();
	Object getModelIdValue();
	boolean set(String modelField, Object value);
	Object get(String modelField);
}
