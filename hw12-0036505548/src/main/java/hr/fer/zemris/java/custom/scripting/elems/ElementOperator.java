package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class is used for representing operator
 * @author af
 *
 */

public class ElementOperator extends Element{

	private String symbol;
	/**
	 * This constructor receives symbol that will be stored
	 * @param symbol value that is stored
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override 
	public String asText() {
		return symbol;
	}
	/**
	 * Getter for symbol
	 * @return string 
	 */
	public String getSymbol() {
		return symbol;
	}
}
