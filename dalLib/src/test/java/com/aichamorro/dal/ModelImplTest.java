package com.aichamorro.dal;

import java.lang.reflect.Field;
import java.util.HashMap;
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
		
		HashMap<String, Field> modelFields = model.getModelFields();
		Set<String> keySet = modelFields.keySet();
		
		assertEquals(2, keySet.size());
		assertTrue(keySet.contains("_id"));
		assertTrue(keySet.contains("_name"));
	}
	
	public void testInheritingModelFields() {
		Model model = new TestModelInherited();
		
		assertEquals("TestModelInherited", model.getModelName());
		assertEquals("_id", model.getIdField().getName());
		
		HashMap<String, Field> modelFields = model.getModelFields();
		Set<String> keySet = modelFields.keySet();
		
		assertEquals(3, keySet.size());
		assertTrue(keySet.contains("_id"));
		assertTrue(keySet.contains("_name"));
		assertTrue(keySet.contains("_surname"));
	}
	
	public void testGetModelInfoWithAModelWithNameFields() {
		Model model = new TestModelWithNames();
		
		assertEquals("ModelWithNames", model.getModelName());
		assertEquals("_id", model.getIdField().getName());
		
		HashMap<String, Field> modelFields = model.getModelFields();
		Set<String> keySet = modelFields.keySet();
		
		assertEquals(2, keySet.size());
		assertTrue(keySet.contains("id"));
		assertEquals("_id", modelFields.get("id").getName());
		
		assertTrue(keySet.contains("name"));
		assertEquals("_name", modelFields.get("name").getName());
	}
	
	public void testGetModelIdWhenIsDuplicatedReturnsTheSpecificClassModelId() {
		Model model = new TestModelInheritedDuplicatingModelIdAndModelFields();
		
		assertEquals("TestModelInheritedDuplicatingModelIdAndModelFields", model.getModelName());
		assertEquals("_idDuplicated", model.getIdField().getName());
	}
	
	public void testModelFieldsWhenAreDuplicatedReturnsTheSpecificClassModelField() {
		Model model = new TestModelInheritedDuplicatingModelIdAndModelFields();
		
		assertEquals("TestModelInheritedDuplicatingModelIdAndModelFields", model.getModelName());
		
		HashMap<String, Field> modelFields = model.getModelFields();
		Set<String> keySet = modelFields.keySet();
		
		assertEquals(2, keySet.size());
		assertFalse(keySet.contains("_id"));
		assertTrue(keySet.contains("_name"));
		assertEquals("_anotherName", modelFields.get("_name").getName());
	}

}

class TestModel extends ModelImpl {
	@ModelId
	private String _id;
	
	@ModelField
	private String _name;
}

class TestModelInherited extends TestModel {
	@ModelField
	private String _surname;
}

class TestModelInheritedDuplicatingModelIdAndModelFields extends TestModel {
	@ModelId
	private int _idDuplicated;
	
	@ModelField("_name")
	private Integer _anotherName;
}

@ModelName("ModelWithNames")
class TestModelWithNames extends ModelImpl {
	@ModelId("id")
	private String _id;
	
	@ModelField("name")
	private String _name;
}
