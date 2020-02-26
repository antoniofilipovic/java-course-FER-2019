package hr.fer.zemris.java.custom.collections;

/**
 * This class represents implementation of linked list-backed collection of
 * objects denoted as LinkedListIndexedCollection which extends class
 * Collection. It can't contain null values but same objects are supported. It
 * has three private variables:
 *
 * 
 * 
 * 
 * @author Antonio Filipovic
 * @version 1.0
 *
 */

public class LinkedListIndexedCollection extends Collection {
	/**
	 * This class represents each Node which will be added in collection. It
	 * contains reference to next and previous node and also value of object.
	 *
	 */

	static class ListNode {
		/**
		 * link to prevous node
		 */
		ListNode previous = null;
		/**
		 * link to next node
		 */
		ListNode next = null;
		/**
		 * value of object
		 */
		Object value = null;
	}

	/**
	 * Current size of collection
	 */
	private int size = 0;
	/**
	 * Reference to the first node of the linked list
	 */
	private ListNode first = null;
	/**
	 * Reference to the last node of the linked list
	 */
	private ListNode last = null;

	/**
	 * This is default constructor. Sets first and last to null.
	 */

	public LinkedListIndexedCollection() {
		first = last = null;
	}

	/**
	 * This constructor copies all elements from Collection coll to this collection.
	 * 
	 * @param coll
	 */
	public LinkedListIndexedCollection(Collection coll) {
		if (coll == null) {
			throw new NullPointerException();
		}
		first = last = null;
		addAll(coll);
	}

	/**
	 * {@inheritDoc} Adds the given object into this collection into last position.
	 * 
	 * @throws NullPointerException if value is null
	 */

	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		ListNode node = new ListNode();
		node.value = value;
		if (first == last && last == null) {
			first = node;
			last = first;
		} else {
			last.next = node;
			node.previous = last;
			last = node;
		}
		size++;

	}

	/**
	 * Returns the object that is stored in collection at position index. Valid
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
		if (index <= size / 2) {
			ListNode temp = first;
			for (int i = 0; i < index; i++) {
				temp = temp.next;
			}
			return temp.value;

		}
		ListNode temp = last;
		index = size - index - 1;
		for (int i = 0; i < index; i++) {
			temp = temp.previous;
		}
		return temp.value;

	}

	/**
	 * {@inheritDoc}. Sets first and last to null. Garbage collector takes care of
	 * everything else.
	 * 
	 */

	public void clear() {
		first = last = null;
		size = 0;

	}

	/**
	 * Inserts the given value at the given position in list. The legal positions
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
		ListNode node = new ListNode();
		node.value = value;
		if (position == 0) {
			first.previous = node;
			node.next = first;
			first = node;
		} else if (position == size) {
			last.next = node;
			node.previous = last;
			last = node;
		} else {
			ListNode positionLeft = first;
			for (int i = 0; i < position - 1; i++) {
				positionLeft = positionLeft.next;
			}
			ListNode positionRight = positionLeft.next;
			node.next = positionLeft.next;
			node.previous = positionRight.previous;
			positionLeft.next = positionRight.previous = node;
		}
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
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			if (temp.value.equals(value)) {
				return i;
			}
			temp = temp.next;
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
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			first = first.next;
			first.previous = null;

		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode tempLeft = first;
			for (int i = 0; i < index - 1; i++) {
				tempLeft = tempLeft.next;
			}
			ListNode tempRight = tempLeft.next.next;
			tempLeft.next = tempRight;
			tempRight.previous = tempLeft;
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
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			if (temp.value.equals(value)) {
				return true;
			}
			temp = temp.next;
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
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			array[i] = temp.value;
			temp = temp.next;
		}
		return array;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			processor.process(temp.value);
			temp = temp.next;
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

}
