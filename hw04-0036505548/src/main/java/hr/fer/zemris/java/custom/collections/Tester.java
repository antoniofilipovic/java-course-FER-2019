package hr.fer.zemris.java.custom.collections;



/**
 * This interface tests whether object is good
 * 
 * @author Antonio Filipovic
 *
 */
public interface Tester<T> {
	/**
	 * Tests whether object is good
	 * 
	 * @param value object that is tested
	 * @return true if it is good, false otherwise
	 */
	boolean test(T value);

}
