package hr.fer.zemris.java.hw05.db;

/**
 * This class represents token. It holds tokens value and type.
 * 
 * @author Antonio Filipović
 *
 */

public class QueryToken {
	/**
	 * this variable represents token type
	 */
	private QueryTokenType type = null;
	/**
	 * represents value of token
	 */
	private Object value = null;

	/**
	 * This is public constructor
	 * 
	 * @param type  from tokentype
	 * @param value can be null
	 */

	public QueryToken(QueryTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns value of token
	 * 
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Returns token type
	 * 
	 * @return token type
	 */
	public QueryTokenType getType() {
		return this.type;
	}

}