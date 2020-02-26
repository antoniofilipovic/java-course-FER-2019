package hr.fer.zemris.java.hw17.jvdraw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listener.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.listener.GeometricalObjectListener;

/**
 * This class represents impl of drawing model
 * 
 * @author af
 *
 */
public class DrawingModelImpl implements DrawingModel {
	/**
	 * listeners
	 */

	private List<DrawingModelListener> listeners = new ArrayList<>();
	/**
	 * Objects
	 */

	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * Modification flag
	 */

	private boolean modificationFlag;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		modificationFlag = true;
		object.addGeometricalObjectListener(new GeometricalObjectListener() {

			@Override
			public void geometricalObjectChanged(GeometricalObject o) {

				notifyListenersChanged(o);

			}

		});
		notifyListenersAdded(object);

	}

	/**
	 * Notifes listenrs when object is added
	 * 
	 * @param o object
	 */
	private void notifyListenersAdded(GeometricalObject o) {
		
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, objects.indexOf(o), objects.indexOf(o));

		}
	}

	/**
	 * notifies when object changed
	 * 
	 * @param source
	 */
	private void notifyListenersChanged(GeometricalObject source) {
		for (DrawingModelListener l : listeners) {
			l.objectsChanged(this, objects.indexOf(source), objects.indexOf(source));

		}

	}

	/**
	 * Notifies when object is removed
	 * 
	 * @param source
	 */
	private void notifyListenersRemoved(GeometricalObject source) {
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, objects.indexOf(source), objects.indexOf(source));

		}

	}

	@Override
	public void remove(GeometricalObject object) {
		if (objects.contains(object)) {
			modificationFlag = true;
			notifyListenersRemoved(object);
			objects.remove(object);
		}

	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int firstIndex = objects.indexOf(object);
		int secondIndex = firstIndex + offset;
		if (secondIndex < 0 || secondIndex >= objects.size()) {
			return;
		}
		modificationFlag = true;
		Collections.swap(objects, firstIndex, secondIndex);
		notifyListenersChanged(objects.get(firstIndex));
		notifyListenersChanged(objects.get(secondIndex));

	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		Iterator<GeometricalObject> iter = objects.iterator();
		while (iter.hasNext()) {
			GeometricalObject o = iter.next();
			notifyListenersRemoved(o);
			iter.remove();

		}

		clearModifiedFlag();

	}

	@Override
	public void clearModifiedFlag() {
		modificationFlag = false;

	}

	@Override
	public boolean isModified() {
		return modificationFlag;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);

	}

}
