package hr.fer.zemris.java.hw17.jvdraw.geomtrical.object;

import hr.fer.zemris.java.hw17.jvdraw.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.listener.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectVisitor;
/**
 * Geometrical object
 * @author af
 *
 */
public abstract class GeometricalObject {
	/**
	 * adder for listenrer
	 */
	public abstract void addGeometricalObjectListener(GeometricalObjectListener l);
	/**
	 * Remover for listenr
	 * @param l
	 */
	public abstract void removeGeometricalObjectListener(GeometricalObjectListener l);
	/**
	 * accept
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	/**
	 * creater of object editor
	 * @return
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}
