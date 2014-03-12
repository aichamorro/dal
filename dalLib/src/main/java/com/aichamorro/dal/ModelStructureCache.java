package com.aichamorro.dal;

import java.util.HashMap;

public interface ModelStructureCache {
	static public final String ModelName = "ModelName";
	static public final String ModelId = "ModelId";
	static public final String ModelFields = "ModelFiels";
	
	HashMap<String, Object> getStructureForClass(Class<? extends Model> modelClass);
}
