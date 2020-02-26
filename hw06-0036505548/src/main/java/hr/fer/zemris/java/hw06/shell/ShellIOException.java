package hr.fer.zemris.java.hw06.shell;

/**
 * This class represents exception thrown in Shell. It is subclass of unchecked
 * exceptions.
 * 
 * @author Antonio Filipović
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is public constructor for shellioexception
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * This constructor enables message for user to be printed
	 * 
	 * @param message for user
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
