package com.aichamorro;

import com.aichamorro.dal.model.ModelImpl;
import com.aichamorro.dal.model.annotations.ModelField;
import com.aichamorro.dal.model.annotations.ModelId;
import com.aichamorro.dal.model.annotations.ModelName;

@ModelName("drawings")
public class Drawing extends ModelImpl {
	@ModelId
	private long id;
	
	@ModelField
	private String name;
	
	public Drawing() {
		
	}
	
	public Drawing(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		Drawing compare = (Drawing)obj;
		
		return (compare.id == this.id && compare.name.equals(this.name));
	}
}
