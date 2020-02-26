package hr.fer.zemris.java.hw17.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class reprsents vecotr
 * 
 * @author af
 *
 */
public class Vector {
	/**
	 * values
	 */
	private Map<String, Integer> values = new HashMap<String, Integer>();

	/**
	 * constructor public
	 * 
	 * @param values values
	 */
	public Vector(Map<String, Integer> values) {
		super();
		this.values = values;
	}

	/**
	 * Getter for values
	 * 
	 * @return
	 */
	public Map<String, Integer> getValues() {
		return values;
	}

	/**
	 * Setter for values
	 * 
	 * @param values
	 */
	public void setValues(Map<String, Integer> values) {
		this.values = values;
	}

	/**
	 * Getter for vector product
	 * 
	 * @param a a
	 * @param b b
	 * @return double
	 */
	public static double getVectorProduct(Map<String, Double> a, Map<String, Double> b) {
		double sum = 0;

		Set<Entry<String, Double>> aSet = a.entrySet();
		for (Entry<String, Double> v1 : aSet) {
			sum += v1.getValue() * b.get(v1.getKey());
		}

		return sum;
	}

}
