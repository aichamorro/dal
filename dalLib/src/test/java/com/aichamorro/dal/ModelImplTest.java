package com.aichamorro.dal;

import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.dataquery.annotations.ModelField;
import com.aichamorro.dal.dataquery.annotations.ModelId;
import com.aichamorro.dal.dataquery.annotations.ModelName;

public class ModelImplTest extends TestCase {
	public static Test suite() {
		return new TestSuite(ModelImplTest.class);
	}

	public void testGetStructureForASimpleModel() {
		Model model = new TestModel();
		
		assertEquals("_id", model.getIdField().getName());
		assertEquals("TestModel", model.getModelName());
		
		Set<String> modelFields = model.modelFields();
		
		assertEquals(2, modelFields.size());
		assertTrue(modelFields.contains("_id"));
		assertTrue(modelFields.contains("_name"));
	}
	
	public void testInheritingModelFields() {
		Model model = new TestModelInherited();
		
		assertEquals("TestModelInherited", model.getModelName());
		assertEquals("_id", model.getIdField().getName());
		
		Set<String> modelFields = model.modelFields();
		
		assertEquals(3, modelFields.size());
		assertTrue(modelFields.contains("_id"));
		assertTrue(modelFields.contains("_name"));
		assertTrue(modelFields.contains("_surname"));
	}
	
	public void testGetModelInfoWithAModelWithNameFields() {
		Model model = new TestModelWithNames();
		
		assertEquals("ModelWithNames", model.getModelName());
		assertEquals("_id", model.getIdField().getName());
		
		Set<String> modelFields = model.modelFields();
		
		assertEquals(2, modelFields.size());
		assertTrue(modelFields.contains("id"));
		assertEquals("5", model.get("id"));
		
		assertTrue(modelFields.contains("name"));
		assertEquals("Alberto", model.get("name"));
	}
	
	public void testGetModelIdWhenIsDuplicatedReturnsTheSpecificClassModelId() {
		Model model = new TestModelInheritedDuplicatingModelIdAndModelFields();
		
		assertEquals("TestModelInheritedDuplicatingModelIdAndModelFields", model.getModelName());
		assertEquals("_idDuplicated", model.getIdField().getName());
	}
	
	public void testModelFieldsWhenAreDuplicatedReturnsTheSpecificClassModelField() {
		Model model = new TestModelInheritedDuplicatingModelIdAndModelFields();
		
		assertEquals("TestModelInheritedDuplicatingModelIdAndModelFields", model.getModelName());
		
		Set<String> modelFields = model.modelFields();
		
		assertEquals(3, modelFields.size());
		assertTrue(modelFields.contains("_id"));
		assertTrue(modelFields.contains("_name"));
		assertTrue(modelFields.contains("_idDuplicated"));
		assertEquals(100, model.get("_name"));
	}

	public void testModelIdWhenAreDuplicatedInTheSameClass() {
		Model model = new TestModelWithDuplicatedIdInTheClass();
		
		Set<String> modelFields = model.modelFields();

		assertEquals(2, modelFields.size());
		assertTrue(modelFields.contains("_id"));
		assertTrue(modelFields.contains("_anotherId"));
	}
}

class TestModelWithDuplicatedIdInTheClass extends ModelImpl {
	@ModelId
	private String _id;
	
	@ModelId
	private int _anotherId;
}

class TestModelWithDuplicatedModelFieldsInTheClass extends ModelImpl {
	@ModelField
	private String name;
	
	@ModelField("name")
	private String differentName;
}

class TestModelInherited extends TestModel {
	@ModelField
	private String _surname;
}

class TestModelInheritedDuplicatingModelIdAndModelFields extends TestModel {
	@ModelId
	private Integer _idDuplicated = 5;
	
	@ModelField("_name")
	private Integer _anotherName = 100;
}

@ModelName("ModelWithNames")
class TestModelWithNames extends ModelImpl {
	@ModelId("id")
	private String _id = "5";
	
	@ModelField("name")
	private String _name = "Alberto";
}
