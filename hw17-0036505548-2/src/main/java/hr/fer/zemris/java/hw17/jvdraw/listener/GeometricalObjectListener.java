package hr.fer.zemris.java.hw17.jvdraw.listener;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.GeometricalObject;
/**
 * Geometrical object listener
 * @author af
 *
 */
public interface  GeometricalObjectListener {
	/**
	 * Geometrical object changed
	 * @param o
	 */
	public void geometricalObjectChanged(GeometricalObject o);

}
