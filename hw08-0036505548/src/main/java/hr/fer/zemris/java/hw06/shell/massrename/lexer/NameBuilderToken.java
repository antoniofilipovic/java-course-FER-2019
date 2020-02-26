package hr.fer.zemris.java.hw06.shell.massrename.lexer;



/**
 * This class represents token. It holds tokens value and type.
 * 
 * @author Antonio Filipović
 *
 */

public class NameBuilderToken {
	/**
	 * this variable represents token type
	 */
	private NameBuilderTokenType type = null;
	/**
	 * represents value of token
	 */
	private Object value = null;
	/**
	 * This is public constructor
	 * @param type from tokentype
	 * @param value can be null
	 */

	public NameBuilderToken(NameBuilderTokenType type, Object value) {
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
	public NameBuilderTokenType getType() {
		return this.type;
	}

}