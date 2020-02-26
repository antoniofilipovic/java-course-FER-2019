package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.component.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.provider.IColorProvider;

/**
 * This class represents linetool
 * 
 * @author af
 *
 */
public class LineTool implements Tool {
	/**
	 * Startpoint
	 */
	private Point startPoint;
	/**
	 * Endpoint
	 */
	private Point endPoint;
	/**
	 * Model
	 */
	private DrawingModel model;
	/**
	 * Foreground color
	 */
	private IColorProvider foregroundColor;
	/**
	 * Canvas
	 */
	private JDrawingCanvas canvas;
	/**
	 * Clicks
	 */
	private int click;

	/**
	 * Public constructor
	 * 
	 * @param model           model
	 * @param foregroundColor color
	 * @param canvas          canvas
	 */
	public LineTool(DrawingModel model, IColorProvider foregroundColor, JDrawingCanvas canvas) {
		this.model = model;
		this.foregroundColor = foregroundColor;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		click++;
		Point point = e.getPoint();
		if (click == 1) {
			startPoint = point;
			// System.out.println("postavili smo startpoint");

		}
		if (click == 2) {
			endPoint = point;
			model.add(new Line(startPoint, endPoint, foregroundColor.getCurrentColor()));
			click = 0;
			startPoint = null;
			endPoint = null;
			// System.out.println("postavili smo endpoint");

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (click <= 1 && startPoint != null) {
			// System.out.println("mičemo miš");
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
		if (endPoint == null || startPoint == null) {
			// done with painting
			return;
		}
		// System.out.println("paint iz line toola");

		g2d.setColor(foregroundColor.getCurrentColor());
		g2d.drawLine((int) startPoint.getX(), (int) startPoint.getY(), (int) endPoint.getX(), (int) endPoint.getY());

	}

}
