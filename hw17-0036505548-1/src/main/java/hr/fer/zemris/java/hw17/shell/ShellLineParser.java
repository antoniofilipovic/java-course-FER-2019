package hr.fer.zemris.java.hw17.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class parses arguments from command line and returns list of string
 * arguments.
 * 
 * @author Antonio Filipović
 *
 */
public class ShellLineParser {

	/**
	 * Gets words
	 * 
	 * @return
	 */
	public static List<String> parseLine(String line) {
		List<String> words = new ArrayList<>();

		char[] data = line.toCharArray();
		int currentIndex = 0;
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			while (currentIndex < data.length && !Character.isAlphabetic(data[currentIndex])) {
				currentIndex++;
			}
			sb.setLength(0);
			while (currentIndex < data.length && Character.isAlphabetic(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			String word = sb.toString().trim();
			if (!word.isEmpty()) {
				words.add(word.toLowerCase());
			}

		}
		return words;
	}

}