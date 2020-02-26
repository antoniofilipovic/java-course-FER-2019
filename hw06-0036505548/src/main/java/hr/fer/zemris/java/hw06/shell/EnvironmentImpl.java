package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

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
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();
	/**
	 * static initialization block for commands
	 */
	static {
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexDumpShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
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
	public SortedMap<String, ShellCommand> commands() {
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

}
