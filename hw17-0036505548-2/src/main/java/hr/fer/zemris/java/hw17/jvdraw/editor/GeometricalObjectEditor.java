package hr.fer.zemris.java.hw17.jvdraw.editor;

import javax.swing.JPanel;

/**
 * This class represents geom obj editor
 * 
 * @author af
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method checks editong
	 */

	public abstract void checkEditing();

	/**
	 * Thi method accepts editing
	 */

	public abstract void acceptEditing();
}
