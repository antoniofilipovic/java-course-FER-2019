package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents token. It holds tokens value and type.
 * 
 * @author Antonio Filipović
 *
 */

public class SmartScriptToken {
	/**
	 * this variable represents token type
	 */
	private SmartScriptTokenType type = null;
	/**
	 * represents value of token
	 */
	private Object value = null;
	/**
	 * This is public constructor
	 * @param type from tokentype
	 * @param value can be null
	 */

	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * Returns value of token
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}
	/**
	 * Returns token type
	 * @return token type
	 */
	public SmartScriptTokenType getType() {
		return this.type;
	}

}
