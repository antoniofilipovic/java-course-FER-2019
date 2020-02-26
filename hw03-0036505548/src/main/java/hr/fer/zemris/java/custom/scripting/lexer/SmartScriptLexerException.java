package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents exception that occurs in lexer
 * 
 * @author af
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Constructor that receives message and prints it.
	 * 
	 * @param s message received
	 */
	public SmartScriptLexerException(String s) {
		super(s);
	}

}
