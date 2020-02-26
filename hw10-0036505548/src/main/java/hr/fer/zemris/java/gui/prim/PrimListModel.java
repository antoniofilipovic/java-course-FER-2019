package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
/**
 * This class represents implementation of list model and shows primes numbers
 * @author af
 *
 */
public class PrimListModel implements ListModel<Integer>{
	/**
	 * List of prim elements
	 */
	private List<Integer> primeElements = new ArrayList<>();
	/**
	 * Iterator
	 */
	private Iterator<Integer> primIterator;
	/**
	 * All observers
	 */
	List<ListDataListener> observers = new ArrayList<>();
	/**
	 * Public constructor for prime model
	 * @param primDemo
	 */
	public PrimListModel(PrimDemo primDemo) {
			primIterator = primDemo.getIterator();
			primeElements.add(primIterator.next());

		}

	@Override
	public int getSize() {
		return primeElements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primeElements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);

	}
	/**
	 * This method gets new prime number and calls other method to inform all observers
	 */
	public void next() {
		primeElements.add(primIterator.next());
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primeElements.size(),
				primeElements.size());
		elementAdded(event);
	}
	/**
	 * This method informs all observers
	 * @param event
	 */
	private void elementAdded(ListDataEvent event) {
		for (ListDataListener l : observers) {
			l.intervalAdded(event);
		}

	}

}
