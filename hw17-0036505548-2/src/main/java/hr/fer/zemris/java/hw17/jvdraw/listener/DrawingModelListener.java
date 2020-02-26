package hr.fer.zemris.java.hw17.jvdraw.listener;

import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * drawing model listener
 * 
 * @author af
 *
 */
public interface DrawingModelListener {
	/**
	 * Objects added
	 * 
	 * @param source source
	 * @param index0 index0
	 * @param index1 index1
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Objects removed
	 * 
	 * @param source source
	 * @param index0 index0
	 * @param index1 index1
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Objects changed
	 * 
	 * @param source source
	 * @param index0 index0
	 * @param index1 index1
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
