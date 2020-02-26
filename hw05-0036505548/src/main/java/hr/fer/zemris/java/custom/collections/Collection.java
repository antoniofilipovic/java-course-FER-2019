package hr.fer.zemris.java.custom.collections;

/**
 * This interface represents Collection for all other classes which will
 * implement this one and add implementation for all methods. By doing this each
 * class is able to implement its own way of solving problems.
 * 
 * @author Antonio Filipovic
 * @version 2.0
 *
 */

public interface Collection<T> {
	/**
	 * Method returns true if collection contains no objects and false otherwise.
	 * 
	 * @return true if collection is empty, false otherwise
	 */

	public default boolean isEmpty() {
		return size()==0;
	}

	/**
	 * This method returns size of Collection.
	 * 
	 * @return size of collection
	 */
	public int size();

	/**
	 * This method adds value to collection.
	 * 
	 * @param value that will be added.
	 */
	public void add(T value);

	/**
	 * This method returns true if collection contains value, false otherwise
	 * 
	 * @param value that collection may contain
	 * @return true if value is contained, false otherwise
	 */

	public boolean contains(Object value);

	/**
	 * value that is removed from collection
	 * 
	 * @param value that will be removed if it is actually in collection
	 * @return true if value has been removed
	 */

	public boolean remove(Object value);

	/**
	 * This method returns array of objects
	 * 
	 * @return array of objects
	 */

	public Object[] toArray();

	/**
	 * This method will process each object in collection.
	 * 
	 * @param processor that will do something with that collection.
	 */

	public default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter=createElementsGetter();
		while(getter.hasNextElement()) {
			T value=getter.getNextElement();
			processor.process(value);
		}
		
	}

	/**
	 * Default method adds into the current collection all elements from the given
	 * collection. This other collection remains unchanged.
	 * 
	 * @param other collection whose elements will be added in this collection
	 */

	public default void addAll(Collection<? extends T> other) {
		Processor<T> processor = value -> add(value);
		other.forEach(processor);
	}

	/**
	 * This method clears all values from collection.
	 */
	public void clear();

	/**
	 * This method returns new ElementsGetter that will get elements when needed
	 * 
	 * @return new ElementsGetter
	 */

	public ElementsGetter<T> createElementsGetter();

	/**
	 * This method gets all elements from given collection and adds them to
	 * collection if they have passed the test from tester.
	 * 
	 * @param col    collection that is given
	 * @param tester that will test elements
	 */

	public default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter=col.createElementsGetter();
		while(getter.hasNextElement()) {
			T value=getter.getNextElement();
			if(tester.test(value)) {
				add(value);
			}
		}
	}
}