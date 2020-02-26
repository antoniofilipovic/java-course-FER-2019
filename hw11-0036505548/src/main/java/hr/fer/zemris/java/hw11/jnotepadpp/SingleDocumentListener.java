package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface for single document listener
 * 
 * @author af
 *
 */
public interface SingleDocumentListener {
	/**
	 * This method will be called in listener impl when status is modified
	 * 
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * This method will be called in listener impl when path is modified
	 * 
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
