package hr.fer.zemris.java.custom.collections;

/**
 * This class represents Exception for empty stack.
 * 
 * @author Antonio Filipovic
 *
 */

public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is constructor that receives message and sends it furhter.
	 * 
	 * @param message that is received
	 */

	public EmptyStackException(String message) {
		super(message);
	}
}
