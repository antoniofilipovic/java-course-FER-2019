package hr.fer.zemris.java.custom.collections;

/**
 * This class represents basic Collection class for all other classes which will
 * inherit this one and add implementation for empty methods.
 * 
 * @author Antonio Filipovic
 * @version 1.0
 *
 */

public class Collection {

	/**
	 * This constructor does nothing.
	 */

	protected Collection() {

	}

	/**
	 * Method returns true if collection contains no objects and false otherwise. In
	 * class Collection, method is implemented to determine result by utilizing
	 * method size()
	 *
	 * @return true if collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size()==0;

	}

	/**
	 * Method returns the number of currently stored objects in this collections. In
	 * class Collection, method implemented to always return zero.
	 * 
	 * @return always returns zero
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method adds the given object into this collection. In class Collection,it is
	 * implemented do nothing.
	 * 
	 * @param value that is added in collection
	 */
	public void add(Object value) {

	}

	/**
	 * Method returns true only if the collection contains given value, as
	 * determined by equals method. In class Collection, always returns false. It is
	 * OK to ask if collection contains <code>null<code\>.
	 * 
	 * @param value for which we determine whether it is contained in this
	 *              collection
	 * @return here always return false
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns true only if the collection contains given value as determined by
	 * equals method and removes one occurrence of it (in this class it is not
	 * specified which one). In class Collection, always return false.
	 * 
	 * @param value
	 * @return true if value has been removed
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method allocates new array with size equals to the size of this collections,
	 * fills it with collection content and returns the array. This method never
	 * returns null. In class Collection,throws UnsupportedOperationException.
	 * 
	 * @return array of objects contained in this collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process(.) for each element of this collection. The
	 * order in which elements will be sent is undefined in this class. In class
	 * Collection, implemented as an empty method.
	 * 
	 * @param processor which will process elements in this collection
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method adds into the current collection all elements from the given
	 * collection. This other collection remains unchanged. Here it is implemented
	 * to define a local processor class whose method process will add each item
	 * into the current collection.
	 * 
	 * @param other collection whose elements will be added in this collection
	 */

	public void addAll(Collection other) {
		/**
		 * This class presents local class which will extend processor and adds all
		 * elements to collection
		 * 
		 *
		 */

		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);
	}

	/**
	 * Removes all elements from this collection. In class Collection, is an empty
	 * method
	 */
	public void clear() {

	}
}
