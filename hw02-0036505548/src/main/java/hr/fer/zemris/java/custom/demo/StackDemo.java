package hr.fer.zemris.java.custom.demo;

import java.util.Arrays;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class calculates expression value in postfix mode.
 * 
 * @author antonio
 */

public class StackDemo {
	private static final String[] regularValues = new String[] { "+", "-", "*", "/", "%" };

	public static void main(String[] args) {
		boolean error = false;
		if (args.length > 1 || args.length == 0) {
			System.out.println("Wrong expression");
			return;
		}
		String[] parts = args[0].split("\\s{1,}");
		ObjectStack stack = new ObjectStack();
		for (String s : parts) {
			try {
				int number = Integer.parseInt(s);
				stack.push(number);
				continue;
			} catch (NumberFormatException e) {
				if (!checkStringValue(s)) {
					error = true;
					break;
				}
			}
			if (!performOperation(stack, s)) {
				error = true;
				break;
			}

		}
		if (stack.size() != 1 || error == true) {
			System.out.println("Wrong expression!");
		} else {
			System.out.printf("Expression evaluates to %d", stack.pop());
		}
	}

	/**
	 * Checks if string is one of operators
	 * 
	 * @param s string that checks
	 * @return true if it is,else false
	 */

	private static boolean checkStringValue(String s) {
		return Arrays.asList(regularValues).contains(s);
	}

	/**
	 * This method performs valid operation
	 * 
	 * @param stack reference to stack
	 * @param s     string that represents operation
	 * @return true if operation has been successful, false otherwise
	 */

	private static boolean performOperation(ObjectStack stack, String s) {
		int number1 = 0, number2 = 0, value = 0;
		try {
			number1 = (int) stack.pop();
			number2 = (int) stack.pop();
		} catch (EmptyStackException e) {
			return false;
		}
		if (number1 == 0 && (s.equals("/") || s.equals("%"))) {
			return false;
		}
		if (s.equals("+")) {
			value = number2 + number1;
		} else if (s.equals("-")) {
			value = number2 - number1;
		} else if (s.equals("*")) {
			value = number2 * number1;
		} else if (s.equals("/")) {
			value = number2 / number1;
		} else {
			value = number2 % number1;
		}
		stack.push(value);
		return true;

	}

}
