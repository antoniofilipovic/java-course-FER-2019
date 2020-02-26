package hr.fer.zemris.java.hw06.shell.massrename;

import java.nio.file.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.ShellIOException;

/**
 * This class represents filter result. It takes path and matcher and for those
 * two given can return number of groups and group depending on pattern
 * 
 * @author af
 *
 */
public class FilterResult {
	/**
	 * Path for filtering
	 */
	private Path fileName;
	/**
	 * Mathcer
	 */
	private Matcher m;
	/**
	 * Minimum index
	 */
	private static final int MIN_INDEX = 0;

	/**
	 * Public constructor for filter result
	 * 
	 * @param p       path
	 * @param pattern pattern
	 * @param m       matcher
	 */
	public FilterResult(Path p, Pattern pattern, Matcher m) {
		this.fileName = p;
		this.m = m;
	}

	@Override
	public String toString() {
		return String.valueOf(fileName.getFileName());
	}

	/**
	 * Number of groups for filtering
	 * 
	 * @return number of groups
	 */
	public int numberOfGroups() {
		return m.groupCount();
	}

	/**
	 * Group from matcher of mentioned index 
	 * 
	 * @param index
	 * @return
	 * @throws IllegalStateException | ShellIOException
	 */
	public String group(int index) {
		if (index < MIN_INDEX || index > numberOfGroups()) {
			throw new ShellIOException("Illegal index of group.");
		}
		m.matches();
		return m.group(index);

	}

}
