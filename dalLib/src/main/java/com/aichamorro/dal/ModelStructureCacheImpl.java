package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;
import com.aichamorro.dal.dataquery.annotations.ModelName;

public class ModelStructureCacheImpl implements ModelStructureCache {
	HashMap<Class<?>, HashMap<String, Object>> _cache;
	
	static private class InstanceHolder {
		static final ModelStructureCacheImpl _instance = new ModelStructureCacheImpl();
	}
	
	static public ModelStructureCacheImpl getInstance() {
		return InstanceHolder._instance;
	}
	
	private ModelStructureCacheImpl() {
		_cache = new HashMap<Class<?>, HashMap<String,Object>>();
	}
	
	public HashMap<String, Object> getStructureForClass(Class<? extends Model> modelClass) {
		if(! _cache.containsKey(modelClass) ) {
			cacheStructure(modelClass);
		}
		
		return _cache.get(modelClass);
	}
	
	private String getModelNameFromClass(Class<?> modelClass) {
		ModelName annotation = modelClass.getAnnotation(ModelName.class);
		
		return ( null != annotation && !annotation.value().isEmpty() ) ? annotation.value() : modelClass.getSimpleName();
	}
	
	private void populateDeclaredFields(Class<?> modelClass, HashMap<String, Field> modelFields, ArrayList<Field> idField) {
		if( null != modelClass) {
			// Fill from the top of the hierarchy. The duplicated ModelFields that will remain will be the
			// ones which are more specific in the hierarchy.
			populateDeclaredFields(modelClass.getSuperclass(), modelFields, idField);
			
			for( Field f : modelClass.getDeclaredFields() ) {
				String annotationValue = null;
				
				if( f.isAnnotationPresent(ModelId.class) ) {
					annotationValue = f.getAnnotation(ModelId.class).value();
					
					assert idField.isEmpty() : "You have more than one ModelId annotation in your model. That's incorrect.";
					
					if(idField.size() > 0) {
						Field duplicatedIdField = idField.remove(0);
						
						modelFields.values().remove(duplicatedIdField);
					}
						
					idField.add(f);
				}else if( f.isAnnotationPresent(ModelField.class) ) {
					annotationValue = f.getAnnotation(ModelField.class).value();
				}else{
					continue;
				}

				String name = (null == annotationValue || annotationValue.isEmpty()) ? f.getName() : annotationValue;
				assert !modelFields.containsKey(name) : "This modelField name it's been overridden";

				modelFields.put(name, f);
			}
		}
	}
	
	private void cacheStructure(Class<? extends Model> modelClass) {
		assert !_cache.containsKey(modelClass) : "This class has been cached already.";
		
		HashMap<String, Object> structure = new HashMap<String, Object>();
		structure.put(ModelName, getModelNameFromClass(modelClass));

		HashMap<String, Field> modelFields = new HashMap<String, Field>();
		ArrayList<Field> idField = new ArrayList<Field>();
		populateDeclaredFields(modelClass, modelFields, idField);
		structure.put(ModelId, idField.get(0));
		
		assert null != idField : "You don't have any ModelId annotation in your model. That's incorrect and you must specify one";
		structure.put(ModelFields, modelFields);
		
		_cache.put(modelClass, structure);
	}
}
