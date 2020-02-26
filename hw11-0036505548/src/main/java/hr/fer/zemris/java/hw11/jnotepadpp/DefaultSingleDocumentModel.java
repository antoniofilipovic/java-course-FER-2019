package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class represents impl for single document model
 * 
 * @author af
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Editor
	 */
	private JTextArea editor;
	/**
	 * path to file
	 */
	private Path openedFilePath;
	/**
	 * true if file is modified
	 */
	private boolean modified;
	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Single document model
	 * 
	 * @param path
	 * @param text
	 */
	public DefaultSingleDocumentModel(Path path, String text) {
		openedFilePath = path;
		editor = new JTextArea();
		editor.setText(text);
		listeners = new ArrayList<>();
		addDocumentListener();

	}

	/**
	 * Adds document listener for given model
	 */
	private void addDocumentListener() {
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				notifyListeners();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				notifyListeners();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
				notifyListeners();

			}
		});

	}

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return openedFilePath;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null) {
			throw new NullPointerException("Path can't be null.");
		}
		openedFilePath = path;
		notifyListenersPath();

	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyListeners();

	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);

	}

	/**
	 * Notifies listeners when status is updated
	 */
	private void notifyListeners() {
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	/**
	 * Notifies listeners when path is updated
	 */
	private void notifyListenersPath() {
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

}
