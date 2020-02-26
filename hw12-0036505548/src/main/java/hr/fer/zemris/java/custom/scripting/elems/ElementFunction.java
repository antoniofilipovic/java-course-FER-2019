package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents base class for function
 * 
 * @author af
 *
 */

public class ElementFunction extends Element {
	/**
	 * variable that holds function name
	 */
	private String name;

	/**
	 * public constructor
	 * 
	 * @param name
	 */

	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * returns function
	 * 
	 * @return function name
	 */
	public String getName() {
		return name;
	}

}
