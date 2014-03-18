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
	
	@ModelField("boolean")
	private boolean booleanVariable;
	
	@ModelField("byte")
	private byte byteVariable;
	
	@ModelField("short")
	private short shortVariable;
	
	@ModelField("char")
	private char charVariable;
	
	@ModelField("Object")
	public PrimitiveFieldsModel object;

	public PrimitiveFieldsModel() {
	}
	
	public PrimitiveFieldsModel(int i, long l, float f, double d, boolean b, byte bte, short s, char c) {
		this.intVariable = i;
		this.longVariable = l;
		this.doubleVariable = d;
		this.floatVariable = f;
		this.byteVariable = bte;
		this.shortVariable = s;
		this.booleanVariable = b;
		this.charVariable = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof PrimitiveFieldsModel ) {
			PrimitiveFieldsModel compare = (PrimitiveFieldsModel)obj;
			
			return (compare.longVariable == this.longVariable && 
					compare.intVariable == this.intVariable && 
					(Math.abs((compare.doubleVariable - this.doubleVariable)) < 0.0000000000001) &&
					(Math.abs(compare.floatVariable - this.floatVariable)) < 0.0000000000001f) &&
					(compare.byteVariable == this.byteVariable) &&
					(compare.shortVariable == this.shortVariable) && 
					(compare.booleanVariable == this.booleanVariable) &&
					(compare.charVariable == this.charVariable);
		}
		
		return false;
	}
}