package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class parses arguments from command line and returns list of string
 * arguments.
 * 
 * @author Antonio Filipović
 *
 */
public class ShellArgumentsParser {
	/**
	 * Current index for parsing
	 */
	private int currentIndex;
	/**
	 * Character symbols of data.
	 */
	private char[] data;
	/**
	 * List of string of arguments
	 */
	private List<String> arguments = new ArrayList<>();

	/**
	 * This is public constructor for parser
	 * 
	 * @param line
	 */
	public ShellArgumentsParser(String line) {
		data = line.toCharArray();
	}

	/**
	 * This method returns list of splited arguments by one or more whitespace
	 * characters. Those whitespace characters are not taken into account in
	 * strings.
	 * 
	 * @return list of arguments
	 */
	public List<String> getArgumentsSplitted() {
		while (true) {
			String s = nextArgument();
			if (s == null) {
				break;
			}
			arguments.add(s);
		}
		return arguments;

	}

	/**
	 * Private method for returning next argument
	 * 
	 * @return
	 */
	private String nextArgument() {
		if (currentIndex >= data.length) {
			return null;
		}
		return getNext();
	}

	/**
	 * Gets next argument
	 * 
	 * @return
	 */
	private String getNext() {
		skipBlanks();
		if (data[currentIndex] == '\"') {
			currentIndex++;
			return parseString();
		}
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			if (Character.isWhitespace(data[currentIndex])) {
				break;
			}
			sb.append(data[currentIndex++]);
		}
		return sb.toString();
	}

	/**
	 * This method returns next arugment from string. In string this escape symbols
	 * are ok: \\->\ , \"->"", every other escape sequence is returned as it is.
	 * 
	 * @return
	 */
	private String parseString() {
		StringBuilder sb = new StringBuilder();
		boolean endString = false;
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\"') {
				currentIndex++;
				endString = true;
				break;
			} else if (data[currentIndex] == '\\') {
				if (currentIndex + 1 >= data.length) {
					throw new IllegalArgumentException("Wrong escaping sequence.");
				}
				if (data[currentIndex + 1] == '\\') {
					sb.append(data[currentIndex + 1]);
				} else if (data[currentIndex + 1] == '\"') {
					sb.append(data[currentIndex + 1]);
				} else {
					sb.append(data[currentIndex]).append(data[currentIndex + 1]);
				}
				currentIndex += 2;
				continue;
			}
			sb.append(data[currentIndex++]);
		}
		if (!endString) {
			throw new IllegalArgumentException("End of string was expected.");
		}
		if (!(currentIndex == data.length || Character.isWhitespace(data[currentIndex]))) {
			throw new IllegalArgumentException(
					"After double quotes at least one whitespace is needed or end of string.");
		}
		return sb.toString();
	}

	/**
	 * This method skips all blanks
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			} else {
				break;
			}
		}
	}

}
