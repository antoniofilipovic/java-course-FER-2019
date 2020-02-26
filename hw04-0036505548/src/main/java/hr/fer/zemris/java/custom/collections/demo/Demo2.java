package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Test class for reallocating table
 * @author af
 *
 */

public class Demo2 {

	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);// testiramo realociranje, 
		//zakomentiraj toString koji imamo i otkomentiraj ovaj moj
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		System.out.println(examMarks);
		System.out.println("capacity:" + examMarks.getCapacity());
		System.out.println("hashkodovi,zaključujemo da ima dovoljno mjesta da ne treba realocirat");
		System.out.println(Math.abs("Ivana".hashCode()) % 4);
		System.out.println(Math.abs("Kristina".hashCode()) % 4);
		System.out.println(Math.abs("Jasna".hashCode()) % 4);
		System.out.println(Math.abs("Ante".hashCode()) % 4);
		

	}

}
