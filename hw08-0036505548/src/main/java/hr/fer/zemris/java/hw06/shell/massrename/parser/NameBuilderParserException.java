package hr.fer.zemris.java.hw06.shell.massrename.parser;

/**
 * This class represents exception that occurs in parser
 * 
 * @author af
 *
 */
public class NameBuilderParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */

	public NameBuilderParserException() {
		super();
	}

	/**
	 * Constructor that receives message and prints it.
	 * 
	 * @param s message received
	 */
	public NameBuilderParserException(String s) {
		super(s);
	}

}
