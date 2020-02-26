package hr.fer.zemris.java.hw05.db;

/**
 * This class models complete conditional expression
 * 
 * @author Antonio Filipović
 *
 */
public class ConditionalExpression {
	/**
	 * Represents reference to IFieldValueGetter
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * Reference to string to compare to
	 */
	private String stringToCompare;
	/**
	 * Reference to comparison operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Public constructor for conditional expression
	 * 
	 * @param fieldValueGetter   getter of field value from student record
	 * @param stringToCompare    string to compare with
	 * @param comparisonOperator operator to use for comparison
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringToCompare,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldValueGetter = fieldValueGetter;
		this.stringToCompare = stringToCompare;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for field value getter
	 * 
	 * @return IFieldValueGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldValueGetter;
	}

	/**
	 * Getter for string to compare
	 * 
	 * @return string to compare
	 */
	public String getStringLiteral() {
		return stringToCompare;
	}

	/**
	 * Comparison operator
	 * 
	 * @return IComparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
