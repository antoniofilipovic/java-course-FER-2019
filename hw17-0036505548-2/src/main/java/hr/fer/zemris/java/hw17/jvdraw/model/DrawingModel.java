package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listener.DrawingModelListener;

/**
 * This class represents drawing model
 * 
 * @author af
 *
 */
public interface DrawingModel {
	/**
	 * Getter of size
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Getter of object on index
	 * 
	 * @param index index
	 * @return object
	 */

	public GeometricalObject getObject(int index);

	/**
	 * Adder of object
	 */

	public void add(GeometricalObject object);

	/**
	 * Remover of object
	 * 
	 * @param object object
	 */

	public void remove(GeometricalObject object);

	/**
	 * Change of order
	 * 
	 * @param object object
	 * @param offset offset
	 */

	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Index of object
	 * 
	 * @param object object
	 * @return
	 */

	public int indexOf(GeometricalObject object);

	/**
	 * Clears model
	 */

	public void clear();

	/**
	 * Clears flag
	 */

	public void clearModifiedFlag();

	/**
	 * Is modifed
	 * 
	 * @return
	 */

	public boolean isModified();

	/**
	 * Adder of drawing model listner
	 * 
	 * @param l listenr
	 */

	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Remover of listener
	 * 
	 * @param l lisntern
	 */

	public void removeDrawingModelListener(DrawingModelListener l);

}
