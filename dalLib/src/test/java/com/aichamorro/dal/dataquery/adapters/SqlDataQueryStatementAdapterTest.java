package com.aichamorro.dal.dataquery.adapters;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.aichamorro.dal.dataquery.DataQueryFilter;
import static com.aichamorro.dal.dataquery.DataQueryFilterFactory.*;

public class SqlDataQueryStatementAdapterTest extends TestCase {
	public SqlDataQueryStatementAdapterTest(String testName ) {
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(SqlDataQueryStatementAdapterTest.class);
	}
	
	private SqlDataQueryFilterAdapter adapter;

	protected void setUp() throws Exception {
		super.setUp();
		
		adapter = new SqlDataQueryFilterAdapter();
	};
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		adapter = null;
	}
	
	public void testSimpleAnd() {
		DataQueryFilter stmnt = and("name=alberto", "id=5");
		
		assertEquals("(name=alberto AND id=5)", adapter.statementAdapter(stmnt));
	}
	
	public void testBigSimpleAnd() {
		DataQueryFilter stmnt = and("name=alberto", "age=15", "surname=chamorro", "id=5", "payed=true");
	
		assertEquals("(name=alberto AND age=15 AND surname=chamorro AND id=5 AND payed=true)", adapter.statementAdapter(stmnt));
	}
	
	public void testComposedAnd() {
		DataQueryFilter stmnt = and(statement("name=alberto"), statement("age=15"), and("id=6", "payed=true"));
	
		assertEquals("(name=alberto AND age=15 AND (id=6 AND payed=true))", adapter.statementAdapter(stmnt));
	}
	
	public void testSimpleOr() {
		DataQueryFilter stmnt = or("name=alberto", "id=5");
		
		assertEquals("(name=alberto OR id=5)", adapter.statementAdapter(stmnt));
	}
	
	public void testBigSimpleOr() {
		DataQueryFilter stmnt = or("name=alberto", "age=15", "surname=chamorro", "id=5", "payed=true");
	
		assertEquals("(name=alberto OR age=15 OR surname=chamorro OR id=5 OR payed=true)", adapter.statementAdapter(stmnt));
	}
	
	public void testComposedOr() {
		DataQueryFilter stmnt = or(statement("name=alberto"), statement("age=15"), or("id=6", "payed=true"));
	
		assertEquals("(name=alberto OR age=15 OR (id=6 OR payed=true))", adapter.statementAdapter(stmnt));
	}
	
	public void testMixedAndOr() {
		DataQueryFilter stmnt = or(statement("name=alberto"), and("age=15", "payed=true"));
		
		assertEquals("(name=alberto OR (age=15 AND payed=true))", adapter.statementAdapter(stmnt));
	}
	
	public void testEmptyStatement() {
		assertEquals("", adapter.statementAdapter(statement("")));
	}
	
	public void testVeryDeepStatements() {
		DataQueryFilter stmnt = or(or(statement("a=1"), or(statement("b=2"), or(statement("c=3"), or(statement("d=4"), and("e=5", "f=6"))))), statement("g=7"));
		
		assertEquals("((a=1 OR (b=2 OR (c=3 OR (d=4 OR (e=5 AND f=6))))) OR g=7)", adapter.statementAdapter(stmnt));
	}
	
	public void testSimpleNotStatement() {
		assertEquals("(NOT a=1)", adapter.statementAdapter(not("a=1")));
	}
	
	public void testNotAnd() {
		DataQueryFilter stmnt = not(and("a=1", "b=2"));
		
		assertEquals("(NOT (a=1 AND b=2))", adapter.statementAdapter(stmnt));
	}
	
	public void testNotOr() {
		DataQueryFilter stmnt = not(or("a=1", "b=2"));
		
		assertEquals("(NOT (a=1 OR b=2))", adapter.statementAdapter(stmnt));
	}
	
	public void testNestedNot() {
		DataQueryFilter stmnt = not(not(not(not(not("b=1")))));
		
		assertEquals("(NOT (NOT (NOT (NOT (NOT b=1)))))", adapter.statementAdapter(stmnt));
	}
	
	public void testSomeComplexQuery() {
		DataQueryFilter stmnt = not(and(or("a=1", "b=2"), not("b=2"), statement("c=3")));
		
		assertEquals("(NOT ((a=1 OR b=2) AND (NOT b=2) AND c=3))", adapter.statementAdapter(stmnt));
	}
}
