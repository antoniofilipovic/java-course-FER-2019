package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	public void testSatisified() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME,
				"Glav*",
				ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("0036491234", "Glavica", "Glava", 2);
		assertTrue(expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),
				expr.getStringLiteral()));
	}
}
