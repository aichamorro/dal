package com.aichamorro.dal.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import com.aichamorro.dal.model.annotations.ModelId;

/**
 * <p>Simple implementation of the {@link Model} interface.</p>
 * <p>This implementation caches the structure of the model
 * when it is requested calling {@link #getModelFields} method.</p>
 * <p>Every model that extends from {@code ModelImpl}, can use the 
 * annotations {@link com.aichamorro.dal.model.annotations.ModelField}, 
 * {@link com.aichamorro.dal.model.annotations.ModelId}, 
 * {@link com.aichamorro.dal.model.annotations.ModelName} to describe 
 * the model's structure. For example,</p>
 * <pre>
 * <code>
 * {@literal @}ModelName("ModelAsIsKnownByTheDataSource")
 * class TestModel extends ModelImpl {
 *     {@literal @}ModelId("IdFieldAsKnownByTheDataSource")
 *     private String _id;
 *     
 *     {@literal @}ModelField("ModelFieldNameAsKnownByTheDataSource")
 *     private String _simpleField;
 * }
 * </code>
 * </pre>
 * <p>describes a model named <tt>ModelAsKnownByTheDataSource</tt> which
 * has two fields: 
 * <ul>
 * <li><tt>IdFieldAsKnownByTheDataSource</tt> (will be used as filter when retrieving
 * specific models from the DataSource).</li>
 * <li><tt>ModelFieldNameAsKnwonByTheDataSource</tt></li>
 * </ul>
 * </p>
 * If we think about a database DataSource scenario, we would have the table 
 * <tt>ModelAsIsKnownByTheDataSource</tt> where every record is composed by 
 * two fields: <tt>IdFieldAsKnownByTheDataSource</tt> and 
 * <tt>ModelFieldNameAsKnwonByTheDataSource</tt>.
 * @see com.aichamorro.dal.model.annotations.ModelField
 * @see com.aichamorro.dal.model.annotations.ModelId
 * @see com.aichamorro.dal.model.annotations.ModelName
 * @author achamorro
 */
abstract public class ModelImpl implements Model {
	/**
	 * Constructor
	 */
	public ModelImpl() {
	}
	
	/**
	 * Obtains the Field associated to a modelField.
	 * @param modelField The name of a model's field.
	 * @return The Field associated to the modelField requested or null if there is no Field associated.
	 */
	private Field getField(String modelField) {
		return getModelFields().get(modelField);
	}
	
	/**
	 * Obtains the whole structure of the Model. It reads the information from a cache system.
	 * @return A HashMap containing all the modelFields of the Model (keys) and their associated fields (values).
	 */
	private HashMap<String, Field> getModelFields() { // includes the ModelId
		return (HashMap<String, Field>)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelFields);
	}
	
	/**
	 * Get the field used as id in the model. 
	 * The id field is identified by a {@link com.aichamorro.dal.model.annotations.ModelId} 
	 * annotation. For instance,
	 * <pre>
	 * {@literal @}ModelId
	 * private String _modelId;</pre>
	 * @return The field used as model's id or <tt>null</tt> if there isn't any.
	 */
	public Field getIdField() {
		return (Field)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelId);
	}
	
	/**
	 * Gets the data type for a specific model field.
	 * @param modelField 
	 * @return The data type of the requested model field, of null if the model field is not present in the Model.
	 */
	public Class<?> getTypeFor(String modelField) {
		HashMap<String, Field> fields = getModelFields();
		
		assert fields.containsKey(modelField) : "The model field requested does not belong to this Model.";

		return fields.get(modelField).getType();
	}
	
	/**
	 * Gets the names of all the model fields of the Model. This includes the annotated @ModelField as well as the @ModelId.
	 * @return A Set with all the model fields' names of the Model.
	 */
	public Set<String> modelFields() {
		return getModelFields().keySet();
	}
	
	/**
	 * Gets the model name of the Model. If there is a @ModelName annotation to the class, then 
	 * the value of the annotation will be used as name. In case there is no annotation or the annotation
	 * is empty, the name of the class is used as model name.
	 */
	public String getModelName() {
		return (String)ModelStructureCacheImpl.getInstance().getStructureForClass(getClass()).get(ModelStructureCache.ModelName);
	}
	
	/**
	 * Get the value of a model's field.
	 * @param modelField The name of the field which value is requested.
	 * @return The value of the requested model's field.
	 */
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
	
	/**
	 * Set the value a model field to a value.
	 * @param modelField The name of the model's field to be set. It must exist in the model's structure.
	 * @param value The value used to set the model's field. The value must be compatible with the type of the field.
	 * @return <tt>true</tt> if the operation could be finished normally, <tt>false</tt> otherwise.
	 */
	public boolean set(String modelField, Object value) {
		Field field = getField(modelField);

		assert field.getType().isAssignableFrom(value.getClass()) : "Incompatible types :(";
		try {
			boolean isAccessible = field.isAccessible();
			
			field.setAccessible(true);
			field.set(this, value);
			field.setAccessible(isAccessible);
			
			return true;
		} catch (IllegalArgumentException e) {
			assert false : "Exception: " + e.toString();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			assert false : "Excetpion: " + e.toString();
			e.printStackTrace();
		} finally {
			// TODO Restore the value of isAccessible in field in case an Exception occurs
			// during the set operation.
		}
		
		return false;
	}
	
	/**
	 * The name of the field used as id. That field is identified by a {@link com.aichamorro.dal.model.annotations.ModelId} 
	 * annotation. For instance,
	 * <pre>{@literal @}ModelId("id")
	 * private String _modelId;</pre>
	 * In this particular case, <b>id</b> is the name of the model's id field. In case there is no value provided by the 
	 * {@link com.aichamorro.dal.model.annotations.ModelId} annotation, then the name of the field is used. In the example,
	 * <b>_modelId</b>.
	 * @return The name of the field used as the id field of the model. 
	 */
	public String getModelIdName() {
		Field idField = getIdField();
		ModelId annotation = idField.getAnnotation(ModelId.class);
		
		return (annotation.value().equals("") ? idField.getName() : annotation.value());
	}
	
	/**
	 * Get the value of the id model field.
	 * @return The value of the id model field.
	 */
	public Object getModelIdValue() {
		return get(getModelIdName());
	}
}
