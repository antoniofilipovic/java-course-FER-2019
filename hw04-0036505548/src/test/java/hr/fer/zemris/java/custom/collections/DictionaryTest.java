package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {
	
	private Dictionary<String,Integer> getDictionary(){
		return new  Dictionary<>();
	}
	
	private static Dictionary <String, Integer> fillDict(Dictionary<String, Integer> dict){
		dict.put("Ivana", 2);
		dict.put("Ante", 2);
		dict.put("Jasna", 2);
		dict.put("Kristina", 5);
		dict.put("Ivana", 5); // overwrites old grade for Ivana
		return dict;
	}
	@Test
	void testDictionary() {
		Dictionary<String,Integer> dict=getDictionary();
		assertEquals(16,dict.getCapacity());
		assertEquals(0,dict.size());
	}

	@Test
	void testIsEmpty() {
		Dictionary<String,Integer> dict=getDictionary();
		assertTrue(dict.isEmpty());
		dict.put("Ivana", 4);
		assertEquals(false,dict.isEmpty());
	}

	@Test
	void testSize() {
		Dictionary<String,Integer> dict=getDictionary();
		dict=fillDict(dict);
		assertEquals(4, dict.size());
	}

	@Test
	void testClear() {
		Dictionary<String,Integer> dict=getDictionary();
		dict=fillDict(dict);
		dict.clear();
		assertEquals(0,dict.size());
		assertEquals(16, dict.getCapacity());
	}

	@Test
	void testPut() {
		Dictionary<String,Integer> dict=getDictionary();
		dict=fillDict(dict);
		assertEquals(4,dict.size());
		assertEquals(16, dict.getCapacity());
		int value=dict.get("Ivana");
		Object value1=dict.get("Marija");
		assertEquals(value,5);
		assertNull(value1);
	}
	
	@Test
	void testPutNull() {
		Dictionary<String,Integer> dict=getDictionary();
		assertThrows(NullPointerException.class, () ->dict.put(null, 1));
	
	}

	@Test
	void testGet() {
		Dictionary<String,Integer> dict=getDictionary();
		dict=fillDict(dict);
		int value=dict.get("Kristina");
		Object value1=dict.get("Petar");
		assertEquals(value,5);
		assertNull(value1);
	}

}
