package hr.fer.zemris.java.custom.collections;



/**
 * This interface represents getter that will provide elements one by one from
 * first one that was added to the last one if it contains any more elements.
 * 
 * @author Antonio Filipovic
 *
 */

public interface ElementsGetter<T> {
	/**
	 * This method gets next elements if it didn't get them all.
	 * 
	 * @return Object next element
	 */

	public T getNextElement();

	/**
	 * This class returns true if it has got next element.
	 * 
	 * @return true if it has more elemetns to get from collection,false otherwise
	 */
	public boolean hasNextElement();
	
	/**
	 * This method processes rest of the elements as processor {@link p} wants
	 * @param p processor that determines what will happen with the rest of elements.
	 */
	public default void processRemaining(Processor<? super T> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}

}
