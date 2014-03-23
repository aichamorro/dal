package com.aichamorro.dal.dataquery;

import static com.aichamorro.dal.dataquery.DataQueryFilterFactory.and;
import static com.aichamorro.dal.dataquery.DataQueryFilterFactory.or;
import static com.aichamorro.dal.dataquery.DataQueryFilterFactory.statement;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;
import com.aichamorro.dal.model.Model;
import com.aichamorro.dal.model.ModelImpl;
import com.aichamorro.dal.model.annotations.ModelField;
import com.aichamorro.dal.model.annotations.ModelId;
import com.aichamorro.dal.model.annotations.ModelName;

public class SqlDataQueryAdapterTest extends TestCase{
	public SqlDataQueryAdapterTest( String suiteName ) {
		super( suiteName );
	}

	public static Test suite() {
		return new TestSuite(SqlDataQueryAdapterTest.class);
	}
	
	SqlDataQueryAdapter adapter;
	Model object;
	String objectClass;
	
	public void setUp() {
		adapter = new SqlDataQueryAdapter();
		objectClass = MockModelForInsert.class.getSimpleName();
		object = new MockModelForInsert();
	}
	
	public void tearDown() {
		adapter = null;
		object = null;
		objectClass = null;
	}

	public void testSelectQueryWithNoFilterReturnsSelectAllStatement() {
		DataQueryFactory query = DataQueryFactory.select(object.getClass());
		
		assertEquals("SELECT * FROM `Queryable`", adapter.objectForQuery(query.createQuery())); 
	}
	
	public void testSelectQueryWithFilterReturnsSelectWithWhereStatement() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where("id='5'").createQuery();
		
		assertEquals("SELECT * FROM `Queryable` WHERE id='5'", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithTwoFiltersUsingAndConcatenation() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(and("id='5'", "name='Alberto'")).createQuery();	
		
		assertEquals("SELECT * FROM `Queryable` WHERE (id='5' AND name='Alberto')", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithTwoFiltersUsingOrConcatenation() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(or("id='5'", "name='Alberto'")).createQuery();
		
		assertEquals("SELECT * FROM `Queryable` WHERE (id='5' OR name='Alberto')", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithMoreThanTwoFiltersUsingSeveralConcatenators() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(or(and("id='5'", "name='Alberto'"),statement("surname='Chamorro'"))).createQuery();
		
		assertEquals("SELECT * FROM `Queryable` WHERE ((id='5' AND name='Alberto') OR surname='Chamorro')", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithFilterGroupsNotInvolvingWhere() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(and(statement("id='5'"), or("name='Alberto'", "surname='Chamorro'"))).createQuery();
		
		assertEquals("SELECT * FROM `Queryable` WHERE (id='5' AND (name='Alberto' OR surname='Chamorro'))", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithFilterGroups() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(or(and("id='5'", "name='Alberto'"), statement("surname='Chamorro'"))).createQuery();

		assertEquals("SELECT * FROM `Queryable` WHERE ((id='5' AND name='Alberto') OR surname='Chamorro')", adapter.objectForQuery(query));
	}
	
	public void testSimpleDeleteQuery() {
		DataQuery query = DataQueryFactory.delete(new MockModelForInsert()).createQuery();
		
		assertEquals("DELETE FROM `Queryable` WHERE idMock='5'", adapter.objectForQuery(query));
	}
	
	public void testSimpleInsertQuery() {
		DataQuery query = DataQueryFactory.insert(new MockModelForInsert()).createQuery();
		
		assertEquals("INSERT INTO `Queryable`(name,surname,age) VALUES('Alberto','Chamorro','32')", adapter.objectForQuery(query));
	}
	
	public void testUpdateSimpleQuery() {
		DataQuery query = DataQueryFactory.update(new MockModelForInsert()).createQuery();
		
		assertEquals("UPDATE `Queryable` SET name='Alberto',surname='Chamorro',age='32' WHERE idMock='5'", adapter.objectForQuery(query));
	}
}

@ModelName("Queryable")
class MockModelForInsert extends ModelImpl {
	@ModelField
	private String name;
	
	@ModelField("surname")
	private String _surname;
	
	@ModelField("age")
	private long _age;
	
	@ModelId("idMock")
	private Long _id;

	public MockModelForInsert() {
		name = "Alberto";
		_surname = "Chamorro";
		_age = 32;
		_id = Long.valueOf(5);
	}
	
	public Long getModelIdValue() {
		return _id;
	}
}
