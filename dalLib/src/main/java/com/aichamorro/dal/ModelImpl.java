package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;

abstract public class ModelImpl implements Model {
	public ModelImpl() {
	}
	
	public Field getIdField() {
		return (Field)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelId);
	}
	
	public Class<?> getTypeFor(String modelField) {
		HashMap<String, Field> fields = getModelFields();
		
		assert fields.containsKey(modelField) : "The model field requested does not belong to this Model.";

		return fields.get(modelField).getType();
	}
	
	public HashMap<String, Field> getModelFields() { // includes the ModelId
		return (HashMap<String, Field>)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelFields);
	}
	
	public String getModelName() {
		return (String)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelName);
	}
	
	public String getModelIdName() {
		Field idField = getIdField();
		ModelId annotation = idField.getAnnotation(ModelId.class);
		
		return (annotation.value().equals("") ? idField.getName() : annotation.value());
	}
	
	public Object getModelIdValue() {
		Object result = null;
		
		try {
			Field idField = getIdField();
			boolean isAccessbile = idField.isAccessible();

			idField.setAccessible(true);
			result = idField.get(this);

			idField.setAccessible(isAccessbile);
		} catch (IllegalArgumentException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		}
		
		return result;
	}
}
