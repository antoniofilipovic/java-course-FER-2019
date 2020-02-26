package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.IntegerStorageChange;

/**
 * This class counts number of changes of value in storage.
 * 
 * @author Antonio Filipović
 *
 */ 
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * This variable counts number of changes.
	 */
	private int counter;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		System.out.printf("Number of value changes since tracking: %d%n", counter);

	}

}
