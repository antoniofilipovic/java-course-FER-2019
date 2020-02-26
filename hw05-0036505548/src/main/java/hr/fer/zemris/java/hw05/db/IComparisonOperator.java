package hr.fer.zemris.java.hw05.db;

/**
 * This interface compares two strings with specified operator
 * 
 * @author Antonio Filipovic
 *
 */
public interface IComparisonOperator {
	/**
	 * Receives two strings that are not fields and returns true if condition is
	 * satisfied
	 * 
	 * @param value1 string to compare
	 * @param value2 string to compare
	 * @return true if condition is satisfied
	 */
	public boolean satisfied(String value1, String value2);
}
