package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	  @Test
	    public void directQuery() {
	        QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
	        assertTrue(qp1.isDirectQuery());
	        assertEquals("0123456789", qp1.getQueriedJMBAG());
	        assertEquals(1, qp1.getQuery().size());
	    }

	    @Test
	    public void complexQuery() {
	        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
	        assertFalse(qp2.isDirectQuery());
	        assertEquals(2, qp2.getQuery().size());
	        assertThrows(IllegalStateException.class,() ->qp2.getQueriedJMBAG());
	    }
	    
	    @Test
	    public void parserException() {
	    	assertThrows(QueryParserException.class,()-> new QueryParser("jmbag=lastName" ));
	    	assertThrows(QueryParserException.class,()-> new QueryParser("jmbag=\"01*23*\" and lastName>\"J\""));
	    	assertThrows(QueryParserException.class,()-> new QueryParser("Jmbag=\"pero\" "));
	    }

}
