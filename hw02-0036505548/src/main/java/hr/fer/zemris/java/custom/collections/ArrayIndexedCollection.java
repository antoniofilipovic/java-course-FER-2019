package hr.fer.zemris.java.custom.collections;

/**
 * This class represents implementation of resizable array-backed collection of
 * objects denoted as ArrayIndexedCollection which extends class Collection. It
 * can't contain null values but same objects are supported.
 * 
 * @author Antonio Filipovic
 * @version 1.0
 *
 */

public class ArrayIndexedCollection extends Collection {
	public static final int DEFAULT_CAPACITY = 16;
	public static final int DOUBLE = 2;

	/**
	 * Represents current size of collection (number of elements actually stored in
	 * {@link elements} array)
	 */
	private int size = 0;
	/**
	 * Represents an array of object references which length is determined by
	 * {@link capacity}
	 */

	private Object[] elements = null;
	/**
	 * Current capacity of collection
	 */
	private int capacity = 0;

	/**
	 * This is default constructor. Sets capacity to {@link DEFAULT_CAPACITY}
	 */

	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * This constructor sets capacity of {@link elements} to {@link initialCapacity}
	 * which can't be smaller than 1.
	 * 
	 * @throws IllegalArgumentException if {@link initialCapacity} is smaller than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * This constructor copies all elements from Collection {@link coll} to
	 * {@link elements} array.
	 * 
	 * @throws NullPointerException if {@link coll} is null
	 */
	public ArrayIndexedCollection(Collection coll) {
		this(coll, coll.size());

	}

	/**
	 * This constructor copies all elements from Collection {@link coll} to
	 * {@link elements} array and sets capacity to initialCapacity.
	 * 
	 * @throws IllegalArgumentException if initialCapacity is smaller than one
	 * @throws NullPointerException     if {@link coll} is null
	 */

	public ArrayIndexedCollection(Collection coll, int initialCapacity) {
		if (coll == null) { //
			throw new NullPointerException();
		}
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		if (initialCapacity < coll.size()) {
			initialCapacity = coll.size();
		}
		capacity = initialCapacity;
		elements = new Object[capacity];
		addAll(coll);

	}

	/**
	 * This method returns capacity.
	 * 
	 * @return int {@link capacity}
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * {@inheritDoc} Adds the given object into this collection into first empty
	 * place in the elements array If the elements array is full, elements array is
	 * reallocated and its size doubles.
	 * 
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (size == capacity) {
			allocateMoreMemory();
		}
		elements[size] = value;
		size++;

	}

	/**
	 * Returns the object that is stored in backing array at position index. Valid
	 * indexes are 0 to size-1.
	 * 
	 * @throws IndexOutOfBoundsException if index is invalid
	 * @param index of element whose value it returns
	 * @return value of object on that index
	 */

	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];

	}

	/**
	 * {@inheritDoc}. Removes all elements from the collection. The allocated array
	 * is left at current capacity Writes null references into the backing array
	 */

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;

	}

	/**
	 * Inserts the given value at the given position in array The legal positions
	 * are 0 to size (both are included).
	 * 
	 * @param value    that is inserted
	 * @param position on which it is inserted
	 * @throws IndexOutOfBoundsException if position is invalid
	 * @throws NullPointerException      if value is null
	 */

	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (value == null) {
			throw new NullPointerException();
		}
		if (size == capacity) {
			allocateMoreMemory();
		}
		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;

	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. Argument can be null and the
	 * result must be that this element is not found
	 * 
	 * @param value that is searched
	 * @return index of value
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1.
	 * 
	 * @throws IndexOutOfBoundsException in case of invalid index
	 * @param index of objet that will be removed
	 */

	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}
		size--;

	}

	/**
	 * {@inheritDoc} Checks whether this collection contains element.
	 * 
	 * @return true if it contains,s false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc} Method removes first occurance of object if this object exists
	 * in collection. It uses method remove(int index)
	 * 
	 * @return true if object is removed, false otherwise.
	 */
	@Override
	public boolean remove(Object value) {
		int indexOf = this.indexOf(value);
		if (indexOf == -1) {
			return false;
		}
		this.remove(indexOf);
		return true;
	}

	/**
	 * {@inheritDoc} Method is implemented here to return Object array of values in
	 * this collection.
	 * 
	 * @return Object array
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}
		return array;
	}

	/**
	 * {@inheritDoc} Objects are sent one by one in row.
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * {@inheritDoc} Returns size of elements array.
	 * 
	 * @return int value
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Private method that allocates more memory by doubling its capacity.
	 */
	private void allocateMoreMemory() {
		capacity *= DOUBLE;
		Object[] array = elements;
		elements = new Object[capacity];
		for (int i = 0; i < size; i++) {
			elements[i] = array[i];
		}
	}

}
