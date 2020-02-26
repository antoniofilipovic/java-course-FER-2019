package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class is used for representing variable expression
 * @author af
 *
 */

public class ElementVariable extends Element {
	
	/**
	 * This is private property which will store value.
	 */
	
	private String name;
	
	/**
	 * Public constructor for element variable
	 * @param name value of variable
	 */
	
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	/**
	 * This is getter of private property
	 * @return property name
	 */
	
	public String getValue() {
		return name;
	}
	
	@Override 
	public String asText() {
		return name;
	}

	

	

}
