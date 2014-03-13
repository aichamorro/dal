package com.aichamorro.dal;

import com.aichamorro.dal.model.ModelImpl;
import com.aichamorro.dal.model.annotations.ModelField;
import com.aichamorro.dal.model.annotations.ModelId;

public class PrimitiveFieldsModel extends ModelImpl {
	@ModelId("int")
	private int intVariable;
	
	@ModelField("double")
	private double doubleVariable;
	
	@ModelField("long")
	private long longVariable;

	@ModelField("float")
	private float floatVariable;
	
	@ModelField("Object")
	public PrimitiveFieldsModel object;

	public PrimitiveFieldsModel() {
	}
	
	public PrimitiveFieldsModel(int i, long l, float f, double d) {
		this.intVariable = i;
		this.longVariable = l;
		this.doubleVariable = d;
		this.floatVariable = f;
		this.doubleVariable = d;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof PrimitiveFieldsModel ) {
			PrimitiveFieldsModel compare = (PrimitiveFieldsModel)obj;
			
			return (compare.longVariable == this.longVariable && compare.intVariable == this.intVariable && 
					(Math.abs((compare.doubleVariable - this.doubleVariable)) < 0.0000000000001) &&
					(Math.abs(compare.floatVariable - this.floatVariable)) < 0.0000000000001f);
		}
		
		return false;
	}
}