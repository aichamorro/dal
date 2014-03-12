package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.util.HashMap;

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
}
