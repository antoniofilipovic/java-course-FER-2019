package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.*;

/**
 * This interface is used as strategy. Each class that implements this interface
 * will bi called when value changes
 * 
 * @author Antonio Filipović
 *
 */
public interface IntegerStorageObserver {
	/**
	 * This method is called when value is changed
	 * 
	 * @param istorage value that is changed
	 */
	public void valueChanged(IntegerStorageChange istorage);
}