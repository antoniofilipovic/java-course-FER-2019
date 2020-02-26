package hr.fer.zemris.java.hw17.jvdraw.visitor;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;
/**
 * This clas represnets save visitor 
 * @author af
 *
 */
public class SaveVisitor implements GeometricalObjectVisitor {
	/**
	 * string builder
	 */
	private StringBuilder sb;
	/**
	 * This class represnts save visitor
	 * @param sb string builder
	 */
	public SaveVisitor(StringBuilder sb) {
		this.sb = sb;
	}

	@Override
	public void visit(Line line) {
		sb.append("LINE " + ((int) line.getStartPoint().getX()) + " " + ((int) line.getStartPoint().getY()) + " "
				+ ((int) line.getEndPoint().getX()) + " " + ((int) line.getEndPoint().getY())+ " "+line.getColor().getRed()+" "+
				line.getColor().getGreen()+" "+line.getColor().getBlue());
		sb.append("\n");

	}

	@Override
	public void visit(Circle circle) {
		sb.append("CIRCLE " + ((int) circle.getCenter().getX()) + " " + ((int) circle.getCenter().getY()) + " "
				+ circle.getRadius() + " " + circle.getColor().getRed() + " " + circle.getColor().getGreen() + " "
				+ circle.getColor().getBlue());
		sb.append("\n");

	}

	@Override
	public void visit(FilledCircle circle) {
		sb.append("FCIRCLE " + ((int) circle.getCenter().getX()) + " " + ((int) circle.getCenter().getY()) + " "
				+ circle.getRadius() + " " + circle.getForegroundColor().getRed() + " "
				+ circle.getForegroundColor().getGreen() + " " + circle.getForegroundColor().getBlue() + " "
				+ circle.getBackgroundColor().getRed() + " " + circle.getBackgroundColor().getGreen() + " "
				+ circle.getBackgroundColor().getBlue());
		sb.append("\n");

	}

}
