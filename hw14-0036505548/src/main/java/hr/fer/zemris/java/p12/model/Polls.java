package hr.fer.zemris.java.p12.model;

/**
 * Public class polls
 * 
 * @author af
 *
 */
public class Polls {
	/**
	 * ID
	 */
	private long id;
	/**
	 * Title
	 */
	private String title;
	/**
	 * Message
	 */
	private String message;

	/**
	 * public constructor
	 */
	public Polls() {
	}

	/**
	 * Getter for id
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * GEtter for title
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	

}
