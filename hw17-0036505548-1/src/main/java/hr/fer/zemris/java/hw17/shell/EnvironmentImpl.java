package hr.fer.zemris.java.hw17.shell;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw17.commands.ExitCommand;
import hr.fer.zemris.java.hw17.commands.QueryCommand;
import hr.fer.zemris.java.hw17.commands.ResultsCommand;
import hr.fer.zemris.java.hw17.commands.TypeCommand;
import hr.fer.zemris.java.hw17.model.Document;
import hr.fer.zemris.java.hw17.model.Vector;

/**
 * This class represents implementation of environment interface. It represents
 * connection between myshell and commands. It is used for writing and reading
 * lines and for getting and setting symbols
 * 
 * @author Antonio Filipović
 *
 */
public class EnvironmentImpl implements Environment {

	/**
	 * Symbol for new command
	 */
	private Character promptSymbol = '>';
	/**
	 * Symbol for morelines to be entered
	 */
	private Character moreLinesSymbol = '\\';
	/**
	 * Symbol for commands that are stretched over more lines.
	 */
	private Character multiLineSymbol = '|';
	/**
	 * Scanner for reading from console
	 */
	private Scanner sc = new Scanner(System.in);
	/**
	 * Sorted map of commands
	 */
	private static SortedMap<String, Command> commands = new TreeMap<>();

	/**
	 * This map represents vocabulary
	 */

	private Map<String, Integer> vocabulary = new LinkedHashMap<>();
	/**
	 * Map of stopwords
	 */

	private Set<String> stopWords = new HashSet<>();
	/**
	 * List of documents
	 */
	private List<Document> documents = new ArrayList<>();
	/**
	 * idf vector
	 */

	private Vector idf;
	/**
	 * Results
	 */

	private Map<Double, Document> results;

	/**
	 * static initialization block for commands
	 */
	static {
		commands.put("query", new QueryCommand());
		commands.put("exit", new ExitCommand());
		commands.put("results", new ResultsCommand());
		commands.put("type", new TypeCommand());

	}

	@Override
	public String readLine() throws ShellIOException {
		return sc.nextLine().trim();
	}

	@Override
	public void write(String text) throws ShellIOException {
		if (text == null) {
			throw new ShellIOException("Write cannot receive null.");
		}
		System.out.printf(text);

	}

	@Override
	public void writeln(String text) throws ShellIOException {
		if (text == null) {
			throw new ShellIOException("Writeln method cannot receive null.");
		}
		System.out.println(text);

	}

	@Override
	public SortedMap<String, Command> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		if (symbol == null) {
			throw new NullPointerException("Multiline symbol cannot be null.");
		}
		multiLineSymbol = symbol;

	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		if (symbol == null) {
			throw new NullPointerException("Prompt symbol cannot be null.");
		}
		promptSymbol = symbol;

	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		if (symbol == null) {
			throw new NullPointerException("More lines symbol cannot be null.");
		}
		moreLinesSymbol = symbol;

	}

	@Override
	public void initializeVocabulary(Path directory) throws ShellIOException {
		File fileDir = new File(String.valueOf(directory));
		File[] files = fileDir.listFiles();
		createStopWords();

		for (File file : files) {
			if (file.isFile() && file.canRead()) {
				List<String> lines = null;
				try {
					lines = Files.readAllLines(file.toPath());
				} catch (IOException e) {
					System.out.println("Cant read file:" + file.getName());
					continue;
				}
				for (String line : lines) {
					List<String> words = ShellLineParser.parseLine(line);
					for (String word : words) {
						if (stopWords.contains(word)) {
							continue;
						}
						vocabulary.merge(word, 1, Integer::sum);

					}

				}
			}
		}

	}

	@Override
	public int getNumberOfWordsVocabulary() {
		return vocabulary.size();
	}

	@Override
	public void initializeDocumentsVectors(Path directory) throws ShellIOException {
		File fileDir = new File(String.valueOf(directory));
		File[] files = fileDir.listFiles();

		idf = createVectorFromVocabulary();
		Map<String, Integer> idfMap = idf.getValues();
		for (File file : files) {
			if (file.isFile() && file.canRead()) {

				Vector tf = createVectorFromVocabulary();

				Map<String, Integer> tfMap = tf.getValues();

				List<String> lines = null;
				try {
					lines = Files.readAllLines(file.toPath());
				} catch (IOException e) {
					System.out.println("Cant read file:" + file.getName());
					continue;
				}

				for (String line : lines) {
					List<String> words = ShellLineParser.parseLine(line);
					for (String word : words) {
						if (!vocabulary.containsKey(word)) {
							continue;
						}
						tfMap.merge(word, 1, Integer::sum);
					}

				}

				tfMap.forEach((k, v) -> {
					if (v != 0) {
						idfMap.merge(k, 1, Integer::sum);
					}
				});

				documents.add(new Document(file.toPath(), tf));
			}
		}

		documents.forEach(d -> d.createTfidf(idf, documents.size()));
		

	}

	/**
	 * This method creates stop words from file
	 */

	private void createStopWords() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("hrvatski_stoprijeci.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new ShellIOException(e.getMessage());
		}
		for (String s : lines) {
			stopWords.add(s.trim());
		}

	}

	@Override
	public Vector createVectorFromVocabulary() {
		Map<String, Integer> values = new HashMap<String, Integer>();
		vocabulary.forEach((k, v) -> values.put(k, 0));

		Vector vector = new Vector(values);
		return vector;
	}

	public Map<String, Integer> getVocabulary() {
		return vocabulary;
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public Vector getIdfVector() {
		if (idf == null) {
			System.out.println("null je");
		}
		if (idf.getValues() == null) {
			System.out.println("ovo smece je null");
		}
		return idf;

	}

	@Override
	public List<Document> getDocuments() {
		return documents;
	}

	@Override
	public void setResults(Map<Double, Document> results) {
		this.results = results;

	}

	@Override
	public Map<Double, Document> getResults() {
		return this.results;
	}

}
