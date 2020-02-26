package hr.fer.zemris.java.servlets.glasanje;

/**
 * This class represents data to store values for voting
 * 
 * @author af
 *
 */
public class Data {
	/**
	 * Link to youtube song
	 */
	private String link;
	/**
	 * Name of band
	 */
	private String name;
	/**
	 * ID of band
	 */
	private String ID;
	/**
	 * Number of votes
	 */
	private int votes;

	/**
	 * Public constructor
	 * 
	 * @param ID   id
	 * @param name name of band
	 * @param link link to song
	 */

	public Data(String ID, String name, String link) {
		super();
		this.link = link;
		this.name = name;
		this.ID = ID;
	}

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Setter for votes
	 * 
	 * @param votes votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	/**
	 * Getter for votes
	 * 
	 * @return votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Getter for link to song
	 * 
	 * @return song link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Setter for song link
	 * 
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name of band
	 */
	public void setName(String name) {
		this.name = name;
	}
}
