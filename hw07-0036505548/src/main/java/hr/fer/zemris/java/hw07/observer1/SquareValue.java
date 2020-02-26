package hr.fer.zemris.java.hw07.observer1;

/**
 * This class squares given value and prints it to console with appropriate
 * message.
 * 
 * @author Antonio Filipović
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {// overflow check
		int value = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
