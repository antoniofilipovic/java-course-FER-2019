package hr.fer.zemris.java.custom.collections;



/**
 * This class represents dictionary, which uses ArrayIndexedCollection as
 * adaptee and offers simple implementation for users.
 * 
 * @author Antonio Filipovic
 *
 * @param <K> represents type for keys
 * @param <V> represents type for values
 */

public class Dictionary<K, V> {
	/**
	 * This is private variable used for storing {@link Element} in array
	 */
	private ArrayIndexedCollection<Element<K, V>> coll;

	/**
	 * This class represents object that stores key and value. Key cannot be null,
	 * but value can.
	 * 
	 * @author Antonio
	 *
	 */
	private class Element<E, F> {
		private E key;
		private F value;

		/**
		 * This is public constructor for Element
		 * 
		 * @param key   that will be stored
		 * @param value of that key
		 */
		public Element(E key, F value) {
			if (key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
		}

		/**
		 * This method returns key
		 * 
		 * @return key
		 */
		public E getKey() {
			return key;
		}

		/**
		 * This method returns value
		 * 
		 * @return value
		 */
		public F getValue() {
			return value;
		}

		/**
		 * This method sets value
		 * 
		 * @return value
		 */
		public void setValue(F value) {
			this.value = value;
		}

	}

	/**
	 * This is public constructor for Dictionary
	 */
	public Dictionary() {
		coll = new ArrayIndexedCollection<>();
	}

	/**
	 * Returns capacity of dictionary
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return coll.getCapacity();
	}

	/**
	 * This method returns whether dictionary is empty
	 * 
	 * @return true if dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return coll.isEmpty();
	}

	/**
	 * Returns size of dictionary
	 * 
	 * @return size
	 */
	public int size() {
		return coll.size();
	}

	/**
	 * This method clears dictionary
	 */
	public void clear() {
		coll.clear();
	}

	/**
	 * This method puts key and value in dictionary if there isn't already same key
	 * in dictionary. Otherwise it just changes its value
	 * 
	 * @param key   that will be put in dictionary
	 * @param value which will be put in dictionary
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key must not be null.");
		}
		Element<K, V> elem = getElement(key);
		if (elem == null) {
			coll.add(new Element<>(key, value));
		} else {
			elem.setValue(value);
		}

	};

	/**
	 * This method returns value V if there is key K in dictionary
	 * 
	 * @param key whose value we look for
	 * @return
	 */
	
	public V get(Object key) {
		Element<K, V> elem = getElement(key);
		if (elem != null)
			return elem.getValue();
		return null;
	}

	/**
	 * This method returns element with {@link key}
	 * 
	 * @param key that we are looking for
	 * @return element with that key
	 */
	@SuppressWarnings("unchecked")
	private Element<K, V> getElement(Object key) {
		if (key == null)
			return null;
		Object[] elems = coll.toArray();
		for (int i = 0; i < coll.size(); i++) {
			if (((Element<K, V>) elems[i]).getKey().equals(key)) {
				return (Element<K, V>) elems[i];
			}
		}
		return null;

	}

}
