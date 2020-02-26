package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	public void queryFilter1Test() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		StudentRecord record = new StudentRecord("0123456789", "Javor", "Igor", 5);
		QueryFilter filter = new QueryFilter(qp2.getQuery());

		assertTrue(filter.accepts(record));

	}

	@Test
	public void queryFilter2Test() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		StudentRecord record = new StudentRecord("0123456789", "Bravo", "Claudio", 5);
		QueryFilter filter = new QueryFilter(qp2.getQuery());

		assertFalse(filter.accepts(record));

	}

}
