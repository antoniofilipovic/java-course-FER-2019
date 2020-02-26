package hr.fer.zemris.java.hw07.demo2;

/**
 * This is test class for primes collection
 * 
 * @author Antonio Filipović
 *
 */
public class PrimesDemo1 {
	/**
	 * This method starts when main program starts
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
