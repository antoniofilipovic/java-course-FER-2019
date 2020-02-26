package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	public void lessTest() {
		assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jure"));
		assertFalse(ComparisonOperators.LESS.satisfied("Jure", "Ante"));
	}

	@Test
	public void lessOrEqualsTest() {
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Jure", "Ante"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Jure", "Jure"));
	}

	@Test
	public void greaterTest() {
		assertTrue(ComparisonOperators.GREATER.satisfied("Boban", "Ante"));
		assertFalse(ComparisonOperators.GREATER.satisfied("Ante", "Boban"));
	}

	@Test
	public void greaterEqualsTest() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Boban", "Ante"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Boban", "Boban"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Ante", "Boban"));
	}

	@Test
	public void equalsTest() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("Jure", "Jure"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("Jure", "aJure"));
	}

	@Test
	public void notEqualsTest() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Boban", "Jure"));
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Boban", "Boban"));
	}

	@Test
	public void likeTest() {
		assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Ante", "*te"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Ante", "An*"));
	}

}
