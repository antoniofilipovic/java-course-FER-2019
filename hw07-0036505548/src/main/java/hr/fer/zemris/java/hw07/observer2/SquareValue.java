package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.IntegerStorageChange;

/**
 * This class squares given value and prints it to console with appropriate
 * message.
 * 
 * @author Antonio Filipović
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {// overflow check
		int value = istorage.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
