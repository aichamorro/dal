package com.aichamorro.dal.dataquery;

import com.aichamorro.dal.dataquery.adapters.SqlDataQueryAdapter;

import static com.aichamorro.dal.dataquery.DataQueryStatementFactory.*;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

public class SqlDataQueryAdapterTest extends TestCase{
	public SqlDataQueryAdapterTest( String suiteName ) {
		super( suiteName );
	}

	public Test suite() {
		return new TestSuite(SqlDataQueryAdapterTest.class);
	}
	
	SqlDataQueryAdapter adapter;
	Queryable object;
	String objectClass;
	
	public void setUp() {
		adapter = new SqlDataQueryAdapter();
		object = mock(Queryable.class);
			when(object.getId()).thenReturn(5L);
		objectClass = object.getClass().getSimpleName();
	}
	
	public void tearDown() {
		adapter = null;
		object = null;
		objectClass = null;
	}

	public void testSelectQueryWithNoFilterReturnsSelectAllStatement() {
		DataQueryFactory query = DataQueryFactory.select(object.getClass());
		
		assertEquals("SELECT * FROM " + objectClass, adapter.objectForQuery(query.createQuery())); 
	}
	
	public void testSelectQueryWithFilterReturnsSelectWithWhereStatement() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where("id='5'").createQuery();
		
		assertEquals("SELECT * FROM " + objectClass + " WHERE id='5'", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithTwoFiltersUsingAndConcatenation() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(and("id='5'", "name='Alberto'")).createQuery();	
		
		assertEquals("SELECT * FROM " + objectClass + " WHERE (id='5' AND name='Alberto')", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithTwoFiltersUsingOrConcatenation() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(or("id='5'", "name='Alberto'")).createQuery();
		
		assertEquals("SELECT * FROM " + objectClass + " WHERE (id='5' OR name='Alberto')", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithMoreThanTwoFiltersUsingSeveralConcatenators() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(or(and("id='5'", "name='Alberto'"),statement("surname='Chamorro'"))).createQuery();
		
		assertEquals("SELECT * FROM " + objectClass + " WHERE ((id='5' AND name='Alberto') OR surname='Chamorro')", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithFilterGroupsNotInvolvingWhere() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(and(statement("id='5'"), or("name='Alberto'", "surname='Chamorro'"))).createQuery();
		
		assertEquals("SELECT * FROM " + objectClass + " WHERE (id='5' AND (name='Alberto' OR surname='Chamorro'))", adapter.objectForQuery(query));
	}
	
	public void testSelectQueryWithFilterGroups() {
		DataQuery query = DataQueryFactory.select(object.getClass()).where(or(and("id='5'", "name='Alberto'"), statement("surname='Chamorro'"))).createQuery();

		assertEquals("SELECT * FROM " + objectClass + " WHERE ((id='5' AND name='Alberto') OR surname='Chamorro')", adapter.objectForQuery(query));
	}
	
	public void testSimpleDeleteQuery() {
		DataQuery query = DataQueryFactory.delete(new MockModelForInsert()).createQuery();
		
		assertEquals("DELETE FROM MockModelForInsert WHERE id='5'", adapter.objectForQuery(query));
	}
	
	public void testSimpleInsertQuery() {
		DataQuery query = DataQueryFactory.insert(new MockModelForInsert()).createQuery();
		
		assertEquals("INSERT INTO MockModelForInsert(name,surname,age) VALUES(Alberto,Chamorro,32)", adapter.objectForQuery(query));
	}
}

class MockModelForInsert implements Queryable {
	@DataField("name")
	private String _name;
	
	@DataField("surname")
	private String _surname;
	
	@DataField("age")
	private String _age;
	
	private long _id;

	public MockModelForInsert() {
		_name = "Alberto";
		_surname = "Chamorro";
		_age = "32";
		_id = 5;
	}
	
	public long getId() {
		return _id;
	}
}
