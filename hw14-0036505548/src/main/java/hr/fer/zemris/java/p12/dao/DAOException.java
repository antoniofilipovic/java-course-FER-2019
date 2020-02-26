package hr.fer.zemris.java.p12.dao;

/**
 * This class represents daoexceotion
 * 
 * @author af
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor
	 */
	public DAOException() {
	}

	/**
	 * Public constructor with different parameters
	 * 
	 * @param message            message
	 * @param cause              cause
	 * @param enableSuppression  suppresion
	 * @param writableStackTrace stacktrace
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Public construcotr with message and cause
	 * 
	 * @param message message
	 * @param cause   cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Public constructor with message
	 * 
	 * @param message message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Public constructor with cause
	 * 
	 * @param cause cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}