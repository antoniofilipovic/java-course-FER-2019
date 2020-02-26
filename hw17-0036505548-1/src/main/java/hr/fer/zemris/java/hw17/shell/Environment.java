package hr.fer.zemris.java.hw17.shell;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.zemris.java.hw17.model.Document;
import hr.fer.zemris.java.hw17.model.Vector;

/**
 * This interface is used for communication between MyShell and ShellCommands
 * 
 * @author Antonio Filipović
 *
 */
public interface Environment {

	void initializeVocabulary(Path directory) throws ShellIOException;

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
	SortedMap<String, Command> commands();

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

	/**
	 * Getter for number of words in vocabualrs
	 * 
	 * @return
	 */
	int getNumberOfWordsVocabulary();

	/**
	 * Initializer of document vector
	 * 
	 * @param directory dir
	 * @throws ShellIOException exception
	 */
	void initializeDocumentsVectors(Path directory) throws ShellIOException;

	/**
	 * Creator of vector from vocabulary
	 * 
	 * @return vector
	 */
	Vector createVectorFromVocabulary();

	/**
	 * Getter for vocabulary
	 * 
	 * @return
	 */
	Map<String, Integer> getVocabulary();

	/**
	 * Getter for number of documents
	 * 
	 * @return
	 */
	int getNumberOfDocuments();

	/**
	 * Getter for id vector
	 * 
	 * @return
	 */
	Vector getIdfVector();

	/**
	 * Getter of documents
	 * 
	 * @return list of documents
	 */

	List<Document> getDocuments();

	/**
	 * Setter for resultrs
	 * 
	 * @param results
	 */
	void setResults(Map<Double, Document> results);

	/**
	 * Getter for results
	 * 
	 * @return
	 */
	Map<Double, Document> getResults();

}