package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This interface represents strategy for operation perform
 * 
 * @author af
 *
 * @param <T>
 */
public interface OperationPerformer<T> {
	/**
	 * This method adds two values
	 * 
	 * @param val1 first value
	 * @param val2 second value
	 * @return sum
	 */
	T performOperationAdd(T val1, T val2);

	/**
	 * This method subs two values
	 * 
	 * @param val1 first value
	 * @param val2 second value
	 * @return result
	 */
	T performOperationSub(T val1, T val2);

	/**
	 * This method multyplies two values
	 * 
	 * @param val1 first value
	 * @param val2 second value
	 * @return result
	 */
	T performOperationMul(T val1, T val2);

	/**
	 * This method divs two values
	 * 
	 * @param val1 first value
	 * @param val2 second value
	 * @return result
	 */
	T performOperationDiv(T val1, T val2);

	int numCompare(T val1, T val2);

}
