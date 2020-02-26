package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * This class represents support for multiple document model
 * 
 * @author af
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	/**
	 * List of models
	 */
	private List<SingleDocumentModel> models;
	/**
	 * current model
	 */
	private SingleDocumentModel currentModel;
	/**
	 * listeners
	 */
	private List<MultipleDocumentListener> listeners;
	/**
	 * Green diskete icon
	 */
	private static final String GREEN_DISKETE = "greenDiskette.png";
	/**
	 * Red diskette icon
	 */

	private static final String RED_DISKETE = "redDiskette.png";
	/**
	 * Current frame
	 */
	private JFrame frame;

	/**
	 * Public constructor
	 */
	public DefaultMultipleDocumentModel(JFrame frame) {
		models = new ArrayList<>();
		listeners = new ArrayList<>();
		this.frame = frame;
		addChangeListener(e -> {
			SingleDocumentModel old = currentModel;
			int selected = getSelectedIndex();
			if (selected >= 0) {
				currentModel = models.get(getSelectedIndex());
				notifyListenersChangedDocument(old, currentModel);
				frame.setTitle(getToolTipTextAt(selected));
			}
			if(selected<0) {
				frame.setTitle("JNotepad++");
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return models.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newModel = addNewSingleDocument(null, null);
		int index = getTabCount();
		addTab("Unnamed", ImageLoader.getIconImage(GREEN_DISKETE), new JScrollPane(newModel.getTextComponent()),
				"Unnamed - JNotepad++");
		setSelectedIndex(index);
		notifyListenersAdded(newModel);
		notifyListenersChangedDocument(currentModel, newModel);
		currentModel = newModel;
		return newModel;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		for (SingleDocumentModel m : models) {
			if (m != null && m.getFilePath() != null && m.getFilePath().equals(path)) {
				setSelectedIndex(models.indexOf(m));
				JOptionPane.showMessageDialog(frame, "File is already opened.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				return m;
			}
		}
		String text = null;

		try {
			text = Files.readString(path, StandardCharsets.UTF_8);
		} catch (IOException | OutOfMemoryError e) {
			return null;
		}

		SingleDocumentModel newModel = addNewSingleDocument(path, text);

		int index = getTabCount();
		addTab(newModel.getFilePath().getFileName().toString(), ImageLoader.getIconImage(GREEN_DISKETE),
				new JScrollPane(newModel.getTextComponent()), newModel.getFilePath().toString()+" - JNotepad++");
		setSelectedIndex(index);
		notifyListenersAdded(newModel);
		notifyListenersChangedDocument(currentModel, newModel);
		currentModel = newModel;

		return newModel;

	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentModel;
	}

	/**
	 * This method creates new single document from given path and text
	 * 
	 * @param path path
	 * @param text text
	 * @return new singledocument
	 */
	private SingleDocumentModel addNewSingleDocument(Path path, String text) {
		SingleDocumentModel newModel = new DefaultSingleDocumentModel(path, text);
		newModel.addSingleDocumentListener(new SingleDocumentListenerImpl());
		models.add(newModel);

		return newModel;

	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(!(model.getFilePath()!=null && newPath!=null && model.getFilePath().equals(newPath))) {
			if (Files.exists(newPath)) {
				newPath = fileExistsChoose(newPath);
				if (newPath == null) {
					return;
				}
			}
		}
		

		for (SingleDocumentModel m : models) {
			if (m.equals(model)) {
				continue;
			}
			if (m != null && m.getFilePath() != null && m.getFilePath().equals(newPath)) {
				JOptionPane.showMessageDialog(frame, "Error", "File is already opened.", JOptionPane.ERROR_MESSAGE);
				setSelectedIndex(models.indexOf(m));
				return;
			}
		}

		try {
			byte[] bytes = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
			Files.write(newPath, bytes);
			JOptionPane.showMessageDialog(frame, "File was saved", "Information", JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(frame, "There was error while saving file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.setFilePath(newPath);
		currentModel.setModified(false);

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int oldIndex = models.indexOf(model);

		int newIndex = oldIndex - 1;
		if (newIndex < 0) {
			newIndex = 0;
		}
		removeTabAt(oldIndex);
		models.remove(model);
		if (models.size() == 0) {
			currentModel = null;
			newIndex = -1;
		}
		if (newIndex >= 0) {
			setSelectedIndex(newIndex);
			currentModel = models.get(newIndex);
		}
		notifyListenersRemoved(model);
		notifyListenersChangedDocument(model, currentModel);

	}

	/**
	 * This method chooses what to do if file path exists
	 * 
	 * @param newPath
	 * @return
	 */
	private Path fileExistsChoose(Path newPath) {

		int option = JOptionPane.showConfirmDialog(frame, "File already exists. Would you like to overwrite it?");
		if (option == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(frame, "Overwriting file.", "Information", JOptionPane.INFORMATION_MESSAGE);
			return newPath;
		}
		if (option == JOptionPane.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(frame, "Canceling saving file.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

		}

		if (option == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(frame, "File won't be overwritten.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

		}

		return null;

	}

	/**
	 * This method notifies all listeners when document is added
	 * 
	 * @param document
	 */
	private void notifyListenersAdded(SingleDocumentModel document) {
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(document);
		}
	}

	/**
	 * This method notifies all listeners when document is removed
	 * 
	 * @param document
	 */
	private void notifyListenersRemoved(SingleDocumentModel document) {
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(document);
		}
	}

	/**
	 * This method notifies all listeners when document is changed
	 * 
	 * @param document
	 */
	private void notifyListenersChangedDocument(SingleDocumentModel oldDocument, SingleDocumentModel newDocument) {
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(oldDocument, newDocument);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return models.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index >= models.size()) {
			throw new IndexOutOfBoundsException();
		}
		return models.get(index);
	}

	/**
	 * This is implementaiton for listener for single document
	 * 
	 * @author af
	 *
	 */
	private class SingleDocumentListenerImpl implements SingleDocumentListener {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if (model.isModified()) {
				setIconAt(models.indexOf(model), ImageLoader.getIconImage(RED_DISKETE));
			} else {
				setIconAt(models.indexOf(model), ImageLoader.getIconImage(GREEN_DISKETE));
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			setTitleAt(models.indexOf(model), String.valueOf(model.getFilePath().getFileName()));
			setToolTipTextAt(models.indexOf(model), model.getFilePath().toString()+" - JNotepad++");

		}

	}

	@Override
	public void activateDocument(SingleDocumentModel model) {
		if (models.contains(model)) {
			setSelectedIndex(models.indexOf(model));
		}

	}

}
