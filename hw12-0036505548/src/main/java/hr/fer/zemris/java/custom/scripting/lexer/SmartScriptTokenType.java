package hr.fer.zemris.java.custom.scripting.lexer;

public enum SmartScriptTokenType {
	/**
	 * end of file
	 */
	EOF,
	/**
	 * text in text mode
	 */
	TEXT,
	/**
	 * begin or end of tag
	 */
	TAG,
	/**
	 * Double number
	 */
	DOUBLE,
	/**
	 * integer
	 */
	INTEGER,
	/**
	 * Function
	 */
	FUNCTION,
	/**
	 * operator
	 */
	OPERATOR,
	/**
	 * String in tag
	 */
	STRING,
	/**
	 * tag name is name after tag {$
	 */
	TAGNAME,
	/**
	 * variable name
	 */
	VARIABLE;
}
