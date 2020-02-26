package hr.fer.zemris.java.hw05.db;

/**
 * Represents parser exception
 * 
 * @author Antonio Filipović
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public QueryParserException() {
		super();
	}

	/**
	 * Constructor that receives message and prints it.
	 * 
	 * @param s message received
	 */
	public QueryParserException(String s) {
		super(s);
	}

}
