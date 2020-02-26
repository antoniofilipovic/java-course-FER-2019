package hr.fer.zemris.java.hw05.db;

public enum QueryTokenType {
	/**
	 * end of file
	 */
	EOF,
	/**
	 * operator
	 */
	OPERATOR,

	/**
	 * String
	 */
	STRING,
	/**
	 * field name is name before operator
	 */
	FIELDNAME,
	/**
	 * Connects two commands
	 */
	LOGICAL_OPERATOR

}
