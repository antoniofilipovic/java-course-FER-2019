package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This interface represetns listener for multipledocument model
 * 
 * @author af
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Method to notify listener when document is changed
	 * 
	 * @param previousModel previous model
	 * @param currentModel  current model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * When document is added this method in listener impl will be called for given
	 * model
	 * 
	 * @param model
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * When document is removed this method in listener impl will be called for
	 * given model
	 * 
	 * @param model
	 */
	void documentRemoved(SingleDocumentModel model);
}
