package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;

/**
 * This class represents line editor
 * 
 * @author af
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Line
	 */
	private Line line;
	/**
	 * Stertx
	 */
	private JLabel startX = new JLabel("Start X");
	/**
	 * Startx field
	 */
	private JTextField startXField;
	/**
	 * Starty
	 */
	private JLabel startY = new JLabel("Start Y");
	/**
	 * Startyfield
	 */
	private JTextField startYField;
	/**
	 * Endx
	 */
	private JLabel endX = new JLabel("End X");
	/**
	 * Endx field
	 */
	private JTextField endXField;
	/**
	 * Endy
	 */
	private JLabel endY = new JLabel("End Y");
	/**
	 * Endy field
	 */
	private JTextField endYField;
	/**
	 * Color
	 */
	private JLabel color = new JLabel("color");
	/**
	 * Area
	 */
	private JColorArea area;
	/**
	 * Startx
	 */

	private int startXInt;
	/**
	 * Startyint
	 */
	private int startYInt;
	/**
	 * End x
	 */
	private int endXInt;
	/**
	 * Endy int
	 */
	private int endYInt;
	/**
	 * Color chosen
	 */
	private Color colorChosen;

//	
//	private final int CANVAS_MAX_WIDTH=500;
//	private final int CANVAS_MAX_HEIGHT=500;
	/**
	 * Public constructor
	 * 
	 * @param line
	 */
	public LineEditor(Line line) {
		this.line = line;
		initGui();

	}

	/**
	 * Initiealizer of gui
	 */
	private void initGui() {
		setLayout(new GridLayout(0, 1));

		startXField = new JTextField(String.valueOf(((int) line.getStartPoint().getX())));

		startYField = new JTextField(String.valueOf(((int) line.getStartPoint().getY())));

		endXField = new JTextField(String.valueOf(((int) line.getEndPoint().getX())));

		endYField = new JTextField(String.valueOf(((int) line.getEndPoint().getY())));

		JPanel firstPanel = new JPanel();
		firstPanel.add(startX);
		firstPanel.add(startXField);

		add(firstPanel);

		JPanel secondPanel = new JPanel();
		secondPanel.add(startY);
		secondPanel.add(startYField);

		add(secondPanel);

		JPanel thirdPanel = new JPanel();
		thirdPanel.add(endX);
		thirdPanel.add(endXField);

		add(thirdPanel);

		JPanel fourthPanel = new JPanel();
		fourthPanel.add(endY);
		fourthPanel.add(endYField);

		add(fourthPanel);

		JPanel fifthPanel = new JPanel();
		fifthPanel.add(color);
		area = new JColorArea(line.getColor());
		fifthPanel.add(area);
		add(fifthPanel);

	}

	@Override
	public void checkEditing() {

		startXInt = Integer.parseInt(startXField.getText());
		startYInt = Integer.parseInt(startYField.getText());
		endXInt = Integer.parseInt(endXField.getText());
		endYInt = Integer.parseInt(endYField.getText());

		// baa

//		int widthCanvas=0;
//		int heigthCanvas=0;
//		
//		
//		Component[] components=this.getParent().getComponents();
//		for(Component c:components) {
//			if(c instanceof JDrawingCanvas) {
//				widthCanvas=c.getWidth();
//				heigthCanvas=c.getHeight();
//				System.out.println("našli smo ga");
//			}
//		}
//		if(startXInt>CANVAS_MAX_WIDTH || startXInt<0 || endXInt>CANVAS_MAX_WIDTH || endXInt<0) {
//			throw new RuntimeException("Start X and end X coordinate must be between 0 and"+ CANVAS_MAX_WIDTH);
//		}
//		if(startYInt>CANVAS_MAX_HEIGHT || startYInt<0 || endYInt>CANVAS_MAX_WIDTH || endYInt<0) {
//			throw new RuntimeException("Start Y and end y coordinate must be between 0 and"+ CANVAS_MAX_HEIGHT);
//		}

		colorChosen = area.getCurrentColor();

	}

	@Override
	public void acceptEditing() {
		line.setStartPoint(new Point(startXInt, startYInt));
		line.setEndPoint(new Point(endXInt, endYInt));
		line.setColor(colorChosen);
		line.updateProperties();

	}

}
