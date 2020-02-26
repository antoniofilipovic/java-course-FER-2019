package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents token
 * 
 * @author Antonio Filipovic
 *
 */
public class Token {
	/**
	 * This is tokentype
	 */
	private TokenType type = null;
	/**
	 * This is value that is stored
	 */
	private Object value = null;

	/**
	 * This is public constructor
	 * 
	 * @param type  of that token
	 * @param value that will be stored
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	/**
	 * This method returns value
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}
	/**
	 * This method returns current type
	 * @return type
	 */
	public TokenType getType() {
		return this.type;
	}
}
