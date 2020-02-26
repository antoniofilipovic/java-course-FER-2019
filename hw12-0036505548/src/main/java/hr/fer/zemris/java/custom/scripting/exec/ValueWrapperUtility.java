package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This is helper class for some tasks
 * 
 * @author af
 *
 */
public class ValueWrapperUtility {
	/**
	 * This method returns type of value in object. It is integer type if it is null
	 * 
	 * @param value
	 * @return
	 */
	public static ValueWrapperType getType(Object value) {
		if (value instanceof Integer || value == null) {
			return ValueWrapperType.INTEGER;
		} else if (value instanceof Double) {
			return ValueWrapperType.DOUBLE;
		} else if (value instanceof String) {
			return ValueWrapperType.STRING;
		} else {
			return ValueWrapperType.OTHER;
		}
	}

	/**
	 * This method returns parsed value of object. It parses string and returns zero
	 * for null values
	 * 
	 * @param value        that will be parsed if it is string
	 * @param incValueType type of value
	 * @return parsed string or null as object
	 */
	public static Object parseValue(Object value, ValueWrapperType incValueType) {
		if (incValueType == ValueWrapperType.INTEGER && value == null) {
			return 0;
		}
		if (incValueType == ValueWrapperType.INTEGER || incValueType == ValueWrapperType.DOUBLE) {
			return value;
		}
		if (incValueType == ValueWrapperType.STRING) {
			Object temp = null;
			boolean parsed = false;
			if (String.valueOf(value).contains(".") || String.valueOf(value).toLowerCase().contains("e")) {
				try {
					temp = Double.parseDouble(String.valueOf(value));
					parsed = true;
				} catch (NumberFormatException e) {
					//
				}
			}
			if (parsed)
				return temp;
			try {
				temp = Integer.parseInt(String.valueOf(value));
				parsed = true;
			} catch (NumberFormatException e) {
				//
			}

			if (parsed)
				return temp;

		}
		throw new RuntimeException("Can't perform operation.");

	}

	/**
	 * This method returns double value of object
	 * 
	 * @param currentValue object
	 * @return value
	 */
	public static double getDoubleValue(Object currentValue) {
		if (currentValue instanceof Integer) {
			int intValue = (Integer) currentValue;
			return (double) intValue;
		}
		return (Double) currentValue;

	}

}
