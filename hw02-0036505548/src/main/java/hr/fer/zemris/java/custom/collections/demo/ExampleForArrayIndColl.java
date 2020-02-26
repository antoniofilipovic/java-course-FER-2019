package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

/**
 * This class represents test class for remove and add because one collegue said that
 * it doesn't work
 * @author antonio
 *
 */

public class ExampleForArrayIndColl {
	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add("a");
		col.add("b");
		col.add("c");
		col.add("d");
		class P extends Processor {
			 public void process(Object o) {
			 System.out.printf("%s ",o);
			 }
			};
		System.out.println("prije micanja:");
		col.forEach(new P());
		col.remove(1);
		System.out.println();
		System.out.println("nakon micanja:");
		col.forEach(new P());
		
		
		
	}

}
