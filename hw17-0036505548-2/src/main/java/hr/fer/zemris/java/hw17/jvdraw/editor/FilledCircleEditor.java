package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.FilledCircle;

/**
 * This class reprsents editor for circle. You can edit color, radius and center
 * point
 * 
 * @author af
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Filled circle
	 */
	private FilledCircle filledCircle;
	/**
	 * Centerx
	 */
	private JLabel centerX = new JLabel("Center X");
	/**
	 * Center x field
	 */
	private JTextField centerXField;
	/**
	 * CEnter y
	 */
	private JLabel centerY = new JLabel("Center Y");
	/**
	 * Centery field
	 */
	private JTextField centerYField;
	/**
	 * Radius
	 */
	private JLabel radius = new JLabel("Radius");
	/**
	 * Radius field
	 */
	private JTextField radiusField;
	/**
	 * Radius int
	 */
	private int radiusInt;
	/**
	 * background color
	 */

	private JLabel colorBackGround = new JLabel("color background");
	/**
	 * Area background
	 */
	private JColorArea areaBackGround;
	/**
	 * Color foreground
	 */
	private JLabel colorForeGround = new JLabel("color foreground");
	/**
	 * Area foreground
	 */
	private JColorArea areaForeGround;
	/**
	 * Center x
	 */

	private int centerXInt;
	/**
	 * Center y
	 */
	private int centerYInt;
	/**
	 * Color chosen background
	 */

	private Color colorChosenBackground;
	/**
	 * Color chosen foreground
	 */
	private Color colorChosenForeGround;

//	private final int CANVAS_MAX_WIDTH = 500;
//	private final int CANVAS_MAX_HEIGHT = 500;
	/**
	 * Public construcotr
	 * @param filledCircle filled circle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		initGui();

	}
	/**
	 * Initializer of gui
	 */
	private void initGui() {

		setLayout(new GridLayout(0, 1));

		centerXField = new JTextField(String.valueOf(((int) filledCircle.getCenter().getX())));

		centerYField = new JTextField(String.valueOf(((int) filledCircle.getCenter().getY())));
		radiusField = new JTextField(String.valueOf(((int) filledCircle.getRadius())));

		JPanel firstPanel = new JPanel();
		firstPanel.add(centerX);
		firstPanel.add(centerXField);

		add(firstPanel);

		JPanel secondPanel = new JPanel();
		secondPanel.add(centerY);
		secondPanel.add(centerYField);

		add(secondPanel);

		JPanel thirdPanel = new JPanel();
		thirdPanel.add(radius);
		thirdPanel.add(radiusField);
		add(thirdPanel);

		JPanel fourthPanel = new JPanel();
		fourthPanel.add(colorForeGround);
		areaForeGround = new JColorArea(filledCircle.getForegroundColor());
		fourthPanel.add(areaForeGround);
		add(fourthPanel);

		JPanel fifthPanel = new JPanel();
		fifthPanel.add(colorBackGround);
		areaBackGround = new JColorArea(filledCircle.getBackgroundColor());
		fifthPanel.add(areaBackGround);
		add(fifthPanel);

	}

	@Override
	public void checkEditing() {

		centerXInt = Integer.parseInt(centerXField.getText());
		centerYInt = Integer.parseInt(centerYField.getText());

		radiusInt = Integer.parseInt(radiusField.getText());

		// baa
//ostavljam ovo ovako jer mi je receno da se te stvari nisu trebale provjeravat prosle godine tak da eto sam da "postoji" da vidite da sam mislio na to
//		int widthCanvas = 0;
//		int heigthCanvas = 0;
//
//		Component[] components = this.getParent().getComponents();
//		for (Component c : components) {
//			if (c instanceof JDrawingCanvas) {
//				widthCanvas = c.getWidth();
//				heigthCanvas = c.getHeight();
//
//			}
//		}
//		if (centerXInt > CANVAS_MAX_WIDTH || centerXInt < 0) {
//			throw new RuntimeException("Start X and end X coordinate must be between 0 and " + CANVAS_MAX_WIDTH);
//		}
//		if (centerYInt > CANVAS_MAX_HEIGHT || centerYInt < 0) {
//			throw new RuntimeException("Start Y and end y coordinate must be between 0 and " + CANVAS_MAX_HEIGHT);
//		}

		if (radiusInt < 0) {
			throw new RuntimeException("Radius cant be smaller than zero");
		}

		colorChosenBackground = areaBackGround.getCurrentColor();
		colorChosenForeGround = areaForeGround.getCurrentColor();

	}

	@Override
	public void acceptEditing() {
		filledCircle.setCenter(new Point(centerXInt, centerYInt));
		filledCircle.setRadius(radiusInt);
		filledCircle.setBackgroundColor(colorChosenBackground);
		filledCircle.setForegroundColor(colorChosenForeGround);
		filledCircle.updateProperties();

	}

}
