package hr.fer.zemris.java.hw17.jvdraw.geomtrical.object;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.listener.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * This class represets circle
 * 
 * @author af
 *
 */
public class Circle extends GeometricalObject {
	/**
	 * Listenre
	 */

	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	/**
	 * CEnter
	 */
	private Point center;
	/**
	 * Radius
	 */
	private int radius;
	/**
	 * Color
	 */
	private Color color;

	/**
	 * constructor
	 * 
	 * @param center cente
	 * @param radius rad
	 * @param color  c
	 */
	public Circle(Point center, int radius, Color color) {
		super();

		this.center = center;
		this.radius = radius;
		this.color = color;
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
		return new CircleEditor(this);
	}

	/**
	 * GEtter for center
	 * 
	 * @return
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Setter for center
	 * 
	 * @param center
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * GEtter for radius
	 * 
	 * @return
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter for radis
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * GEtter for color
	 * 
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Circle (" + (int) center.getX() + "," + (int) center.getY() + ")," + radius;
	}

	/**
	 * Update properties
	 */
	public void updateProperties() {
		notifyObjectChanged();
	}

	/**
	 * Notify object changed
	 */
	private void notifyObjectChanged() {
		for (GeometricalObjectListener l : listeners) {
			l.geometricalObjectChanged(this);
		}

	}

}
