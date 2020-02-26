package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Basic test class plus my tests
 * @author af
 *
 */

public class Demo1 {
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
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
		System.out.println("Ivana".equals(2));
		System.out.println("Sadrzi vrijednost 5:"+examMarks.containsValue(5));
		System.out.println("Sadrzi vrijednsot \"Petar\":"+examMarks.containsValue("Petar"));
		
		
		System.out.println("------------------------------before remove----------------");
		System.out.println(examMarks.containsKey("Ivana"));
		System.out.println(examMarks.containsKey("Ante"));
		System.out.println(examMarks.containsKey("Jasna"));
		System.out.println(examMarks.containsKey("Kristina"));
		System.out.println("----------------------------------------- remove-------------");
		examMarks.remove("Ivana");
		System.out.println(examMarks.containsKey("Ivana"));
		System.out.println(examMarks.containsKey("Ante"));
		System.out.println(examMarks.containsKey("Jasna"));
		System.out.println(examMarks.containsKey("Kristina"));
		
		System.out.println("----------------------------------------- next remove-------------");
		examMarks.remove("Kristina");
		System.out.println(examMarks.containsKey("Ivana"));
		System.out.println(examMarks.containsKey("Ante"));
		System.out.println(examMarks.containsKey("Jasna"));
		System.out.println(examMarks.containsKey("Kristina"));
		
		System.out.println("----------------------------------------- next fake remove-------------");
		examMarks.remove("Petar");
		System.out.println(examMarks.containsKey("Ivana"));
		System.out.println(examMarks.containsKey("Ante"));
		System.out.println(examMarks.containsKey("Jasna"));
		System.out.println(examMarks.containsKey("Kristina"));

	}

}
