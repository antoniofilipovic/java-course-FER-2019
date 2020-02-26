package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This class represents Jnotepadpp that supports multiple documents and lot of
 * other actions
 * 
 * @author af
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Documetn model
	 */
	private MultipleDocumentModel documentModel;
	/**
	 * status panel
	 */
	private JPanel statusPanel;
	/**
	 * Length label
	 */
	private JLabel length;
	/**
	 * Postion label
	 */
	private JLabel position;
	/**
	 * localization provider
	 */
	private FormLocalizationProvider formLocalizationProvider = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);
	/**
	 * Timer
	 */
	private Timer timer;
	/**
	 * Dateformat
	 */
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * Public constructor
	 */
	public JNotepadPP() {
		setTitle("JNotepad++");
		documentModel = new DefaultMultipleDocumentModel(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplicationAction.actionPerformed(null);

			}

		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		initGui();
		formLocalizationProvider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				statusBarCalculations(documentModel.getCurrentDocument());

			}
		});
		setLocationRelativeTo(null);
	}
	/**
	 * This method initializes gui
	 */
	private void initGui() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());
		cp.add((JTabbedPane) documentModel);
		addSatusPanel(cp);
		documentModel.addMultipleDocumentListener(new MultipleDocumentListenerImpl());// mora bit iza status panela

		disableActions();
		createActions();
		createMenu();
		createToolBar();

	}

	/**
	 * This method disables actions
	 */
	private void disableActions() {
		saveDocument.setEnabled(false);
		saveAsDocument.setEnabled(false);
		closeTab.setEnabled(false);
		invertCase.setEnabled(false);
		toLowerCase.setEnabled(false);
		toUpperCase.setEnabled(false);
		copyAction.setEnabled(false);
		cutAction.setEnabled(false);
		pasteAction.setEnabled(false);
		ascendingAction.setEnabled(false);
		descendingAction.setEnabled(false);
		uniqueAction.setEnabled(false);
	}

	/**
	 * This method enables actions
	 */
	private void enableActions() {
		saveDocument.setEnabled(true);
		saveAsDocument.setEnabled(true);
		closeTab.setEnabled(true);
		pasteAction.setEnabled(true);

	}

	/**
	 * This method adds status panel
	 * 
	 * @param cp
	 */
	private void addSatusPanel(Container cp) {
		// create the status bar panel and shove it down the bottom of the frame
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		cp.add(statusPanel, BorderLayout.SOUTH);

		length = new JLabel();
		length.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(length, BorderLayout.WEST);

		position = new JLabel();
		position.setHorizontalAlignment(SwingConstants.CENTER);
		statusPanel.add(position, BorderLayout.CENTER);

		JLabel time = new JLabel();
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		statusPanel.add(time, BorderLayout.EAST);

		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				time.setText(dateFormat.format(date)); // 2016/11/16 12:08:43
			}
		});
		timer.setInitialDelay(0);
		timer.start();

	}

	/**
	 * This method creates action
	 */
	private void createActions() {
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("open.png"));

		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("save.png"));

		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);// kaj tu stavit
		saveAsDocument.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("saveAs.png"));

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("copy.png"));

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("cut.png"));

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("paste.png"));

		createNewDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createNewDocument.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("create.png"));

		exitApplicationAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitApplicationAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitApplicationAction.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("closeApp.png"));

		closeTab.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTab.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeTab.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("closeTab.png"));

		invertCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		toUpperCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		toLowerCase.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCase.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		hrLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		hrLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		hrLanguage.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("cro.png"));

		engLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		engLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		engLanguage.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("en.png"));

		deLanguage.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
		deLanguage.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Z);
		deLanguage.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("de.png"));

		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A "));
		ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		statisticalInfo.putValue(Action.SMALL_ICON, ImageLoader.getIconImage("stats.png"));
	}

	/**
	 * This method crates menu
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new LJMenu("file", formLocalizationProvider);

		file.add(new JMenuItem(openDocument));

		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.add(new JMenuItem(createNewDocument));
		file.addSeparator();
		file.add(new JMenuItem(closeTab));
		file.addSeparator();
		file.add(exitApplicationAction);
		mb.add(file);

		JMenu edit = new LJMenu("edit", formLocalizationProvider);
		mb.add(edit);
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(cutAction));
		edit.add(new JMenuItem(pasteAction));

		JMenu tools = new LJMenu("tools", formLocalizationProvider);
		mb.add(tools);

		JMenu changeCase = new LJMenu("changeCase", formLocalizationProvider);
		changeCase.add(new JMenuItem(toLowerCase));
		changeCase.add(new JMenuItem(toUpperCase));
		changeCase.add(new JMenuItem(invertCase));
		tools.add(changeCase);

		JMenu sort = new LJMenu("sort", formLocalizationProvider);
		sort.add(new JMenuItem(ascendingAction));
		sort.add(new JMenuItem(descendingAction));
		sort.add(new JMenuItem(uniqueAction));
		tools.addSeparator();
		tools.add(sort);
		tools.addSeparator();
		tools.add(new JMenuItem(statisticalInfo));

		JMenu languages = new LJMenu("languages", formLocalizationProvider);
		mb.add(languages);
		languages.add(new JMenuItem(hrLanguage));
		languages.add(new JMenuItem(engLanguage));
		languages.add(new JMenuItem(deLanguage));

		setJMenuBar(mb);
	}

	/**
	 * This method creates toolbar
	 */
	private void createToolBar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		tb.add(new JButton(openDocument));
		tb.add(new JButton(createNewDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));
		tb.add(new JButton(statisticalInfo));
		tb.add(new JButton(copyAction));
		tb.add(new JButton(cutAction));
		tb.add(new JButton(pasteAction));
		tb.add(new JButton(closeTab));
		tb.add(new JButton(exitApplicationAction));
		getContentPane().add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * This class represents implementation of multiple document listener
	 * 
	 * @author af
	 *
	 */
	private class MultipleDocumentListenerImpl implements MultipleDocumentListener {
		/**
		 * Holds reference to current listener
		 */
		private ChangeListener listener;

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			if (documentModel.getNumberOfDocuments() == 0) {
				position.setText(" ");
				length.setText(" ");
			}
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			updateStatusBar(model.getTextComponent().getDocument().getLength(), 1, 1, 0);
		}

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			if (previousModel != null) {
				previousModel.getTextComponent().getCaret().removeChangeListener(listener);
			}
			if (currentModel == null) {
				return;
			}

			listener = new ChangeListenerImpl(currentModel);

			JTextComponent c = currentModel.getTextComponent();
			c.requestFocusInWindow();
			updateStatusBar(currentModel.getTextComponent().getDocument().getLength(), 1, 1, 0);
			currentModel.getTextComponent().getCaret().addChangeListener(listener);

		}
	}

	/**
	 * This method updates status bar
	 * 
	 * @param length length
	 * @param ln     line
	 * @param col    column
	 * @param sel    selection
	 */
	private void updateStatusBar(int length, int ln, int col, int sel) {

		this.length.setText(formLocalizationProvider.getString("length") + ":" + length);
		this.position.setText(
				formLocalizationProvider.getString("ln") + ":" + ln + " " + formLocalizationProvider.getString("col")
						+ ":" + col + " " + formLocalizationProvider.getString("sel") + ":" + sel);
	}

	/**
	 * This method represents status bar calculations
	 * 
	 * @param model
	 */
	private void statusBarCalculations(SingleDocumentModel model) {
		if (model == null)
			return;
		JTextComponent c = model.getTextComponent();
		int pos = c.getCaretPosition();
		Document doc = c.getDocument(); // we assume it is instanceof PlainDocument
		Element root = doc.getDefaultRootElement();
		int row = root.getElementIndex(pos); // zero-based line index...
		int col = pos - root.getElement(row).getStartOffset();
		int sel = Math.abs(c.getCaret().getMark() - c.getCaret().getDot());
		boolean selection = sel > 0;
		invertCase.setEnabled(selection);
		toLowerCase.setEnabled(selection);
		toUpperCase.setEnabled(selection);
		copyAction.setEnabled(selection);
		cutAction.setEnabled(selection);
		ascendingAction.setEnabled(selection);
		descendingAction.setEnabled(selection);
		uniqueAction.setEnabled(selection);
		updateStatusBar(doc.getLength(), row + 1, col + 1, sel);
	}

	/**
	 * This class represents change listener
	 * 
	 * @author af
	 *
	 */
	
	private class ChangeListenerImpl implements ChangeListener {
		private SingleDocumentModel model;

		public ChangeListenerImpl(SingleDocumentModel model) {
			this.model = model;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			statusBarCalculations(model);
		}

	}

	/**
	 * This action opens document
	 */
	private final Action openDocument = new LocalizableAction("open", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path openedFilePath = jfc.getSelectedFile().toPath();
			if (!Files.isReadable(openedFilePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "You don't have permission to read this file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			SingleDocumentModel singleDocModel = documentModel.loadDocument(openedFilePath);
			if (singleDocModel == null) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "There was problem with reading this file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			enableActions();

		}

	};
	/**
	 * This action represents saving document
	 */
	private final Action saveDocument = new LocalizableAction("save", formLocalizationProvider) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel model = documentModel.getCurrentDocument();
			Path openedFilePath = model.getFilePath();
			if (openedFilePath == null) {
				openedFilePath = chooseFileToSaveTo("Save file");
				if (openedFilePath == null) {
					return;
				}

			}
			documentModel.saveDocument(model, openedFilePath);

			return;

		}
	};
	/**
	 * This action represents saving document as some file
	 */
	private final Action saveAsDocument = new LocalizableAction("saveAs", formLocalizationProvider) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel model = documentModel.getCurrentDocument();

			Path openedFilePath = chooseFileToSaveTo("Save as file.");
			if (openedFilePath == null) {
				return;
			}

			documentModel.saveDocument(model, openedFilePath);
		}
	};

	/**
	 * This method chooses file to save to
	 * 
	 * @param text to write to window
	 * @return path
	 */
	private Path chooseFileToSaveTo(String text) {
		Path newPath = null;
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		newPath = showMessageDialogChooseFile(text);
		return newPath;

	}

	/**
	 * This text shows dialog to choose file
	 * 
	 * @param text text to show in dialog
	 * @return path
	 */
	private Path showMessageDialogChooseFile(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {// jel se treba ovo vrtit
			JOptionPane.showMessageDialog(JNotepadPP.this, "You didn't choose file.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return null;

		}
		return jfc.getSelectedFile().toPath();

	}

	/**
	 * This method copies text
	 */
	private final Action copyAction = new LocalizableAction("copy", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action action = new DefaultEditorKit.CopyAction();
			action.actionPerformed(e);
		}
	};
	/**
	 * This method cuts text
	 */
	private final Action cutAction = new LocalizableAction("cut", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action action = new DefaultEditorKit.CutAction();
			action.actionPerformed(e);
		}

	};
	/**
	 * This method pastes text
	 */
	private final Action pasteAction = new LocalizableAction("paste", formLocalizationProvider) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Action action = new DefaultEditorKit.PasteAction();
			action.actionPerformed(e);
		}

	};
	/**
	 * This method creates new document
	 */
	private final Action createNewDocument = new LocalizableAction("create", formLocalizationProvider) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			documentModel.createNewDocument();
			enableActions();
		}
	};
	/**
	 * This method exits application and asks user to save work if there is
	 * something unsaved
	 */
	private final Action exitApplicationAction = new LocalizableAction("exit", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int numberOfDocuments = documentModel.getNumberOfDocuments();
			boolean stop = true;
			for (int i = 0; i < numberOfDocuments; i++) {
				SingleDocumentModel model = documentModel.getDocument(0);
				documentModel.activateDocument(model);
				if (actionForTab(model) == false) {
					stop = false;
					break;
				}
				documentModel.closeDocument(documentModel.getDocument(0));
			}
			if (stop) {
				timer.stop();
				dispose();
			}
		}
	};
	/**
	 * This method closes curent tab. If there is unsaved work it asks user to save
	 * it. It doesn't close tab if user presses cancel on first question
	 */
	private final Action closeTab = new LocalizableAction("close_tab", formLocalizationProvider) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel model = documentModel.getCurrentDocument();
			documentModel.activateDocument(model);
			if (actionForTab(model) == false) {
				return;
			}
			documentModel.closeDocument(model);
			if (documentModel.getNumberOfDocuments() == 0) {
				disableActions();
			}
		}
	};

	/**
	 * If tab should be closed it returns true, else false. For current tab it asks
	 * user if there is unsaved work to save it
	 * 
	 * @param model for which it checks if it is modifed
	 * @return true if it should be closed
	 */
	private boolean actionForTab(SingleDocumentModel model) {
		if (model.isModified()) {
			int option = JOptionPane.showConfirmDialog(JNotepadPP.this,
					"There is still here some unsaved work. Would you like to save it?");
			if (option == JOptionPane.YES_OPTION) {
				Path path = showMessageDialogChooseFile("Save file");
				if (path != null) {
					documentModel.saveDocument(model, path);
				}
			}
			if (option == JOptionPane.CANCEL_OPTION) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This action gives stats info, number of lines, non blank chars and number of
	 * chars
	 */
	private final Action statisticalInfo = new LocalizableAction("statistical", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel model = documentModel.getCurrentDocument();

			if (model == null) {
				// showError(formLocalizationProvider.getString("no_document_selected"));
				JOptionPane.showMessageDialog(JNotepadPP.this, "There isn't open dialog", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			JTextArea area = model.getTextComponent();
			String text = area.getText();

			int numberOfChars = text.length();
			int numberofNonBlank = 0;
			char[] data = text.toCharArray();
			for (int i = 0, length = text.length(); i < length; i++) {
				if (!Character.isWhitespace(data[i])) {
					numberofNonBlank++;
				}

			}
			String message = "Your document has " + numberOfChars + " characters, " + numberofNonBlank
					+ " non-blank characters and " + area.getLineCount() + " lines.";

			JOptionPane.showMessageDialog(JNotepadPP.this, message, "Information", JOptionPane.INFORMATION_MESSAGE);

		}

	};
	/**
	 * This action inverts case
	 */
	private final Action invertCase = new LocalizableAction("invert", formLocalizationProvider) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeText("invertCase");

		}

	};
	/**
	 * This action changes text to uppercase
	 */
	private final Action toUpperCase = new LocalizableAction("toUpper", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeText("toUpperCase");

		}

	};
	/**
	 * This action changes text to lower case
	 */
	private final Action toLowerCase = new LocalizableAction("toLower", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeText("toLowerCase");

		}

	};

	/**
	 * This method changes text
	 * 
	 * @param type that will be changed
	 */
	private void changeText(String type) {
		Document doc = documentModel.getCurrentDocument().getTextComponent().getDocument();
		Caret caret = documentModel.getCurrentDocument().getTextComponent().getCaret();
		int start = Math.min(caret.getMark(), caret.getDot());
		int len = Math.abs(caret.getMark() - caret.getDot());
		if (len == 0)
			return;
		try {
			String text = doc.getText(start, len);
			text = toggleText(text, type);
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "There was problem", "Error", JOptionPane.ERROR_MESSAGE);
		}
		;
	}

	/**
	 * This method toggles text, depending on type of action
	 * 
	 * @param text that will be toggled
	 * @param type it can be all to uppercase, to lowercase or invertcase
	 * @return string
	 */
	private String toggleText(String text, String type) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			switch (type) {
			case "toLowerCase":
				chars[i] = Character.toLowerCase(chars[i]);
				break;
			case "toUpperCase":
				chars[i] = Character.toUpperCase(chars[i]);
				break;
			case "invertCase":
				if (Character.isUpperCase(chars[i])) {
					chars[i] = Character.toLowerCase(chars[i]);
				} else {
					chars[i] = Character.toUpperCase(chars[i]);
				}
				break;
			}

		}
		return String.valueOf(chars);
	}

	/**
	 * This action sets language to croatian
	 */

	private final Action hrLanguage = new LocalizableAction("croatian", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");

		}

	};
	/**
	 * This action changes language to english
	 */
	private final Action engLanguage = new LocalizableAction("english", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");

		}

	};
	/**
	 * This action changes language to deutsch
	 */
	private final Action deLanguage = new LocalizableAction("deutsch", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");

		}

	};
	/**
	 * This action sorts lines in asc way
	 */
	private final Action ascendingAction = new LocalizableAction("ascending", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			List<String> lines = getAllLinesWithCondition(false);
			lines = sortLines(lines, true);
			insertLines(lines);

		}

	};
	/**
	 * This action sorts lines in desc way
	 */
	private final Action descendingAction = new LocalizableAction("descending", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> lines = getAllLinesWithCondition(false);
			lines = sortLines(lines, false);
			insertLines(lines);
		}

	};
	/**
	 * This action leaves every unique line
	 */
	private final Action uniqueAction = new LocalizableAction("unique", formLocalizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> lines = getAllLinesWithCondition(true);

			insertLines(lines);

		}

	};

	/**
	 * This method sorts lines in asc or desc way
	 * 
	 * @param lines that will be sorted
	 * @param asc   if true than asc, else desc
	 * @return sorted lines
	 */
	private List<String> sortLines(List<String> lines, boolean asc) {
		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(locale);
		Comparator<String> c = asc == true ? (s1, s2) -> collator.compare(s1, s2)
				: (s1, s2) -> collator.compare(s2, s1);
		lines.sort(c);
		return lines;
	}

	/**
	 * This method inserts lines
	 * 
	 * @param lines
	 */
	private void insertLines(List<String> lines) {
		JTextArea area = documentModel.getCurrentDocument().getTextComponent();
		Document d = area.getDocument();
		Caret caret = area.getCaret();
		int start = Math.min(caret.getMark(), caret.getDot());
		StringBuilder sb = new StringBuilder();
		for (String s : lines) {
			sb.append(s).append("\n");
		}
		sb.setLength(sb.length());
		try {
			d.insertString(start, sb.toString(), null);
		} catch (BadLocationException e) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);

		}
	}

	/**
	 * This method returns all lines, if remove duplicates is set to true than it
	 * removes duplicates
	 * 
	 * @param removeDuplicates removesduplicates if set to treu
	 * @return lines
	 */
	private List<String> getAllLinesWithCondition(boolean removeDuplicates) {
		List<String> lines = new ArrayList<>();

		JTextArea area = documentModel.getCurrentDocument().getTextComponent();
		Document d = area.getDocument();
		Caret caret = area.getCaret();
		int start = Math.min(caret.getMark(), caret.getDot());
		int end = Math.max(caret.getMark(), caret.getDot());

		if (start == end) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Nothing was selected.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// start and end lines
		int startLine;
		int endLine;
		try {
			startLine = area.getLineOfOffset(start);
			endLine = area.getLineOfOffset(end);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		int numberOfLines = endLine - startLine + 1;

		for (int i = 0; i < numberOfLines; i++) {
			try {
				int beginOffset = area.getLineStartOffset(startLine + i);
				int endOffset = area.getLineEndOffset(startLine + i);
				String text = d.getText(beginOffset, endOffset - beginOffset).trim();
				if (text.isEmpty()) {

				}
				if (removeDuplicates) {
					if (lines.contains(text)) {
						continue;
					}
				}
				lines.add(text);
			} catch (BadLocationException e1) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Something went wrong.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

		}
		try {
			d.remove(area.getLineStartOffset(startLine),
					area.getLineEndOffset(endLine) - area.getLineStartOffset(startLine));
			area.setCaretPosition(area.getLineStartOffset(startLine));
		} catch (BadLocationException e) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return lines;

	}

	/**
	 * This method starts when main program stars
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
