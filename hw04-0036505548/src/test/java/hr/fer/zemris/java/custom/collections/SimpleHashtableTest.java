package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class SimpleHashtableTest {
	
	private SimpleHashtable<String, Integer> getTable(){
		return new SimpleHashtable<String,Integer>();
	}
	
	private SimpleHashtable<String, Integer> getTable1(){
		return new SimpleHashtable<String,Integer>(2);
	}
	private static SimpleHashtable<String, Integer> fillTable1(SimpleHashtable<String, Integer> table){
		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);
		table.put("Ivana", 5); // overwrites old grade for Ivana
		return table;
	}
	
	
	@Test
	void testPutElements() {
		SimpleHashtable<String, Integer> examMarks =getTable();
		examMarks=fillTable1(examMarks);
		assertEquals(4,examMarks.size());
		assertEquals(5,examMarks.get("Kristina"));
		assertEquals(5,examMarks.get("Ivana"));
		assertEquals(16,examMarks.getCapacity());
	}
	
	@Test
	void testPutElementsNullValue() {
		SimpleHashtable<String, Integer> examMarks =getTable();
		examMarks=fillTable1(examMarks);
		try {
			examMarks.put("Jagor", null);
		}catch(Exception e) {
			fail("Test");
		}
		
		assertTrue(examMarks.containsValue(null));

	}
	
	@Test
	void testPutElementsNull() {
		SimpleHashtable<String, Integer> examMarks =getTable();
		assertThrows(NullPointerException.class, () ->examMarks.put(null, 1));
	}
//  treba metodu stavit u public
//	@Test
//	void testCheckCapacity() {
//		SimpleHashtable<String, Integer> examMarks =getTable();
//		assertEquals(8,examMarks.checkCapacity(5));
//		assertEquals(16,examMarks.checkCapacity(16));
//		
//	}
	
	//i ovu metodu u public
//	@Test
//	void testCalculateIndexOfSlot() {
//		SimpleHashtable<String, Integer> examMarks =getTable1();
//		assertEquals(1,examMarks.calculateIndexOfSlot("Ivana"));
//		assertEquals(0,examMarks.calculateIndexOfSlot("Ante"));
//		assertEquals(1,examMarks.calculateIndexOfSlot("Jasna"));
//		assertEquals(1,examMarks.calculateIndexOfSlot("Kristina"));
//	}
	
	@Test
	void testContainsKeyValue() {
		SimpleHashtable<String, Integer> examMarks =getTable();
		examMarks=fillTable1(examMarks);
		assertTrue(examMarks.containsKey("Ivana"));
		assertEquals(false,examMarks.containsKey("Petar"));
		assertEquals(false,examMarks.containsKey(1));
		
		assertEquals(true,examMarks.containsValue(5));
		assertEquals(false,examMarks.containsValue("Ivana"));
		
		examMarks.put("Jagor", null);
		try {
			boolean b=examMarks.containsKey(null);
			assertEquals(false,b);
		}catch(Exception e) {
			fail("Test");
		}
		
		try {
			boolean b=examMarks.containsValue(null);
			assertEquals(true,b);
		}catch(Exception e) {
			fail("Test");
		}
		
	}
	


	
	@Test
	void testGet() {
		SimpleHashtable<String, Integer> examMarks =getTable();
		examMarks=fillTable1(examMarks);
		try {
			Object o=examMarks.get(null);
			assertNull(o);
		}catch(Exception e) {
			fail("Test");
		}
		assertEquals(5,examMarks.get("Ivana"));
		
		
	}
	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> examMarks =getTable1();
		examMarks=fillTable1(examMarks);
		examMarks.remove("Ivana");
		assertEquals(3,examMarks.size());
		assertEquals(false,examMarks.containsKey("Ivana"));
		examMarks.remove("Kristina");
		assertEquals(2,examMarks.size());
		examMarks.remove("Ivana");
		assertEquals(4,examMarks.getCapacity());
		
	}
	
	
}
