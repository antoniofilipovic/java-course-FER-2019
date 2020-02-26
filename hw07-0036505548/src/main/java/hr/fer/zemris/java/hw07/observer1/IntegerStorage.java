package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is used for storage of observers. It notifys each of them when
 * there is change of current value.
 * 
 * @author Antonio Filipović
 *
 */
public class IntegerStorage {
	/**
	 * Current value
	 */
	private int value;
	/**
	 * This is list of observers.
	 */
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!

	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * This method adds observer to storage intern list.
	 * 
	 * @param observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer == null) {
			throw new NullPointerException("Observer cannot be null.");
		}
		if (observers.contains(observer)) {
			return;
		}
		observers.add(observer);
	}

	/**
	 * This method 'removes' observer from list so he won't be notified any more.
	 * But it keeps reference in list as null value so there won't be
	 * ConncurentModificationException if some of them removes itself.
	 * 
	 * @param observer that will be removed
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observer == null)
			return;
		observers.remove(observer);

	}

	/**
	 * This method removes all observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * This method returns current value.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * This method sets new value and notifies observers if there was change. It
	 * calls each of them. After that removes all null values from list.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				List<IntegerStorageObserver> copyOfObservers=List.copyOf(observers);
				for (IntegerStorageObserver observer : copyOfObservers) {
					if (observers.contains(observer))
						observer.valueChanged(this);
				}
			}
			
		}
	}
	
}