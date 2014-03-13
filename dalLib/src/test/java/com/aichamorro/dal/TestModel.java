package com.aichamorro.dal;

import com.aichamorro.dal.model.ModelImpl;
import com.aichamorro.dal.model.annotations.ModelField;
import com.aichamorro.dal.model.annotations.ModelId;

public class TestModel extends ModelImpl {
	@ModelId
	private String _id;
	
	@ModelField
	private String _name;
	
	public TestModel() {
		this(null, null);
	}
	
	public TestModel(String id, String name) {
		_id = id;
		_name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof TestModel ) {
			TestModel compare = (TestModel)obj;
			
			return (compare._id.equals(this._id) && compare._name.equals(this._name));
		}
		
		return false;
	}
}
