package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * This interface is used for communication between MyShell and ShellCommands
 * 
 * @author Antonio Filipović
 *
 */
public interface Environment {
	/**
	 * This method reads all possible lines, concatenates string without morelines
	 * symbol and returns that string. Throws exception if morelines symbol is not
	 * last symbol in every line. After morelines symbol, empty line can follow
	 * 
	 * @return concatenated string
	 * @throws ShellIOException if input stream was not good
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes text to console without \n
	 * 
	 * @param text that will be written
	 * @throws ShellIOException if line is null
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes text to console with \n
	 * 
	 * @param text that will be written
	 * @throws ShellIOException if line cannot be written
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns sorted map of all commands
	 * 
	 * @return sorted map
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns multiline symbol. That symbol shell writes for multiline commands
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * sets new multiline symbol
	 * 
	 * @param symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol.With that symbol every new command starts
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol
	 * 
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns more lines symbol. That symbol is expected for multiline imputs
	 * 
	 * @return more lines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets more lines symbol
	 * 
	 * @param symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
