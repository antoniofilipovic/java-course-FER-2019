package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	LinkedListIndexedCollection generateLinkedListCollection1() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		return coll;
	}

	LinkedListIndexedCollection generateLinkedListCollection2() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		coll.add(Integer.valueOf(20));
		coll.add(Integer.valueOf(25));
		coll.add("Zagreb");
		coll.add("Zadar");
		return coll;
	}

	// testovi za size
	@Test
	void testSizeBeforeAdding() {
		LinkedListIndexedCollection coll = generateLinkedListCollection1();
		assertEquals(coll.size(), 0);
	}

	@Test
	void testSizeAfterAdding() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertEquals(coll.size(), 4);
	}

	// sljedeca tri testa su testovi za add

	@Test
	void testAdd() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		Object[] container = coll.toArray();
		assertArrayEquals(new Object[] { Integer.valueOf(20), Integer.valueOf(25),
				"Zagreb", "Zadar" }, container);

	}


	@Test
	void testAddNull() {
		LinkedListIndexedCollection coll = generateLinkedListCollection1();
		assertThrows(NullPointerException.class, () -> {
			coll.add(null);
		});
	}

	@Test
	void testAddSameElement() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		coll.add("Zagreb");
		Object[] container = coll.toArray();
		assertEquals("Zagreb", container[2]);
		assertEquals("Zagreb", container[4]);

	}

	// test za contains
	@Test
	void testContains() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertEquals(coll.contains("Zadar"), true);
		assertEquals(coll.contains("Zadar"), true);
		assertEquals(coll.contains("Beograd"), false);
		assertEquals(coll.contains(null), false);

	}
	// testovi za remove object

	@Test
	void testRemoveObject() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertEquals(coll.remove("Zagreb"), true);
		assertEquals(coll.indexOf("Zagreb"), -1);
		assertEquals(coll.indexOf("Zadar"), 2);
		assertEquals(coll.size(), 3);
	}

	@Test
	void testRemoveObjectNull() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertEquals(coll.remove(null), false);
		assertEquals(coll.remove("Beograd"), false);
	}

	// test za toArray
	@Test
	void testToArray() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
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
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		coll.forEach(processor);
		assertEquals(processor.getCounter(), 4);
	}

	// testovi za clear
	@Test
	void testClear() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		coll.clear();
		assertEquals(0, coll.size());
		
	}

	@Test
	void testClear2() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		coll.clear();
		coll.add("Zagreb");
		assertEquals(coll.size(), 1);
	}

	// testovi za inicijalizaciju
	@Test
	void testLinkedListIndexedCollection() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		assertEquals(coll.size(), 0);
	}

	@Test
	void testLinkedListIndexedCollectionAfterAdd() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		assertEquals(coll.size(), 0);
		coll.add("Zagreb");
		coll.add("Zadar");
		coll.add("Rijeka");
		coll.add("Osijek");
		assertEquals(coll.size(), 4);

	}
	@Test
	void testLinkedListIndexedCollectionCollection() {
		LinkedListIndexedCollection coll1 = generateLinkedListCollection2();
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection(coll1);
		assertEquals(coll.size(), 4);
		Object[] container1 = coll1.toArray();
		Object[] container = coll.toArray();
		assertArrayEquals(container1, container);

	}



	@Test
	void testArrayIndexedCollectionCollectionNull() {
		assertThrows(NullPointerException.class, () -> {
			LinkedListIndexedCollection coll = new LinkedListIndexedCollection(null);
		});

	}

	// testovi za get
	@Test
	void testGetInRange() {// indekis
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertEquals(coll.get(2), "Zagreb");
		assertEquals(coll.get(1), Integer.valueOf(25));
	}

	@Test
	void testGetOutOfRange() {// indekis
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.get(-5);
		});
	}

	@Test
	void testGetOutOfRange1() {// indekis
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.get(8);
		});
	}

	// testovi za insert
	@Test
	void testInsertOutOfRangeSmaller() {// -1
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.insert("bla", -1);
		});
	}

	@Test
	void testInsertInRange() {// 5
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		coll.insert("bla", 0);
		coll.insert("njam", coll.size());
		coll.insert("vanDam", coll.size()-1);
		Object[] container = coll.toArray();
		assertArrayEquals(new Object[] { "bla", Integer.valueOf(20), Integer.valueOf(25),
				"Zagreb", "Zadar","vanDam", "njam" },
				container);
	}

	@Test
	void testInsertOutOfRangeBigger() {// size+1
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.insert("bla", coll.size() + 1);
		});
	}

	@Test
	void testInsertNull() {// null
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(NullPointerException.class, () -> {
			coll.insert(null, 1);
		});
	}

	// testovi za indexOf
	@Test
	void testIndexOfNull() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		try {
			coll.indexOf(null);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void testIndexOf() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertEquals(coll.indexOf("Zagreb"), 2);
		assertEquals(coll.indexOf("Beograd"), -1);

	}

	// remove index out of bounds sljedeca tri testa
	@Test
	void testRemoveIntOutOfBounds1() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.remove(-5);
		});
	}

	@Test
	void testRemoveIntOutOfBounds2() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			coll.remove(7);
		});
	}

	void testRemoveInt() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		coll.remove(2);
		assertEquals(coll.indexOf("Zagreb"), -1);
		assertEquals(coll.indexOf("Zadar"), 2);
		assertEquals(coll.size(), 3);
	}

	// testovi za isEmpty
	@Test
	void testIsEmpty() {
		LinkedListIndexedCollection coll = generateLinkedListCollection1();
		assertTrue(coll.isEmpty());
		coll.add("Zagreb");
		assertEquals(false, coll.isEmpty());

	}

	@Test
	void testAddAll() {
		LinkedListIndexedCollection coll = generateLinkedListCollection2();
		LinkedListIndexedCollection coll1 = new LinkedListIndexedCollection();
		coll1.addAll(coll);
		assertEquals(coll1.size(), 4);
		Object[] container1 = coll1.toArray();
		Object[] container = coll.toArray();
		assertArrayEquals(container1, container);

	}

}

