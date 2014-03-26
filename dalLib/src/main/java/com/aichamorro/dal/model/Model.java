package com.aichamorro.dal.model;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * States the operations that must be implemented by Models.
 * @author achamorro
 *
 */
public interface Model {
	/**
	 * Returns the Field used as id of the model
	 * @return The Field used as id of the model.
	 */
	Field getIdField();
	/**
	 * Returns the name of all the model's fields.
	 * @return A {@link Set} containing all the names of the model's fields.
	 */
	Set<String> modelFields();
	/**
	 * Gets the data type for a specific model field.
	 * @param modelField 
	 * @return The data type of the requested model field, of null if the model field is not present in the Model.
	 */
	Class<?> getTypeFor(String modelField);
	/**
	 * Returns the domain name of the model.
	 * @return
	 */
	String getModelName();
	/**
	 * Returns the domain name of the field which is used as id in the model.
	 * @return The domain name (can be different from the field's name) of the id field or <tt>null</tt> if there isn't any.
	 */
	String getModelIdName();
	/**
	 * Returns the value of the field which is used as id in the model.
	 * @return The value of the field which is used as id in the model, or null if there is no id field.
	 */
	Object getModelIdValue();
	/**
	 * Replaces the current value of a model's field by the specified value. 
	 * The type of the field must be compatible with the value provided. 
	 * @param modelField Domain name of the field. Must exists within the model.
	 * @param value Value to set the current value with. The type must be compatible with the field's type.
	 * @return <tt>true</tt> if the set operation could be completed successfully, <tt>false</tt>otherwise.
	 */
	boolean set(String modelField, Object value);
	/**
	 * Returns the value associated to a model's field.
	 * @param modelField The domain name of the field. Must exist within the model.
	 * @return The value associated to the specified model's field.
	 */
	Object get(String modelField);
}
