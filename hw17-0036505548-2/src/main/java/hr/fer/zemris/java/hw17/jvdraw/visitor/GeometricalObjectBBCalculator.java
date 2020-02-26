package hr.fer.zemris.java.hw17.jvdraw.visitor;

import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;

/**
 * CAlculatro of bounding box
 * 
 * @author af
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * minx
	 */
	private int minX=Integer.MAX_VALUE;
	/**
	 * miny
	 */
	private int minY=Integer.MAX_VALUE;
	/**
	 * maxx
	 */
	private int maxX;
	/**
	 * max y
	 */
	private int maxY;

	@Override
	public void visit(Line line) {
		int startX = (int) line.getStartPoint().getX();
		int startY = (int) line.getStartPoint().getY();
		int endX = (int) line.getEndPoint().getX();
		int endY = (int) line.getEndPoint().getY();
		

		calculate(startX, endX, startY, endY);

	}

	@Override
	public void visit(Circle circle) {
		int centerX = (int) circle.getCenter().getX();
		int centerY = (int) circle.getCenter().getY();
		int radius = circle.getRadius();

		int leftX = centerX - radius;
		int rightX = centerX + radius;

		int upY = centerY - radius;
		int downY = centerY + radius;

		calculate(leftX, rightX, upY, downY);

	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int centerX = (int) filledCircle.getCenter().getX();
		int centerY = (int) filledCircle.getCenter().getY();
		int radius = filledCircle.getRadius();

		int leftX = centerX - radius;
		int rightX = centerX + radius;

		int upY = centerY - radius;
		int downY = centerY + radius;

		calculate(leftX, rightX, upY, downY);

	}

	/**
	 * This method gets two x coordinates and two y coordinates for which it checks
	 * if they are maximum or minimum
	 * 
	 * @param x1 x
	 * @param x2 x
	 * @param y1 y
	 * @param y2 y
	 */
	private void calculate(int x1, int x2, int y1, int y2) {
		
		if (x1 < minX) {
			minX = x1;
		}
		if (x1 > maxX) {
			maxX = x1;
		}

		if (x2 < minX) {
			minX = x2;
		}
		if (x2 > maxX) {
			maxX = x2;
		}

		if (y1 < minY) {
			minY = y1;
		}
		if (y1 > maxY) {
			maxY = y1;

		}

		if (y2 < minY) {
			minY = y2;
		}
		if (y2 > maxY) {
			maxY = y2;

		}

	}

	/**
	 * Getter for bounding box
	 * 
	 * @return rect
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, Math.abs(maxX - minX), Math.abs(maxY - minY));
	}

}
