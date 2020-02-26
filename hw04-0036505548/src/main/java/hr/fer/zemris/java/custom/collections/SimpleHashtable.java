package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents more serious implementation of map. Private static
 * class {@link TableEntry} is used for storing keys and values inside of table.
 * Position in table on which every entry will be stored depends on hashCode of
 * key. Keys cannot be null, but values can.
 * 
 * @author Antonio Filipovic
 *
 * @param <K> parameter of key
 * @param <V> parameter of value
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * This constant defines minimum size for table
	 */
	private static final int MIN_SIZE = 1;
	/**
	 * This constant defines default capacity for table
	 */
	private static final int DEFAULT_CAPACITY = 16;
	/**
	 * number 1
	 */
	private static final int ZERO = 0;

	/**
	 * This is table of slots used for storing TableEntries
	 */
	private TableEntry<K, V>[] table;
	/**
	 * This is number of pairs stored in table;
	 */
	private int size;
	/**
	 * Counter for modification of table;
	 */

	private long modificationCount;
	/**
	 * Number of slots in table that are not null
	 */

	private int numberOfSlotsFilled;

	/**
	 * This is public default constructor. Creates table with 16 entires.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * This constructor receives capacity and creates table of entries whose
	 * capacity is number that is exponent of number 2.
	 * 
	 * @param capacity of table that will be created
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < MIN_SIZE) {
			throw new IllegalArgumentException("Initital capacity should be larger than 1.");
		}
		capacity = checkCapacity(capacity);
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * This method puts new TableEntry in table if there isnt already one with this
	 * key, than it just changes its value.
	 * 
	 * @param key   that will be put
	 * @param value value that will be put
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key must not be null.");
		}
		TableEntry<K, V> temp = getTableEntry(key);
		if (temp == null) {
			modificationCount++;
			size++;
			int index = calculateIndexOfSlot(key);
			TableEntry<K, V> tmp = table[index];
			if (tmp != null) {
				while (tmp.next != null) {
					tmp = tmp.next;
				}
				tmp.next = new TableEntry<>(key, value);
			} else {
				numberOfSlotsFilled++;
				table[index] = new TableEntry<>(key, value);
				if (numberOfSlotsFilled * 1.0 / table.length >= 0.75) {
					reallocateTable();
				}
			}
		} else {
			temp.setValue(value);
		}
	}

	/**
	 * This method gets value of given object
	 * 
	 * @param key
	 * @return
	 */
	public V get(Object key) {
		TableEntry<K, V> temp = getTableEntry(key);
		if (key == null || temp == null)
			return null;
		return temp.getValue();
	}

	/**
	 * This method returns size of table
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * This method returns true if map contains key, false otherwise.
	 * 
	 * @param key that map may contain
	 * @return true if map contains key, false otherwise
	 */
	public boolean containsKey(Object key) {
		TableEntry<K, V> temp = getTableEntry(key);
		if (temp == null)
			return false;
		return temp != null;

	}

	/**
	 * This method returns true if map contains value, false otherwise. Value can be
	 * null.
	 * 
	 * @param value that map may contain
	 * @return true if map contains value
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> temp = table[i];
			while (temp != null) {
				if (value == null && temp.getValue() == null) {
					return true;
				} else if (temp.getValue().equals(value)) {
					return true;
				}
				temp = temp.next;
			}

		}
		return false;
	}

	/**
	 * This method removes entry with given key if possible
	 * 
	 * @param key that will be removed
	 */
	public void remove(Object key) {
		if (containsKey(key)) {
			modificationCount++;
			size--;
			int index = calculateIndexOfSlot(key);
			TableEntry<K, V> temp = table[index];
			if (!temp.getKey().equals(key)) {// if it is not the first one
				while (temp.next != null && !temp.next.getKey().equals(key)) {// while there is next one or we found it
					temp = temp.next;
				}
				temp.next = temp.next.next;

			} else {// if it is the first one
				table[index] = temp.next;
			}
		}
	}

	/**
	 * This method returns true if {@link SimpleHashtable} is empty, false otherwise
	 * 
	 * @return true if empty,false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = numberOfSlotsFilled = 0;
		modificationCount++;
	}

	/**
	 * This method returns String representation of table, so that entries in same
	 * slot are printed in same line
	 */
	@Override
	public String toString() {
		int current = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> temp = table[i];
			while (temp != null) {
				if (current != ZERO && current != size) {
					sb.append(", ");
				}
				current += 1;
				sb.append(temp.getKey() + "=" + temp.getValue());
				temp = temp.next;
			}

		}
		sb.append("]");
		return sb.toString();
	}
// odkomentiraj ovo i zakomentiraj ovaj klasicni toString za bolji uvid
//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < table.length; i++) {
//			TableEntry<K, V> temp = table[i];
//			sb.append("index:" + i);
//			while (temp != null) {
//
//				sb.append("   " + temp.getKey() + "=" + temp.getValue());
//				temp = temp.next;
//			}
//			sb.append("\n");
//		}
//		return sb.toString();
//	}

	public int getCapacity() {
		return table.length;
	}

	/**
	 * This method creates new Iterator
	 */

	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * This method reallocates entries in new table that is two times bigger
	 */
	@SuppressWarnings("unchecked")
	private void reallocateTable() {
		TableEntry<K, V>[] tempTable = table;
		table = (TableEntry<K, V>[]) new TableEntry[tempTable.length * 2];
		TableEntry<K, V> tempEntry = null;
		int index = 0;
		size = numberOfSlotsFilled = 0;
		modificationCount++;
		while (true) {
			if (tempEntry == null) {
				tempEntry = tempTable[index++];
			}
			while (tempEntry != null) {
				put(tempEntry.key, tempEntry.value);
				tempEntry = tempEntry.next;
			}
			if (index == tempTable.length) {
				break;
			}
		}

	}

	/**
	 * This method returns capacity that is exponent of number 2. This exponent of
	 * number 2 can be equal to {@link capacity} or it is first next exponent bigger
	 * than capacity.
	 * 
	 * @param capacity
	 * @return exponent of number 2
	 */
	private int checkCapacity(int capacity) {
		int i = 1;
		while (i < capacity) {
			i *= 2;
		}
		return i;
	}

	/**
	 * This method calculates index slot in table for given key
	 * 
	 * @param key for which it calculates slot
	 * @return int index
	 */
	private int calculateIndexOfSlot(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 * This method returns tableEntry with given key
	 * 
	 * @param key of tableEntry that we are searching for
	 * @return {@link TableEntry}
	 */
	private TableEntry<K, V> getTableEntry(Object key) {
		if (key == null)
			return null;
		int index = calculateIndexOfSlot(key);
		TableEntry<K, V> temp = table[index];
		while (temp != null) {
			if (temp.getKey().equals(key)) {
				return temp;
			}
			temp = temp.next;
		}
		return null;

	}

	/**
	 * This private class represents entry for storing key and value
	 * 
	 * @author Antonio Filipovic
	 *
	 * @param <E> it is parameter of key
	 * @param <T> parameter of value
	 */
	public static class TableEntry<E, T> {
		/**
		 * This is value of key.
		 */
		private E key;
		/**
		 * This field represents value of given key.
		 */
		private T value;
		/**
		 * This is pointer to next TableEntry
		 */
		private TableEntry<E, T> next;

		public TableEntry(E key, T value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}

		/**
		 * This is public getter for keys
		 * 
		 * @return key
		 */
		public E getKey() {
			return key;
		}

		/**
		 * This is public getter for value
		 * 
		 * @return value
		 */
		public T getValue() {
			return value;
		}

		/**
		 * This is public setter for value
		 * 
		 * @param value new value of TableEntry
		 */
		public void setValue(T value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.key + " " + this.value;
		}
	}

	/**
	 * This class represents implementation of Iterator that can iterate through all
	 * table entries
	 * 
	 * @author af
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * This represents number of retrieved elements
		 */
		private int tableEntriesReceived;
		/**
		 * This represents current index in table
		 */
		private int currentIndex;
		/**
		 * This represents current table entry
		 */
		private TableEntry<K, V> currentTableIndexedEntry = null;
		/**
		 * if next was called and element can be removed {@link nextWasCalled} sets to
		 * true
		 */

		private boolean nextWasCalled = false;
		/**
		 * Modification count for iterator
		 */
		private long modificationCountIter;

		/**
		 * size when iter was called
		 */
		private int sizeIter;

		public IteratorImpl() {
			modificationCountIter = SimpleHashtable.this.modificationCount;
			sizeIter = SimpleHashtable.this.size;
		}

		/**
		 * This method returns true if there is next element to be returned
		 * 
		 * @return true if there is next element, false otherwise
		 */
		@Override
		public boolean hasNext() {
			if (modificationCountIter != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Modification was made.");
			}
			return tableEntriesReceived < sizeIter;
		}

		/**
		 * This method returns next element
		 * 
		 * @return next {@link TableEntry} if there is one
		 * @throws NoSuchElementException if there is not next entry
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationCountIter != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Modification was made.");
			}
			if (hasNext()) {
				nextWasCalled = true;
				if (currentTableIndexedEntry != null && currentTableIndexedEntry.next != null) {
					currentTableIndexedEntry = currentTableIndexedEntry.next;

				} else {
					currentTableIndexedEntry = SimpleHashtable.this.table[currentIndex++];
					while (currentTableIndexedEntry == null) {
						currentTableIndexedEntry = SimpleHashtable.this.table[currentIndex++];

					}
				}
				tableEntriesReceived++;
				return currentTableIndexedEntry;
			}
			throw new NoSuchElementException("There is no next element.");
		}

		/**
		 * Removes element once per call of method next
		 * 
		 * @throws IllegalStateException if there is no element to be removed
		 */
		public void remove() {
			if (modificationCountIter != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Modification was made.");
			}
			if (nextWasCalled == false) {
				throw new IllegalStateException("Method next has not yet been call or was called already.");
			}

			nextWasCalled = false;
			modificationCountIter++;
			SimpleHashtable.this.remove(currentTableIndexedEntry.getKey());

		}
	}
}
