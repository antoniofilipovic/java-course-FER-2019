package hr.fer.zemris.java.hw07.observer1;

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
	public void valueChanged(IntegerStorage istorage);
}