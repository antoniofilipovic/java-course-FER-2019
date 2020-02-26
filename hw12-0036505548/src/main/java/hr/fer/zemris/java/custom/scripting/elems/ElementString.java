package hr.fer.zemris.java.custom.scripting.elems;
/**
 * This class is used for representing string value
 * @author af
 *
 */
public class ElementString extends Element {
	/**
	 * This is private property that stores value
	 */
	private String value;
	/**
	 * This is public constructor for element string
	 * @param value that will be stored
	 */
	
	public ElementString(String value) {
		super();
		this.value = value;
	}
	@Override 
	public String asText() {
		return value;
	}
	/**
	 * Getter for private property
	 * @return string value
	 */
	public String getValue() {
		return value;
	}

}
