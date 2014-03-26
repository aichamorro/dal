package com.aichamorro.dal.dataquery;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.aichamorro.dal.model.Model;
import com.aichamorro.dal.model.ModelImpl;

/**
 * <p>DataQuery is an abstraction of a data action.</p>
 * <p>A data action can be:
 * <ul>
 * <li><b>Create</b>: Create a record in a data source. Are identified as <tt>insert</tt> query.</li>
 * <li><b>Delete</b>: Delete a specific record from a data source. Are identified as <tt>delete</tt> query.</li>
 * <li><b>Update</b>: Modify a specific record from a data source. Are identified as <tt>update</tt> query.</li>
 * <li><b>Read</b>: Read records (one or more) from a data source. Are identified as <tt>select</tt> query.</li>
 * </ul></p>
 * <p>The names of the types for data actions has been chosen for similarity with SQL query statements. This does 
 * not mean the uses of DataQuery are the same of SQL statements.</p>
 * <p>The DataQuery must always be associated to a {@link com.aichamorro.dal.model.Model}.</p>
 * <p>DataQuery must be created through a {@link DataQueryFactory}, using one of the following methods:
 * <ul>
 * <li>{@link DataQueryFactory#select}</li>
 * <li>{@link DataQueryFactory#update}</li>
 * <li>{@link DataQueryFactory#delete}</li>
 * <li>{@link DataQueryFactory#insert}</li>
 * </ul></p>
 * <p>Examples of creation of <tt>select</tt> DataQuery would be:
 * <pre>DataQuery selectQuery = DataQueryFactory.select(TestModel.class).createQuery();
 * TestModel newTestModelInstance = createAndInitializeTestModel();
 * TestModel testModelObtainedFromSelect = /* get the test model from the data source *{@literal /}
 * 
 * DataQuery insertQuery = DataQueryFactory.insert(newTestModelInstance).createQuery();
 * DataQuery deleteQuery = DataQueryFactory.delete(testModelObtainedFromSelect).createQuery();
 * DataQuery updateQuery = DataQueryFactory.update(testModelObtainedFromSelect).createQuery();</pre></p>
 * <p>Filters can be applied, but only on <tt>select</tt> queries. An example of filtering:
 * <pre>DataQuery selectQuery = DataQueryFactory.select(TestModel.class).where("name='John Doe'").createQuery();</pre></p>
 * @author achamorro
 * @see DataQueryFactory
 */
public class DataQuery {
	public enum QueryType {
		SELECT,
		INSERT,
		UPDATE,
		DELETE;
	};
	
	QueryType _queryType;
	Model _payload;
	DataQueryFilter _where;
	Class<? extends Model> _modelClass;
	
	private DataQuery(QueryType queryType, DataQueryFilter where) {
		_queryType = queryType;
		_where = where;
	}
	DataQuery(QueryType queryType, Class<? extends Model> modelClass, DataQueryFilter where) {
		this(queryType, where);
		
		_payload = null;
		_modelClass = modelClass;
	}
	
	DataQuery(QueryType queryType, Model object, DataQueryFilter where) {
		this(queryType, where);

		_payload = object;
		_modelClass = object.getClass();
	}

	public QueryType getType() {
		return _queryType;
	}
	
	private HashMap<String, String> payloadAsHashMap() {
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		
		for( String name : _payload.modelFields() ) {
			result.put(name, _payload.get(name).toString());
		}
		
		result.remove(_payload.getModelIdName());
		
		return result;
	}
	
	public void visit(DataQueryVisitor visitor) {
		visitor.setType(_queryType);
		visitor.setModel(ModelImpl.getModelNameFromClass(_modelClass));	
		if( null != _payload ) { visitor.setPayload(payloadAsHashMap()); }
		if( null != _where ) { visitor.addFilter(_where.iterator()); }
	}
}
