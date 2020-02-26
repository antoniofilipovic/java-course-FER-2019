package hr.fer.zemris.java.hw16.servlets;

/**
 * This class represents picture
 * @author af
 *
 */
public class Picture {

    /**
     * Path
     */
    private String path;
    /**
     * Description
     */
    private String description;
    /**
     * Thimbnail path
     */
    private String thumbnailPath;
    /**
     * Big picture path
     */
    private String fullPicturePath;

    /**
     * Tags
     */
    private String[] tags;

    /**
     * Public constructor
     * @param path
     * @param description
     * @param tags
     */
    public Picture(String path, String description, String[] tags) {
        this.path = path;
        this.description = description;
        this.tags = tags;
    }
    /**
     * Getter for path
     * @return
     */
    public String getPath() {
        return path;
    }
    /**
     * Setter for path
     * @param thumbnailPath
     */
    public void setThumbnailPath(String thumbnailPath) {
    	this.thumbnailPath=thumbnailPath;
    }
    /**
     * Getter for path
     * @return
     */
    public String getThumbnailPath() {
    	return thumbnailPath;
    }
    
    /**
     * Getter for path
     * @return
     */
    public String getFullPicturePath() {
		return fullPicturePath;
	}
    /**
     * Setter for path
     * @param thumbnailPath
     */
	public void setFullPicturePath(String fullPicturePath) {
		this.fullPicturePath = fullPicturePath;
	}
	 /**
     * Getter for desc
     * @return
     */
	public String getDescription() {
        return description;
    }

	 /**
     * Getter for tags
     * @return
     */
    public String[] getTags() {
        return tags;
    }

}