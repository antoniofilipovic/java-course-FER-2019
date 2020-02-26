package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.IntegerStorageChange;

/**
 * This class doubles each value for only defined number of times. After that it
 * removes itself from list of observers.
 * 
 * @author Antonio Filipović
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	private int counter;

	public DoubleValue(int i) {
		counter = i;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("Double value: %d%n", istorage.getNewValue() * 2);
		counter--;
		if (counter == 0) {
			istorage.getInternStorage().removeObserver(this);
		}

	}

}
