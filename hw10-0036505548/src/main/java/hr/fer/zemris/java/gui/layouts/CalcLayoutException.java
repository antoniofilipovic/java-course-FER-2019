package hr.fer.zemris.java.gui.layouts;
/**
 * This class represents exception
 * @author af
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Public constructor
	 */
	public CalcLayoutException() {
		super();
	}
	/**
	 * Public constructor
	 * @param s
	 */
	public CalcLayoutException(String s) {
		super(s);
	}

}
