package hr.fer.zemris.java.hw17.jvdraw.visitor;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.Line;
/**
 * This class represetnts object visitor
 * @author af
 *
 */
public interface GeometricalObjectVisitor {
	/**
	 * Visitro of line 
	 * @param line line
	 */
	public abstract void visit(Line line);
	/**
	 * circle visitor
	 * @param circle circle
	 */
	 public abstract void visit(Circle circle);
	 /**
	  * Visitor of filled circle
	  * @param filledCircle
	  */
	 public abstract void visit(FilledCircle filledCircle);

}
