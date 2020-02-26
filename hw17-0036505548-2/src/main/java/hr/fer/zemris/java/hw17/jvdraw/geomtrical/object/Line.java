package hr.fer.zemris.java.hw17.jvdraw.geomtrical.object;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.listener.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectVisitor;
/**
 * This class represents line
 * @author af
 *
 */
public class Line  extends GeometricalObject {
	/**
	 * listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * Startpoint
	 */
	private Point startPoint;
	/**
	 * Endpoint
	 */
	private Point endPoint;
	/**
	 * Color
	 */
	private Color color;
	/**
	 * Constructor
	 * @param startPoint sp
	 * @param endPoint ep
	 * @param color c
	 */
	public Line(Point startPoint,Point endPoint,Color color) {
		this.startPoint=startPoint;
		this.endPoint=endPoint;
		this.color=color;
	}

	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);

	}

	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);

	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);

	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	/**
	 * Getter for start point
	 * @return
	 */
	public Point getStartPoint() {
		return startPoint;
	}
	/**
	 * Setter for start point
	 * @param startPoint
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	/**
	 * Getter for end point
	 * @return
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	/**
	 * setter for end point
	 * @param endPoint
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	/**
	 * Getter for color
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void updateProperties() {
		notifyObjectChanged();
	}
	
	private void notifyObjectChanged() {
		for(GeometricalObjectListener l:listeners) {
			l.geometricalObjectChanged(this);
		}
		
	}

	@Override
	public String toString() {
		return "Line "+"("+(int)startPoint.getX()+","+(int)startPoint.getY()+")-("+(int)endPoint.getX()+","+(int)endPoint.getY()+")";
	}


	

	

}
