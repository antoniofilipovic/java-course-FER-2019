package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents exception in parser
 * 
 * @author af
 *
 */

public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * public constructor without message
	 */
	public SmartScriptParserException() {
		super();

	}

	/**
	 * constructor with message
	 * 
	 * @param s message
	 */
	public SmartScriptParserException(String s) {
		super(s);
	}

}
