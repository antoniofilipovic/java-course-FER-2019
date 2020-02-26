package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class is used for representing integer value
 * 
 * @author af
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Private property of variable
	 */
	private int value;

	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	/**
	 * Getter for private property
	 * 
	 * @return value
	 */

	public int getValue() {
		return value;
	}

}
