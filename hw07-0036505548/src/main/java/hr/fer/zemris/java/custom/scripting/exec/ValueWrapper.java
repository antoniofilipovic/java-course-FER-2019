package hr.fer.zemris.java.custom.scripting.exec;
/**
 * This class represents value wrapper. It can contain string, integer, double or null
 * @author af
 *
 */
public class ValueWrapper {
	/**
	 * Value of valueWrapper
	 */
	private Object value;
	/**
	 * Type of value
	 */
	private ValueWrapperType currentValueType;
	/**
	 * Reference to int operation
	 */
	private IntOperation intOperation;
	/**
	 * Reference to double operation
	 */
	private DoubleOperation doubleOperation;
	/**
	 * Public constructor for value wrapper
	 * @param value that value wrapper holds
	 */
	public ValueWrapper(Object value) {
		this.value = value;
		intOperation = new IntOperation();
		doubleOperation = new DoubleOperation();
		currentValueType = ValueWrapperUtility.getType(this.value);
		if (currentValueType == ValueWrapperType.OTHER)
			throw new RuntimeException("Value can be string,double,int or null.");

	}
	/**
	 * This method adds two values. It follows rules explained before
	 * @param incValue
	 */
	public void add(Object incValue) {
		this.value = performOperation(incValue, OperationType.ADD);

	}
	/**
	 * This method subracts two values
	 * @param decValue
	 */
	public void subtract(Object decValue) {
		this.value = performOperation(decValue, OperationType.SUB);
	}
	/**
	 * This method multiplies two values
	 * @param mulValue
	 */
	public void multiply(Object mulValue) {
		this.value = performOperation(mulValue, OperationType.MUL);
	}
	/**
	 * This method divides two values
	 * @param divValue
	 */
	public void divide(Object divValue) {
		this.value = performOperation(divValue, OperationType.DIV);
	}
	/**
	 * This method compares two values
	 * @param withValue
	 * @return
	 */
	public int numCompare(Object withValue) {
		return (int) performOperation(withValue, OperationType.COMPARE);
	}
	/**
	 * This method performs operation by calling appropriate reference to operation type
	 * @param secondOpertor second operator
	 * @param operationType
	 * @return
	 */
	public <T> Object performOperation(Object secondOpertor, OperationType operationType) {
		ValueWrapperType incValueType = ValueWrapperUtility.getType(secondOpertor);
		if (incValueType == ValueWrapperType.OTHER)
			throw new RuntimeException("Value can be string,double,int or null.");
		
		Object parsedIncValue = ValueWrapperUtility.parseValue(secondOpertor, incValueType);
		incValueType = ValueWrapperUtility.getType(parsedIncValue);
		
		Object currentValue=ValueWrapperUtility.parseValue(value,currentValueType);
		currentValueType=ValueWrapperUtility.getType(currentValue);
		
		boolean bothInt = incValueType == ValueWrapperType.INTEGER && currentValueType == ValueWrapperType.INTEGER;
		if (bothInt) {
			switch (operationType) {
			case ADD: return intOperation.performOperationAdd((Integer) currentValue, (Integer) parsedIncValue);
			case MUL: return intOperation.performOperationMul((Integer) currentValue, (Integer) parsedIncValue);
			case SUB:return intOperation.performOperationSub((Integer) currentValue, (Integer) parsedIncValue);
			case DIV: return intOperation.performOperationDiv((Integer) currentValue, (Integer) parsedIncValue);
			default: return intOperation.numCompare((Integer) currentValue, (Integer) parsedIncValue);
			}
		} else {
			double val1 = ValueWrapperUtility.getDoubleValue(currentValue);
			double val2=ValueWrapperUtility.getDoubleValue(parsedIncValue);
			switch (operationType) {
			case ADD:
				return doubleOperation.performOperationAdd(val1, val2);
			case MUL:
				return doubleOperation.performOperationMul(val1, val2);
			case SUB:
				return doubleOperation.performOperationSub(val1, val2);
			case DIV:return doubleOperation.performOperationDiv(val1, val2);
			default: return doubleOperation.numCompare(val1, val2);
			}
		}

	}
	/**
	 * Getter for value 
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * Setter for value
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
