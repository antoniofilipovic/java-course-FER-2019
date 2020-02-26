package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents exception thrown;
 * 
 * @author Antonio Filipovic
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public LexerException() {
		super();
	}

	/**
	 * Constructor that receives message and prints it.
	 * 
	 * @param s message received
	 */
	public LexerException(String s) {
		super(s);
	}

}
