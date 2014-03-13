package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import com.aichamorro.dal.dataquery.annotations.ModelId;

abstract public class ModelImpl implements Model {
	public ModelImpl() {
	}
	
	private Field getField(String modelField) {
		return getModelFields().get(modelField);
	}
	
	private HashMap<String, Field> getModelFields() { // includes the ModelId
		return (HashMap<String, Field>)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelFields);
	}
	
	public Field getIdField() {
		return (Field)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelId);
	}
	
	public Class<?> getTypeFor(String modelField) {
		HashMap<String, Field> fields = getModelFields();
		
		assert fields.containsKey(modelField) : "The model field requested does not belong to this Model.";

		return fields.get(modelField).getType();
	}
	
	public Set<String> modelFields() {
		return getModelFields().keySet();
	}
	
	public String getModelName() {
		return (String)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelName);
	}
	
	public Object get(String modelField) {
		Object result = null;
		try {
			Field idField = getField(modelField);
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
	
	public boolean set(String modelField, Object value) {
		Field field = getField(modelField);

		assert field.getType().isAssignableFrom(value.getClass()) : "Incompatible types :(";
		try {
			boolean isAccessible = field.isAccessible();
			
			field.setAccessible(true);
			field.set(modelField, value);
			field.setAccessible(isAccessible);
			
			return true;
		} catch (IllegalArgumentException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assert false : "Excetpion: " + e.toString();
			e.printStackTrace();
		}
		
		return false;
	}
	
	public String getModelIdName() {
		Field idField = getIdField();
		ModelId annotation = idField.getAnnotation(ModelId.class);
		
		return (annotation.value().equals("") ? idField.getName() : annotation.value());
	}
	
	public Object getModelIdValue() {
		return get(getModelIdName());
	}
}
