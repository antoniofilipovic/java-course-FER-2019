package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents implementation of multistack. With every stack one
 * string is associated. Every stack can contain multiple values, itd. Every
 * rule for stack applies here.
 * 
 * @author Antonio Filipović
 *
 */

public class ObjectMultistack {
	/**
	 * This variable represents multistack
	 */
	private Map<String, MultistackEntry> multiStackMap;

	/**
	 * Public constructor.
	 */
	public ObjectMultistack() {
		multiStackMap = new LinkedHashMap<>();
	}

	/**
	 * This method adds new value in stack with keyName. If it doesn't exist it
	 * creates one. Nor valueWrapper nor key can be null.Inside of valueWrapper can
	 * be null. Idea was to create linked list so that every key from
	 * {@linkplain multiStackMap} points to last {@link ValueWrapper} added in other
	 * words points to last {@link MultistackEntry}
	 * 
	 * @param keyName      name of key
	 * @param valueWrapper value that will be added.
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		if (keyName == null) {
			throw new NullPointerException("Key can't be null.");
		}
		if (valueWrapper == null) {
			throw new NullPointerException("Value can't be null.");
		}
		if (multiStackMap.containsKey(keyName)) {
			MultistackEntry lastEntry = multiStackMap.get(keyName);
			MultistackEntry newEntry = new MultistackEntry(valueWrapper);
			newEntry.next = lastEntry;
			multiStackMap.put(keyName, newEntry);
			return;
		}
		multiStackMap.put(keyName, new MultistackEntry(valueWrapper));
	}

	/**
	 * This method pops from stack last value added associated with given key. If
	 * key doesn't exist than RuntimeExceptio is thrown.
	 * 
	 * @param keyName key of stack
	 * @return value popped from stack
	 * @throws RuntimeException if entry doesn't exist
	 */
	public ValueWrapper pop(String keyName) {
		MultistackEntry entry = multiStackMap.get(keyName);
		if (entry == null) {
			throw new EmptyStackException();
		}
		ValueWrapper value = entry.getValue();
		if (entry.next == null) {
			multiStackMap.remove(keyName);
			return value;
		}
		multiStackMap.put(keyName, entry.next);
		return value;
	}

	/**
	 * Gets last value from stack but doesn't remove it. If stack is empty throws
	 * exception.
	 * 
	 * @param keyName name of key to pop value from
	 * @return valueWrapper of that key
	 * @throws RuntimeException if key doesn't exist
	 */
	public ValueWrapper peek(String keyName) {
		MultistackEntry entry = multiStackMap.get(keyName);
		if (entry == null) {
			throw new EmptyStackException();
		}
		return entry.getValue();
	}

	/**
	 * Returns true if stack is empty, false otherwise for given keyName
	 * 
	 * @param keyName which stack will be checked
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !multiStackMap.containsKey(keyName);
	}

	/**
	 * This class forms linked list of objects, which represents stack.
	 * 
	 * @author Antonio Filipović
	 *
	 */
	public static class MultistackEntry {
		/**
		 * This is value of that entry
		 */
		private ValueWrapper value;
		/**
		 * Reference to next entry
		 */
		private MultistackEntry next;

		/**
		 * Public constructor
		 * 
		 * @param value
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = value;
		}

		/**
		 * Setter for next entry
		 * 
		 * @param next value
		 */
		public void setNext(MultistackEntry next) {
			this.next = next;
		}

		/**
		 * Setter for value
		 * 
		 * @param value for that entry
		 */
		public void setValue(ValueWrapper value) {
			this.value = value;
		}

		/**
		 * Getter for value
		 * 
		 * @return value
		 */
		public ValueWrapper getValue() {
			return this.value;
		}
	}

}
