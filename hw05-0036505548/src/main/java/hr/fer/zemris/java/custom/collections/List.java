package hr.fer.zemris.java.custom.collections;

/**
 * This interface extends Collection interface and adds few new methods.
 * 
 * @author Antonio Filipovic
 *
 */

public interface List<T> extends Collection<T> {
	/**
	 * Gets element on given index. Index is valid if it is in range from 0 to
	 * size-1
	 * 
	 * @throws IndexOutOfBoundsException if index is invalid
	 * @param index element on that index will be returned
	 * @return object value;
	 */
	T get(int index);

	/**
	 * Inserts element on given position if position is valid.
	 * 
	 * @param value    that will be inserted
	 * @param position on which will be inserted
	 */

	void insert(T value, int position);

	/**
	 * Returns index of given value
	 * 
	 * @param value which index we search in list.
	 * @return index of value
	 */

	int indexOf(T value);

	/**
	 * Removes value on given index
	 * 
	 * @param index of value that will be removed
	 */

	void remove(int index);

}
