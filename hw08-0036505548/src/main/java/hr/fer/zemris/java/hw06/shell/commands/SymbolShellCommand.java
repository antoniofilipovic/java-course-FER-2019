package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents implementation of symbol command. This implementation
 * can change symbol character or get character for that symbol. It expects one
 * or two arguments. If called with one arugment it prints that character for
 * that symbol, with two it changes that symobl.
 * 
 * @author Antonio Filipović
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	private String commandName = "symbol";
	/**
	 * Description used for help method
	 */
	private List<String> description = Arrays.asList(new String[] {
			"Symbol command. It can receive one or two arguements.",
			"If called with one arugument, character for that symbol will be printed to console.",
			"If called with two arguemnts, and second argument length is 1, that symbol will be changed to that character.",
			"Possible symbols are:PROMPT,MORELINES,MULTILINE" });

	private static Map<String, String> outputs = new HashMap<>();

	static {
		outputs.put("PROMPT2", "Symbol for PROMPT is");
		outputs.put("MORELINES2", "Symbol for MORELINES is");
		outputs.put("MULTILINE2", "Symbol for MULTILINE is");
		outputs.put("PROMPT3", "Symbol for PROMPT has changed from");
		outputs.put("MORELINES3", "Symbol for MORELINES has changed from");
		outputs.put("MULTILINE3", "Symbol for MULTILINE has changed from");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		String output = outputs.get(parts.get(0) + String.valueOf((parts.size() + 1)));
		if (output == null) {
			env.writeln("Unknown symbol command.");
			return ShellStatus.CONTINUE;
		}
		output = output + " \'" + getSymbol(parts.get(0), env) + "\'";
		if (parts.size() == 1) {
			env.writeln(output);
		} else if (parts.size() == 2) {
			if (!changedSymbol(parts.get(0), parts.get(1), env)) {
				env.writeln("Invalid symbol for changing symbol.");
				return ShellStatus.CONTINUE;
			}
			env.writeln(output + " to \'" + getSymbol(parts.get(0), env) + "\'");
		} else {
			env.writeln("One or two arguments expected but " + parts.size() + " were given.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>(description);
		list = Collections.unmodifiableList(list);
		return list;
	}

	/**
	 * This method is used as getter for symbol
	 * 
	 * @param symbol command name of symbol
	 * @param env    envirnomnet
	 * @return String of that symbol
	 */
	private String getSymbol(String symbol, Environment env) {
		switch (symbol) {
		case "PROMPT":
			return String.valueOf(env.getPromptSymbol());
		case "MULTILINE":
			return String.valueOf(env.getMultilineSymbol());
		default:
			return String.valueOf(env.getMorelinesSymbol());
		}
	}

	/**
	 * Returns true if symbol was changed. Symbol is changed if its length is 1.
	 * 
	 * @param keyword symbol that should be changed
	 * @param symbol  it represents to what symbol should be changed
	 * @param env     environment
	 * @return true if symbol was changed
	 */
	private boolean changedSymbol(String keyword, String symbol, Environment env) {
		if (symbol.length() > 1) {
			return false;
		}
		switch (keyword) {
		case "PROMPT":
			env.setPromptSymbol(symbol.charAt(0));
			break;
		case "MULTILINE":
			env.setMultilineSymbol(symbol.charAt(0));
			break;
		default:
			env.setMorelinesSymbol(symbol.charAt(0));
			break;
		}
		return true;
	}

}
