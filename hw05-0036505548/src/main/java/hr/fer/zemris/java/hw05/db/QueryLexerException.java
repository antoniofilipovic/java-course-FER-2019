package hr.fer.zemris.java.hw05.db;
/**
 * Represents exception in lexer
 * @author Antonio Filipović
 *
 */
public class QueryLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public QueryLexerException() {
		super();
	}

	/**
	 * Constructor that receives message and prints it.
	 * 
	 * @param s message received
	 */
	public QueryLexerException(String s) {
		super(s);
	}
}
