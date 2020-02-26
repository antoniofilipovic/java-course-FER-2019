package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * This interface represents singledocument
 * 
 * @author af
 *
 */
public interface SingleDocumentModel {
	/**
	 * Text componet
	 * 
	 * @return
	 */
	JTextArea getTextComponent();

	/**
	 * Current path
	 * 
	 * @return
	 */
	Path getFilePath();

	/**
	 * Sets file path
	 * 
	 * @param path
	 */
	void setFilePath(Path path);

	/**
	 * Returns modified status
	 * 
	 * @return
	 */
	boolean isModified();

	/**
	 * Sets modified status
	 * 
	 * @param modified
	 */
	void setModified(boolean modified);

	/**
	 * Adds single listener
	 * 
	 * @param l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * REmoves single listener
	 * 
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
