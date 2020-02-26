package hr.fer.zemris.java.hw07.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Double value: %d%n", istorage.getValue() * 2);
		counter--;
		if (counter == 0) {
			istorage.removeObserver(this);
		}

	}

}
