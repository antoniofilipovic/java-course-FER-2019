package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	ArrayIndexedCollection generateArrayCollection1() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		return coll;
	}

	ArrayIndexedCollection generateArrayCollection2() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection(3);
		coll.add(Integer.valueOf(20));
		coll.add(Integer.valueOf(25));
		coll.add("Zagreb");
		coll.add("Zadar");
		return coll;
	}

	// testovi za size
	@Test
	void testSizeBeforeAdding() {
		ArrayIndexedCollection coll = generateArrayCollection1();
		assertEquals(coll.size(), 0);
	}

	@Test
	void testSizeAfterAdding() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(coll.size(), 4);
	}

	// sljedeca tri testa su testovi za add

	@Test
	void testAdd() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		Object[] container = coll.toArray();
		assertArrayEquals(new Object[] { Integer.valueOf(20), Integer.valueOf(25), "Zagreb", "Zadar" }, container);

	}

	/**
	 * Testing whether collection's size has doubled.
	 */
	@Test
	void testAddDoublingItsCapacity() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(6, coll.getCapacity());

	}

	@Test
	void testAddNull() {
		ArrayIndexedCollection coll = generateArrayCollection1();
		assertThrows(NullPointerException.class, () -> {
			coll.add(null);
		});
	}

	@Test
	void testAddSameElement() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		coll.add("Zagreb");
		Object[] container = coll.toArray();
		assertEquals("Zagreb", container[2]);
		assertEquals("Zagreb", container[4]);

	}

	// test za contains
	@Test
	void testContains() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(coll.contains("Zadar"), true);
		assertEquals(coll.contains("Zadar"), true);
		assertEquals(coll.contains("Beograd"), false);
		assertEquals(coll.contains(null), false);

	}
	// testovi za remove object

	@Test
	void testRemoveObject() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(coll.remove("Zagreb"), true);
		assertEquals(coll.indexOf("Zagreb"), -1);
		assertEquals(coll.indexOf("Zadar"), 2);
		assertEquals(coll.size(), 3);
	}

	@Test
	void testRemoveObjectNull() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(coll.remove(null), false);
		assertEquals(coll.remove("Beograd"), false);
	}

	// test za toArray
	@Test
	void testToArray() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		Object[] container = coll.toArray();
		assertArrayEquals(new Object[] { Integer.valueOf(20), Integer.valueOf(25), "Zagreb", "Zadar" }, container);
	}

	@Test
	void testForEach() {
		class MyCulProcesor extends Processor {
			int counter = 0;

			@Override
			public void process(Object value) {
				counter++;
			}

			public int getCounter() {
				return this.counter;
			}
		}
		MyCulProcesor processor = new MyCulProcesor();
		ArrayIndexedCollection coll = generateArrayCollection2();
		coll.forEach(processor);
		assertEquals(processor.getCounter(), 4);
	}

	// testovi za clear
	@Test
	void testClear() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		coll.clear();
		assertEquals(0, coll.size());
		assertEquals(6, coll.getCapacity());
	}

	@Test
	void testClear2() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		coll.clear();
		coll.add("Zagreb");
		assertEquals(coll.size(), 1);
		assertEquals(coll.getCapacity(), 6);
	}

	// testovi za inicijalizaciju
	@Test
	void testArrayIndexedCollection() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertEquals(coll.getCapacity(), 16);
		assertEquals(coll.size(), 0);
	}

	@Test
	void testArrayIndexedCollectionInt() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection(3);
		assertEquals(coll.getCapacity(), 3);
		assertEquals(coll.size(), 0);
		coll.add("Zagreb");
		coll.add("Zadar");
		coll.add("Rijeka");
		coll.add("Osijek");
		assertEquals(coll.getCapacity(), 6);
		assertEquals(coll.size(), 4);

	}

	@Test
	void testArrayIndexedCollectionInt1() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayIndexedCollection coll = new ArrayIndexedCollection(-1);
		});

	}

	@Test
	void testArrayIndexedCollectionCollection() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		ArrayIndexedCollection coll1 = new ArrayIndexedCollection(coll);
		assertEquals(coll1.getCapacity(), 4);
		assertEquals(coll1.size(), 4);

	}

	@Test
	void testArrayIndexedCollectionCollectionNull() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection coll = new ArrayIndexedCollection(null);
		});

	}

	@Test
	void testArrayIndexedCollectionCollectionIntNull() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection coll = new ArrayIndexedCollection(null, -1);
		});
	}

	@Test
	void testArrayIndexedCollectionCollectionIntNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayIndexedCollection coll = generateArrayCollection2();
			ArrayIndexedCollection coll1 = new ArrayIndexedCollection(coll, -1);
		});
	}

	@Test
	void testArrayIndexedCollectionCollectionInt() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		ArrayIndexedCollection coll1 = new ArrayIndexedCollection(coll, 1);
		assertEquals(coll1.getCapacity(), 4);
		assertEquals(coll1.size(), 4);

	}

	// testovi za get
	@Test
	void testGetInRange() {// indekis
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(coll.get(2), "Zagreb");
		assertEquals(coll.get(1), Integer.valueOf(25));
	}

	@Test
	void testGetOutOfRange() {// indekis
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.get(-5);
		});
	}

	@Test
	void testGetOutOfRange1() {// indekis
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.get(8);
		});
	}

	// testovi za insert
	@Test
	void testInsertOutOfRangeSmaller() {// -1
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.insert("bla", -1);
		});
	}

	@Test
	void testInsertInRange() {// 5
		ArrayIndexedCollection coll = generateArrayCollection2();
		coll.insert("bla", 0);
		coll.insert("njam", coll.size());
		Object[] container = coll.toArray();
		assertArrayEquals(new Object[] { "bla", Integer.valueOf(20), Integer.valueOf(25), "Zagreb", "Zadar", "njam" },
				container);
	}

	@Test
	void testInsertOutOfRangeBigger() {// size+1
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.insert("bla", coll.size() + 1);
		});
	}

	@Test
	void testInsertNull() {// null
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(NullPointerException.class, () -> {
			coll.insert(null, 1);
		});
	}

	// testovi za indexOf
	@Test
	void testIndexOfNull() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		try {
			coll.indexOf(null);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testIndexOf() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertEquals(coll.indexOf("Zagreb"), 2);
		assertEquals(coll.indexOf("Beograd"), -1);

	}

	// remove index out of bounds sljedeca tri testa
	@Test
	void testRemoveIntOutOfBounds1() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.remove(-5);
		});
	}

	@Test
	void testRemoveIntOutOfBounds2() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.remove(7);
		});
	}

	void testRemoveInt() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		coll.remove(2);
		assertEquals(coll.indexOf("Zagreb"), -1);
		assertEquals(coll.indexOf("Zadar"), 2);
		assertEquals(coll.size(), 3);
	}

	// testovi za isEmpty
	@Test
	void testIsEmpty() {
		ArrayIndexedCollection coll = generateArrayCollection1();
		assertTrue(coll.isEmpty());
		coll.add("Zagreb");
		assertEquals(false, coll.isEmpty());

	}

	@Test
	void testAddAll() {
		ArrayIndexedCollection coll = generateArrayCollection2();
		ArrayIndexedCollection coll1 = new ArrayIndexedCollection();
		coll1.addAll(coll);
		assertEquals(coll1.size(), 4);
		Object[] container1 = coll1.toArray();
		Object[] container = coll.toArray();
		assertArrayEquals(container1, container);

	}

}
