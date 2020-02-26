package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents element with value of double
 * 
 * @author af
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Private proprety of this class. Stores value
	 */
	private double value;

	/**
	 * This is contructor for element constant double
	 * 
	 * @param value that will be stored
	 */

	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	/**
	 * Getter of private property
	 * 
	 * @return double value
	 */
	public double getValue() {
		return value;
	}

}
