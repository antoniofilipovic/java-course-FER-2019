package hr.fer.zemris.java.hw17.jvdraw.geomtrical.object;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.listener.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectVisitor;
/**
 * Filled circle
 * @author af
 *
 */
public class FilledCircle extends GeometricalObject {
	/**
	 * listeners
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	/**
	 * Center
	 */
	private Point center;
	/**
	 * Radius
	 */
	private int radius;
	/**
	 * background color
	 */
	private Color backgroundColor;
	/**
	 * Foreground color
	 */
	private Color foregroundColor;
	/**
	 * Filled circle
	 * @param center center
	 * @param radius  radius
	 * @param backgroundColor background color
	 * @param foregroundColor background color
	 */
	public FilledCircle(Point center, int radius, Color backgroundColor, Color foregroundColor) {
		super();

		this.center = center;
		this.radius = radius;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
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
		return new FilledCircleEditor(this);
	}
	/**
	 * Center
	 * @return
	 */
	public Point getCenter() {
		return center;
	}
	/**
	 * set center
	 * @param center
	 */
	public void setCenter(Point center) {
		this.center = center;
	}
	/**
	 * Getter for radius
	 * @return
	 */
	public int getRadius() {
		return radius;
	}
	/**
	 * Setter for radius
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}
	/**
	 * Getter for background color
	 * @return
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	/**
	 * Setter for background color
	 * @param backgroundColor
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	/**
	 * Getter for foreground color
	 * @return
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}
	/**
	 * Setter for foreground color
	 * @param foregroundColor
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	
	@Override
	public String toString() {
		return "Filled circle ("+(int)center.getX()+","+(int)center.getY()+"),"+ radius+"#"+(Integer.toHexString(backgroundColor.getRGB()).substring(2));
	}
	/**
	 * Update properties
	 */
	public void updateProperties() {
		notifyObjectChanged();
	}
	/**
	 * Notifeis changed object
	 */
	private void notifyObjectChanged() {
		for(GeometricalObjectListener l:listeners) {
			l.geometricalObjectChanged(this);
		}
		
	}

}
