package hr.fer.zemris.java.custom.collections;

/**
 * This class represents ObjectStack instance. Each will create and manage its
 * own private instance of ArrayIndexedCollection and use it for actual element
 * storage. This way, the methods of ObjectStack will be the methods user
 * expects to exist. It has one private variable 
 * stack - represents ArrayIndexedCollection
 * 
 * @author Antonio Filipovic
 * @version 1.0
 *
 */

public class ObjectStack {
	private ArrayIndexedCollection stack = null;

	/**
	 * Default constructor. Constructs new ArrayIndexedCollection
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}

	/**
	 * Calls method from ArrayIndexedCollection to check if it is empty.
	 * 
	 * @return true if stack is empty, else otherwise.
	 */

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Calls method from ArrayIndexedCollection to check its size
	 * 
	 * @return size
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Calls method from ArrayIndexedCollection to add value on last position.
	 * 
	 * @param value that is added
	 */

	public void push(Object value) {

		stack.add(value);// ovo nam baca error, treba li se on jos uvijek bacit
	}

	/**
	 * Removes and returns last value.
	 * 
	 * @return Object
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		Object obj = peek();
		stack.remove(size() - 1);
		return obj;

	}

	/**
	 * Returns last value but doesnt remove it.
	 * 
	 * @return Object
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if (size() == 0) {
			throw new EmptyStackException("Stack is empty.");
		}
		Object obj = stack.get(size() - 1);
		return obj;
	}
	/**
	 * Clears all elements from stack.
	 */
	public void clear() {
		stack.clear();
	}

}
