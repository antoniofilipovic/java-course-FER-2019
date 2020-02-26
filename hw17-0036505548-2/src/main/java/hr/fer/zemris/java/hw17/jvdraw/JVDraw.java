package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.component.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.visitor.SaveVisitor;

/**
 * This class represents jvdraw, for creating objects like lines circles and
 * filled circles. Also it enables editing objects
 * 
 * @author af
 *
 */
public class JVDraw extends JFrame implements Supplier<Tool> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Status panel
	 */
	private JPanel statusPanel;
	/**
	 * Status panel text
	 */
	private JStatusLabel statusPanelText;
	/**
	 * Foreground
	 */
	private JColorArea foreground;
	/**
	 * Background
	 */
	private JColorArea background;
	/**
	 * line tool
	 */
	private LineTool lineTool;
	/**
	 * Circle tool
	 */
	private CircleTool circleTool;
	/**
	 * Filled Circle tool
	 */
	private FilledCircleTool filledCircleTool;
	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * canvas
	 */
	private JDrawingCanvas jDrawingCanvas;
	/**
	 * Jlist
	 */
	private JList<GeometricalObject> jList;
	/**
	 * Save path
	 */
	private Path savePath;
	/**
	 * Current tool
	 */
	private Tool currentTool;

	/**
	 * Public constructor
	 */
	public JVDraw() {
		setTitle("JVDraw");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplication.actionPerformed(null);

			}

		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		initGui();
	}

	/**
	 * Initializer of gui
	 */
	private void initGui() {
		Container cp = getContentPane();

		cp.setLayout(new BorderLayout());

		drawingModel = new DrawingModelImpl();

		jDrawingCanvas = new JDrawingCanvas(this, drawingModel);

		cp.add(jDrawingCanvas, BorderLayout.CENTER);
		ListModel<GeometricalObject> listModel = new DrawingObjectListModel(drawingModel);
		jList = new JList<>(listModel);
		cp.add(new JScrollPane(jList), BorderLayout.EAST);

		createMenu();
		createToolBar();
		addStatusPanel(cp);
		addJListListeners();

	}

	/**
	 * Adder of listeners
	 */
	private void addJListListeners() {
		JFrame frame = this;

		jList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickCount = e.getClickCount();
				GeometricalObject o = jList.getSelectedValue();
				if (clickCount == 2 && o != null) {
					GeometricalObjectEditor editor = o.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(frame, editor, "Editor",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();

						} catch (Exception ex) {
							System.out.println(ex.getMessage());
						}

					}

				}

			}
		});
		jList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				GeometricalObject o = jList.getSelectedValue();
				if (o == null) {
					return;
				}
				if (e.getKeyCode() == KeyEvent.VK_PLUS) {
					drawingModel.changeOrder(o, -1);// moving up on list
				} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					drawingModel.changeOrder(o, +1);// moving down on list
				}

				else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					drawingModel.remove(o);
				}

			}
		});

	}

	/**
	 * Adder for status panel
	 * 
	 * @param cp container
	 */
	private void addStatusPanel(Container cp) {

		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		cp.add(statusPanel, BorderLayout.SOUTH);

		statusPanelText = new JStatusLabel(foreground, background);
		statusPanelText.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusPanelText, BorderLayout.WEST);

	}

	/**
	 * Creator of toolbar
	 */
	private void createToolBar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.setSelected(true);

		JToggleButton circleButton = new JToggleButton("Circle");

		// Group the radio buttons.

		JToggleButton filledCircleButton = new JToggleButton("Filled Circle");
		ButtonGroup group = new ButtonGroup();

		group.add(lineButton);
		group.add(circleButton);
		group.add(filledCircleButton);

		foreground = new JColorArea(Color.RED);
		background = new JColorArea(Color.BLUE);

		tb.add(foreground);
		tb.add(background);
		foreground.setSize(getPreferredSize());
		background.setSize(getPreferredSize());

		tb.add(lineButton);
		tb.add(circleButton);
		tb.add(filledCircleButton);

		lineButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lineTool == null) {
					lineTool = new LineTool(drawingModel, foreground, jDrawingCanvas);
				}

				currentTool = lineTool;

			}
		});
		circleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (circleTool == null) {
					circleTool = new CircleTool(drawingModel, foreground, jDrawingCanvas);
				}

				currentTool = circleTool;

			}
		});
		filledCircleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (filledCircleTool == null) {
					filledCircleTool = new FilledCircleTool(drawingModel, foreground, jDrawingCanvas, background);
				}

				currentTool = filledCircleTool;

			}
		});

		lineButton.doClick();

		getContentPane().add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * Creator of menu
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("File");

		file.add(new JMenuItem(openFile));

		file.add(new JMenuItem(saveFile));
		file.add(new JMenuItem(saveAsFile));
		file.add(new JMenuItem(exportAction));

		file.addSeparator();

		file.add(exitApplication);
		mb.add(file);
		setJMenuBar(mb);

	}

	/**
	 * This action opens jvd file only and creates scene
	 */
	private final Action openFile = new AbstractAction("Open") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (drawingModel.isModified()) {
				int option = JOptionPane.showConfirmDialog(JVDraw.this,
						"There have been some changes. Press Yes to save, No to reject and Cancel to abort action");
				if (option == JOptionPane.YES_OPTION) {
					saveFile.actionPerformed(null);

				}
				if (option == JOptionPane.CANCEL_OPTION) {
					return;

				}

			}

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			jfc.setFileFilter(new FileNameExtensionFilter("JVD FILES", "*.jvd", "jvd"));
			if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path openedFilePath = jfc.getSelectedFile().toPath();
			if (!String.valueOf(openedFilePath).endsWith(".jvd")) {
				JOptionPane.showMessageDialog(JVDraw.this, "Only reading .jvd extensions.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!Files.isReadable(openedFilePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, "You don't have permission to read this file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				List<String> lines = Files.readAllLines(openedFilePath, StandardCharsets.UTF_8);
				drawingModel.clear();

				for (String s : lines) {
					GeometricalObject o = parseLine(s);
					if (o == null) {
						continue;
					}
					drawingModel.add(o);
				}
				savePath = openedFilePath;
				drawingModel.clearModifiedFlag();

			} catch (IOException e1) {
				System.out.println("Error occured while reading this file");
			}

		}

	};

	/**
	 * This method parses line from jvd file
	 * 
	 * @param s line
	 * @return object
	 */
	private GeometricalObject parseLine(String s) {
		if (s.isEmpty()) {
			return null;
		}
		GeometricalObject o = null;
		String[] parts = s.split(" ");

		if (parts[0].equals("LINE")) {
			try {
				o = new Line(new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
						new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])),
						new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7])));

			} catch (Exception e) {
				return null;
			}
		} else if (parts[0].equals("CIRCLE")) {
			try {
				o = new Circle(new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
						Integer.parseInt(parts[3]),
						new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])));
			} catch (Exception e) {
				return null;
			}

		} else if (parts[0].equals("FCIRCLE")) {
			try {
				o = new FilledCircle(new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
						Integer.parseInt(parts[3]),
						new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9])),
						new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])));
			} catch (Exception e) {
				return null;
			}

		}
		return o;

	}

	/**
	 * This action represents saving what was painted
	 */
	private final Action saveFile = new AbstractAction("Save") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (savePath == null) {
				savePath = chooseFilePath("Save file");
				if (savePath == null) {
					return;
				}
				
				if (!(String.valueOf(savePath).endsWith(".jvd"))) {

					savePath = Paths.get(String.valueOf(savePath) + ".jvd");
				}
			}
			if (drawingModel.isModified() == true) {
				byte[] bytes = getTextForSavingFile().getBytes(StandardCharsets.UTF_8);
				try {
					Files.write(savePath, bytes);
					drawingModel.clearModifiedFlag();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "File wasn't save due to error.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}

		}
	};

	/**
	 * This method chooses file path
	 * 
	 * @param text text for chooser
	 * @return path
	 */
	private Path chooseFilePath(String text) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(text);
		jfc.setFileFilter(new FileNameExtensionFilter("JVD FILES", "*.jvd", "jvd"));

		if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {// jel se treba ovo vrtit
			JOptionPane.showMessageDialog(JVDraw.this, "You didn't choose file.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return null;

		}
		return jfc.getSelectedFile().toPath();
	}

	/**
	 * This method represents getter for text to save to file
	 * 
	 * @return
	 */
	private String getTextForSavingFile() {
		StringBuilder sb = new StringBuilder();
		int numberOfFiles = drawingModel.getSize();
		GeometricalObjectVisitor visitor = new SaveVisitor(sb);
		for (int i = 0; i < numberOfFiles; i++) {
			GeometricalObject o = drawingModel.getObject(i);
			o.accept(visitor);
		}

		return sb.toString();

	}

	/**
	 * This action represents saving document as some file
	 */
	private final Action saveAsFile = new AbstractAction("Save as") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Path saveFilePath = chooseFilePath("Save as file");
			if (saveFilePath == null) {
				return;
			}
			savePath = saveFilePath;
			if (!(String.valueOf(savePath).endsWith(".jvd"))) {
				savePath = Paths.get(String.valueOf(savePath) + ".jvd");
			}
			byte[] bytes = getTextForSavingFile().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(savePath, bytes);
				drawingModel.clearModifiedFlag();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(JVDraw.this, "File wasn't save due to error.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	};

	/**
	 * This action represents exiting
	 */
	private final Action exitApplication = new AbstractAction("Exit") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (drawingModel.isModified()) {
				int option = JOptionPane.showConfirmDialog(JVDraw.this,
						"There have been some changes. Press Yes to save, No to reject changes or "
								+ "Cancel to abort closing");
				if (option == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Saving file.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					saveFile.actionPerformed(null);

					dispose();
				}
				if (option == JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Aborting action.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					return;

				}

				if (option == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Rejecting changes.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();

				}
			} else {
				dispose();
			}
		}
	};

	/**
	 * This action represents exporting document as picture
	 */
	private final Action exportAction = new AbstractAction("Export") {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Export");
			jfc.setFileFilter(new FileNameExtensionFilter("IMAGE FILES", "jpg", "gif", "png"));

			if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {// jel se treba ovo vrtit
				JOptionPane.showMessageDialog(JVDraw.this, "You didn't choose file.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;

			}
			Path savePath = jfc.getSelectedFile().toPath();
			if (!(String.valueOf(savePath).endsWith(".png") || String.valueOf(savePath).endsWith(".jpg")
					|| String.valueOf(savePath).endsWith(".gif"))) {
				JOptionPane.showMessageDialog(JVDraw.this, "You need to choose gif, png or jpg format.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			File file = new File(String.valueOf(savePath));
			if (file.exists()) {
				int option = JOptionPane.showConfirmDialog(JVDraw.this,
						"File already exists. Press Yes to overwrite, No to reject  or " + "Cancel to abort ");
				if (option != JOptionPane.YES_OPTION) {
					return;
				}
			}

			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for (int i = 0; i < drawingModel.getSize(); i++) {
				drawingModel.getObject(i).accept(bbcalc);
			}

			Rectangle box = bbcalc.getBoundingBox();
			System.out.println(box.x+","+box.y);

			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g2d = image.createGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, box.width, box.height);

			g2d.translate(-box.x, -box.y);
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
			for (int i = 0; i < drawingModel.getSize(); i++) {
				drawingModel.getObject(i).accept(painter);
			}
			g2d.dispose();
			String extension = determineExtension(file.getName());

			try {
				ImageIO.write(image, extension, file);
			} catch (IOException e1) {
				System.out.println("exception");
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Image was exported.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

		}

	};

	/**
	 * This method determines extension
	 * 
	 * @param name
	 * @return
	 */
	private String determineExtension(String name) {
		return name.substring(name.lastIndexOf(".") + 1);

	}

	/**
	 * This method starts when main programm starts
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	@Override
	public Tool get() {
		return currentTool;
	}

}
