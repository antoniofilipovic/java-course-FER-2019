package hr.fer.zemris.java.hw06.shell.massrename.lexer;

/**
 * This class represents exception that occurs in lexer
 * 
 * @author af
 *
 */
public class NameBuilderLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public NameBuilderLexerException() {
		super();
	}

	/**
	 * Constructor that receives message and prints it.
	 * 
	 * @param s message received
	 */
	public NameBuilderLexerException(String s) {
		super(s);
	}

}