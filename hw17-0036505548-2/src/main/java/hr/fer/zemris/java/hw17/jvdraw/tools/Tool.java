package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This class represents tool
 * 
 * @author af
 *
 */
public interface Tool {
	/**
	 * Mouse pressed
	 * 
	 * @param e
	 */
	void mousePressed(MouseEvent e);

	/**
	 * Mouse relsesd
	 * 
	 * @param e
	 */
	void mouseReleased(MouseEvent e);

	/**
	 * Mouse clicked
	 * 
	 * @param e
	 */
	void mouseClicked(MouseEvent e);

	/**
	 * Mouse moved
	 * 
	 * @param e
	 */

	void mouseMoved(MouseEvent e);

	/**
	 * Mouse dragged
	 * 
	 * @param e
	 */

	void mouseDragged(MouseEvent e);

	/**
	 * Paint
	 * 
	 * @param g2d
	 */

	void paint(Graphics2D g2d);

}
