package hr.fer.zemris.java.hw06.shell.massrename.lexer;

public enum NameBuilderTokenType {
	/**
	 * tag
	 */
	TAG, 
	/**
	 * end of file
	 */
	EOF,
	/**
	 * text
	 */
	TEXT,
	/**
	 * Number that contains zero before
	 */
	ZERO_NUMBER,
	/**
	 * number regular
	 */
	NUMBER,
	/**
	 * Comma
	 */
	COMMA

}
