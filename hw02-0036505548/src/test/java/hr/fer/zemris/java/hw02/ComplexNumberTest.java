package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

class ComplexNumberTest {
	private static final ComplexNumber c1 = new ComplexNumber(1, 1);
	private static final ComplexNumber c2 = new ComplexNumber(-1, 1);
	private static final ComplexNumber c3 = new ComplexNumber(-1, -1);
	private static final ComplexNumber c4 = new ComplexNumber(1, -1);

	@Test
	void testComplexNumber() {
		ComplexNumber c = new ComplexNumber(2, 5);
		assertEquals(c.getReal(), 2);
		assertEquals(c.getImaginary(), 5);
	}

	@Test
	void testFromReal() {
		ComplexNumber c = new ComplexNumber(2, 5);
		ComplexNumber c1 = ComplexNumber.fromReal(c.getReal());
		assertEquals(new ComplexNumber(2.0, 0.0), c1);
	}

	@Test
	void testFromImaginary() {
		ComplexNumber c = new ComplexNumber(2, 5);
		ComplexNumber c1 = ComplexNumber.fromImaginary(c.getImaginary());
		assertEquals(new ComplexNumber(0.0, 5.0), c1);
	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI / 3);
		ComplexNumber c1 = new ComplexNumber(1, Math.sqrt(3));
		assertEquals(c, c1);
	}
	
	@Test
	void testFromMagnitudeAndAngleException() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(-2, Math.PI / 3);
		});

	}

	

	@Test
	void testParseValidStringCombination1() {
		ComplexNumber c = ComplexNumber.parse(" 2   +  5.3i");
		assertEquals(new ComplexNumber(2, 5.3), c);
	}

	@Test
	void testParseValidStringCombination2() {
		ComplexNumber c = ComplexNumber.parse(" 2 - 5.3i");
		assertEquals(new ComplexNumber(2, -5.3), c);
	}

	@Test
	void testParseValidStringCombination3() {
		ComplexNumber c = ComplexNumber.parse("+ 2  - 5.3i");
		assertEquals(new ComplexNumber(2, -5.3), c);
	}

	@Test
	void testParseValidStringCombination4() {
		ComplexNumber c = ComplexNumber.parse("- 2+5.3i");
		assertEquals(new ComplexNumber(-2, 5.3), c);
	}

	@Test
	void testParseValidStringCombination5() {
		ComplexNumber c = ComplexNumber.parse("-  2 -5.3i");
		assertEquals(new ComplexNumber(-2, -5.3), c);
	}

	@Test
	void testParseInValidStringCombination1() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("-+2-5.3i");
		});

	}

	@Test
	void testParseInValidStringCombination2() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("++2-5.3i");
		});
	}

	@Test
	void testParseInValidStringCombination3() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("2+-5.3i");
		});
	}

	@Test
	void testParseInValidStringCombination4() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("2--5.3i");
		});
	}

	@Test
	void testParseInValidStringCombination5() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("2i+2");
		});
	}

	@Test
	void testParseInValidStringCombination6() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("3+i2");
		});
	}

	@Test
	void testParseInValidStringCombination7() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("i2");
		});
	}

	@Test
	void testParseValidStringCombinationMarignalCase1() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("0+i2");
		});
	}

	@Test
	void testParseValidStringCombinationMarignalCase2() {
		ComplexNumber c = ComplexNumber.parse("0+i");
		assertEquals(new ComplexNumber(0, 1), c);
	}

	@Test
	void testParseValidStringCombinationMarignalCase3() {
		ComplexNumber c = ComplexNumber.parse("i");
		assertEquals(new ComplexNumber(0, 1), c);
	}

	@Test
	void testParseValidStringCombinationMarignalCase4() {
		ComplexNumber c = ComplexNumber.parse("-i");
		assertEquals(new ComplexNumber(0, -1), c);
	}

	@Test
	void testParseValidStringCombinationMarignalCase5() {
		ComplexNumber c = ComplexNumber.parse("-2i");
		assertEquals(new ComplexNumber(0, -2), c);
	}

	@Test
	void testParseValidStringCombinationMarignalCase6() {
		ComplexNumber c = ComplexNumber.parse("1+0i");
		assertEquals(new ComplexNumber(1, 0), c);
	}

	

	@Test
	void testParseInValidString() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = ComplexNumber.parse("agfg36572+52");
		});
	}

	@Test
	void testParseNull() {
		assertThrows(NullPointerException.class, () -> {
			ComplexNumber c = ComplexNumber.parse(null);
		});
	}

	@Test
	void testGetReal() {
		ComplexNumber c = new ComplexNumber(2, 5);
		assertEquals(c.getReal(), 2.0);
	}

	@Test
	void testGetImaginary() {
		ComplexNumber c = new ComplexNumber(2, 5);
		assertEquals(c.getImaginary(), 5.0);
	}

	@Test
	void testGetMagnitude() {
		ComplexNumber c = new ComplexNumber(6, 8);
		assertEquals(c.getMagnitude(), 10);
	}

	@Test
	void testGetAngle() {
		assertEquals(c1.getAngle(), Math.PI / 4);
		assertEquals(c2.getAngle(), 3 * Math.PI / 4);
		assertEquals(c3.getAngle(), 5 * Math.PI / 4);
		assertEquals(c4.getAngle(), 7 * Math.PI / 4);
	}

	@Test
	void testAdd() {
		assertEquals(c1.add(c2), new ComplexNumber(0, 2));
	}

	@Test
	void testAddNull() {
		assertThrows(NullPointerException.class, () -> {
			c1.add(null);
		});
	}

	@Test
	void testSub() {
		assertEquals(c1.sub(c2), new ComplexNumber(2, 0));
	}

	@Test
	void testSubNull() {
		assertThrows(NullPointerException.class, () -> {
			c1.sub(null);
		});
	}

	@Test
	void testMul() {
		assertEquals(c1.mul(c2), new ComplexNumber(-2, 0));
	}

	@Test
	void testMulNull() {
		assertThrows(NullPointerException.class, () -> {
			c1.mul(null);
		});
	}

	@Test
	void testDiv() {
		assertEquals(c1.div(c2), new ComplexNumber(0, -1));
	}

	@Test
	void testDivNull() {
		assertThrows(NullPointerException.class, () -> {
			c1.div(null);
		});
	}

	@Test
	void testDivZero() {
		ComplexNumber c = c1.div(new ComplexNumber(0, 0));
		assertTrue(Double.isNaN(c.getReal()));
		assertTrue(Double.isNaN(c.getImaginary()));
	}

	@Test
	void testPower() {
		assertEquals((new ComplexNumber(1.5, -2)).power(4), new ComplexNumber(-32.9375, 21));
	}

	@Test
	void testPowerWrongRange() {
		assertThrows(IllegalArgumentException.class, () -> {
			c1.power(-2);
		});
	}

	@Test
	void testPowerZero() {
		assertEquals(c1.power(0), new ComplexNumber(1, 0));
	}

	@Test
	void testRoot() {
		ComplexNumber c = new ComplexNumber(-1, 0);
		ComplexNumber[] numbers = c.root(2);
		assertArrayEquals(numbers, new ComplexNumber[] { new ComplexNumber(0, -1), new ComplexNumber(0, 1) });
	}

	@Test
	void testRootWrongRange() {
		assertThrows(IllegalArgumentException.class, () -> {
			c1.root(-2);
		});
	}

	@Test
	void testToString() {
		assertEquals(c1.toString(), "1.0+1.0i");
	}

	@Test
	void testEquals() {
		ComplexNumber c = new ComplexNumber(2, 3);
		ComplexNumber cd = new ComplexNumber(2.0000001, 3.0000000000000243);
		assertEquals(c, cd);
	}

}
