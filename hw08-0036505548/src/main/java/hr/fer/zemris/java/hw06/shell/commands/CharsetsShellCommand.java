package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command lists all available charsets in java. It writes every charset in
 * new line only if arguments are empty.
 * 
 * @author Antonio Filipović
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	/**
	 * This variable represents name of command that needs tu be inputed in shell
	 */
	private String commandName = "charsets";
	/**
	 * This is map of all available charsets, first value is charset name but as string,
	 * second is real charset
	 */
	private SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
	/**
	 * This is text that will be writen as description for help method
	 */
	private List<String> description = Arrays.asList(new String[] {
			"Command charsets takes no arguments and lists names of supported charsets for your Java platform.",
			"A single charset name is written per line" });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.write("Charsets must be called without any arguments.");
			return ShellStatus.CONTINUE;
		}

		availableCharsets.forEach((k, v) -> env.writeln(k));
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

}
