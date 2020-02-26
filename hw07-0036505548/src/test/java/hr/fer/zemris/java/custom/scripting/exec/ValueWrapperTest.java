package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	public void addNullTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		v1.add(null);
		assertEquals(0, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
	}

	@Test
	public void addNullTest2() {
		ValueWrapper v1 = new ValueWrapper(3.51);
		v1.add(null);
		assertEquals(3.51, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	public void addMixedTest() {
		ValueWrapper v1 = new ValueWrapper("1");
		ValueWrapper v2=new ValueWrapper(1);
		v1.add(v2.getValue());
		assertEquals(2, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
	}

	@Test
	public void addMixedTest2() {
		ValueWrapper v1 = new ValueWrapper(1);
		v1.add("3.4E1");
		assertEquals(35.0, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	public void addBothIntTest() {
		ValueWrapper v1 = new ValueWrapper(5);
		v1.add(6);
		assertEquals(11, v1.getValue());
	}

	public void exceptionTest() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		assertThrows(RuntimeException.class,()-> v1.add(1));
	}

	@Test
	public void subTest() {
		ValueWrapper v1 = new ValueWrapper("17.0");
		v1.subtract(2);
		assertEquals(15.0, v1.getValue());
		assertTrue(v1.getValue() instanceof Double);
	}

	@Test
	public void multiplyTest() {
		ValueWrapper v1 = new ValueWrapper("5");
		v1.multiply("3");
		assertEquals(15, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
	}

	@Test
	public void divideTest() {
		ValueWrapper v1 = new ValueWrapper(12);
		v1.divide(4);
		assertEquals(3, v1.getValue());
		assertTrue(v1.getValue() instanceof Integer);
	}

	@Test
	public void numCompareBigger() {
		ValueWrapper v1 = new ValueWrapper("6.1");
		ValueWrapper v2 = new ValueWrapper(3.4);
		assertTrue(v1.numCompare(v2.getValue()) > 0);
	}

	@Test
	public void numCompareSmaller() {
		ValueWrapper v1 = new ValueWrapper("6.1");
		ValueWrapper v2 = new ValueWrapper(150);
		assertTrue(v1.numCompare(v2.getValue()) < 0);
	}

	@Test
	public void numCompareEquals() {
		ValueWrapper v1 = new ValueWrapper(12);
		ValueWrapper v2 = new ValueWrapper(12);
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}

	@Test
	public void getValueTest() {
		ValueWrapper wrapper = new ValueWrapper(5);
		assertEquals(5, wrapper.getValue());
	}

	@Test
	public void setValueTest() {
		ValueWrapper wrapper = new ValueWrapper(5);
		wrapper.setValue("Mljac");
		assertEquals("Mljac", wrapper.getValue());
	}

}
