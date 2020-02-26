package hr.fer.zemris.java.hw17.jvdraw.visitor;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;
/**
 * This class represents painter
 * @author af
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * Graphics
	 */
	private Graphics2D g2d;
	/**
	 * Constructor
	 * @param g2d graphihcs
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;

	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());

		g2d.drawLine((int) line.getStartPoint().getX(), (int) line.getStartPoint().getY(),
				(int) line.getEndPoint().getX(), (int) line.getEndPoint().getY());

	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval((int) (circle.getCenter().getX() - circle.getRadius()),
				(int) (circle.getCenter().getY() - circle.getRadius()), circle.getRadius() * 2, circle.getRadius() * 2);

	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(filledCircle.getForegroundColor());
		g2d.fillOval((int) (filledCircle.getCenter().getX() - filledCircle.getRadius() - 3),
				(int) (filledCircle.getCenter().getY() - filledCircle.getRadius() - 3),
				filledCircle.getRadius() * 2 + 5, filledCircle.getRadius() * 2 + 5);

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(filledCircle.getBackgroundColor());
		g2d.fillOval((int) (filledCircle.getCenter().getX() - filledCircle.getRadius()),
				(int) (filledCircle.getCenter().getY() - filledCircle.getRadius()), filledCircle.getRadius() * 2,
				filledCircle.getRadius() * 2);

	}

}
