package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * This class represents collection of prime elements
 * 
 * @author Antonio Filipović
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	private int numberOfPrimeElements;

	/**
	 * This is public constructor for primes collection
	 * 
	 * @param numberOfPrimeElements number of prime elements that will be reuturned
	 */
	public PrimesCollection(int numberOfPrimeElements) {
		this.numberOfPrimeElements = numberOfPrimeElements;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}

	private class PrimesIterator implements Iterator<Integer> {
		/**
		 * Number of iterated elements
		 */
		private int iterated;
		/**
		 * current prime element;
		 */
		private int current = 1;

		@Override
		public boolean hasNext() {
			return PrimesCollection.this.numberOfPrimeElements > iterated;
		}

		@Override
		public Integer next() {
			iterated++;
			int j = current + 1;
			while (true) {
				boolean prime = true;
				for (int i = 2; i < (int)Math.sqrt(j) + 1; i++) {
					if (j % i == 0) {
						prime = false;
						break;
					}
				}
				if (prime) {
					current = j;
					break;
				}j++;
			}
			current = j;
			return current;
		}

	}
}
