package hr.fer.zemris.java.custom.collections;

/**
 * This interface represent the processor. It is a model  capable of
 * performing some operation on the passed object.
 * 
 * @author Antonio Filipovic
 * @version 2.0
 *
 */

public interface Processor {
	/**
	 * This method  processes value
	 * @param value that will be processed.
	 */
	public void process(Object value);
}
