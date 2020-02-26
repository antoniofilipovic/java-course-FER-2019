package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
/**
 * This interface represents support for work with multiple documents
 * @author af
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new document
	 * @return new document
	 */
	SingleDocumentModel createNewDocument();
	/**
	 * Getts current document
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();
	/**
	 * Loads document from given path, which cant be null
	 * @param path to load from
	 * @return document
	 */
	SingleDocumentModel loadDocument(Path path);
	/**
	 * Saves document to given path, which cant be null. 
	 * @param model to save
	 * @param newPath new path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	/**
	 * Closes given document
	 * @param model 
	 */
	void closeDocument(SingleDocumentModel model);
	/**
	 * Adds listener
	 * @param l listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Removes listener
	 * @param l listener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Returns number of documents
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	/**
	 * Getts document from given index
	 * @param index of document
	 * @return single document
	 */
	SingleDocumentModel getDocument(int index);
	/**
	 * Activates given document
	 * @param model for document to activate
	 */
	void activateDocument(SingleDocumentModel model);
}
