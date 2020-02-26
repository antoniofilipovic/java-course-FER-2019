package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.component.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.provider.IColorProvider;

/**
 * This class represents circle tool
 * 
 * @author af
 *
 */
public class CircleTool implements Tool {
	/**
	 * Center
	 */
	private Point center;
	/**
	 * Endpoint
	 */
	private Point endPoint;
	/**
	 * Radius
	 */
	private int radius;
	/**
	 * Model
	 */

	private DrawingModel model;
	/**
	 * Foreground color
	 */
	private IColorProvider foregroundColor;
	/**
	 * CAnvas
	 */
	private JDrawingCanvas canvas;
	/**
	 * Click
	 */
	private int click;

	/**
	 * Public constructor
	 * 
	 * @param model           model
	 * @param foregroundColor foregroud color
	 * @param canvas          canvas
	 */
	public CircleTool(DrawingModel model, IColorProvider foregroundColor, JDrawingCanvas canvas) {
		this.model = model;
		this.foregroundColor = foregroundColor;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		click++;
		Point point = e.getPoint();
		if (click == 1) {
			center = point;
			// System.out.println("postavili smo startpoint");

		}
		if (click == 2) {
			endPoint = point;
			updateRadius(center, endPoint);
			model.add(new Circle(center, radius, foregroundColor.getCurrentColor()));

			click = 0;
			center = null;
			endPoint = null;
			radius = 0;

		}

	}

	private void updateRadius(Point center2, Point endPoint2) {
		radius = (int) Math
				.sqrt(Math.pow(endPoint2.getX() - center2.getX(), 2) + Math.pow(endPoint2.getY() - center2.getY(), 2));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (click <= 1 && center != null) {
			endPoint = e.getPoint();
			canvas.repaint();
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// do nothing

	}

	@Override
	public void paint(Graphics2D g2d) {
		if (center == null) {
			return;
		}

		updateRadius(center, endPoint);
		g2d.setColor(foregroundColor.getCurrentColor());
		g2d.drawOval((int) center.getX() - radius, (int) center.getY() - radius, radius * 2, radius * 2);

	}

}
