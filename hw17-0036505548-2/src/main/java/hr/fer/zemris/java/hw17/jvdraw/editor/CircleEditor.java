package hr.fer.zemris.java.hw17.jvdraw.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;

/**
 * This class reprsents editor for circle. You can edit color, radius and center
 * point
 * 
 * @author af
 *
 */
public class CircleEditor extends GeometricalObjectEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Circle
	 */
	private Circle circle;
	/**
	 * Center x
	 */

	private JLabel centerX = new JLabel("Center X");
	/**
	 * Center x field
	 */
	private JTextField centerXField;
	/**
	 * Centery
	 */
	private JLabel centerY = new JLabel("Center Y");
	/**
	 * Center y field
	 */
	private JTextField centerYField;
	/**
	 * Radius
	 */
	private JLabel radius = new JLabel("Center Y");
	/**
	 * Radius field
	 */
	private JTextField radiusField;
	/**
	 * Radius int
	 */
	private int radiusInt;
	/**
	 * Color
	 */

	private JLabel color = new JLabel("color");
	/**
	 * Area
	 */
	private JColorArea area;
	/**
	 * Center x
	 */
	private int centerXInt;
	/**
	 * Center y
	 */
	private int centerYInt;
	/**
	 * Chosen color
	 */
	private Color colorChosen;

//	private final int CANVAS_MAX_WIDTH = 500;
//	private final int CANVAS_MAX_HEIGHT = 500;
	/**
	 * Circle editor
	 * 
	 * @param circle circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		initGui();

	}

	/**
	 * This method initialies gui
	 */
	private void initGui() {

		setLayout(new GridLayout(0, 1));

		centerXField = new JTextField(String.valueOf(((int) circle.getCenter().getX())));

		centerYField = new JTextField(String.valueOf(((int) circle.getCenter().getY())));
		radiusField = new JTextField(String.valueOf(((int) circle.getRadius())));

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

		JPanel fifthPanel = new JPanel();
		fifthPanel.add(color);
		area = new JColorArea(circle.getColor());
		fifthPanel.add(area);
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

		colorChosen = area.getCurrentColor();

	}

	@Override
	public void acceptEditing() {
		circle.setCenter(new Point(centerXInt, centerYInt));
		circle.setRadius(radiusInt);
		circle.setColor(colorChosen);
		circle.updateProperties();

	}

}
