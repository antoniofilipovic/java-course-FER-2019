package hr.fer.zemris.java.hw17.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listener.DrawingModelListener;
/**
 * This class represents list model
 * @author af
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Constructor of drwaing object list model
	 * @param drawingModel
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		this.drawingModel=drawingModel;
		this.drawingModel.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.fireIntervalAdded(source, index0, index1);
		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.fireIntervalRemoved(source, index0, index1);
		
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.fireContentsChanged(source, index0, index1);
		
	}
	

}
