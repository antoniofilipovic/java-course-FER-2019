package hr.fer.zemris.java.p12.model;

/**
 * This class represents data model for poll options
 * 
 * @author af
 *
 */
public class PollOptions {
	/**
	 * Id
	 */
	private long id;
	/**
	 * title
	 */
	private String optionTitle;
	/**
	 * Link
	 */
	private String optionLink;
	/**
	 * Pollid
	 */
	private long pollID;
	/**
	 * Votes count
	 */
	private long votesCount;

	/**
	 * Poll options
	 */
	public PollOptions() {
	}

	/**
	 * Get id
	 * 
	 * @return id
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
	 * Getter for title
	 * 
	 * @return title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for title
	 * 
	 * @param optionTitle
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for option link
	 * 
	 * @return link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for option link
	 * 
	 * @param optionLink
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * GEtter for pollid
	 * 
	 * @return
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for pollid
	 * 
	 * @param pollID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * GEtter for votes count
	 * 
	 * @return
	 */
	public long getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	@Override
	public String toString() {
		return "Unos id=" + id;
	}
}
