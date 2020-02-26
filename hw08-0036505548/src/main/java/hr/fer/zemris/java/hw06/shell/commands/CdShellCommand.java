package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command changes current directory to given directory. It expects exactly
 * one argument.
 * 
 * @author Antonio Filipović
 *
 */
public class CdShellCommand implements ShellCommand {
	/**
	 * Name of command that needs to be writen in console to start this command.
	 */
	private String commandName = "cd";
	/**
	 * This represents description that will be written to console if help command
	 * is called.
	 */
	private List<String> description = Arrays.asList(new String[] { "Command cd takes one one argument.",
			"It changes current directory to given path if one exists." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		if (!(parts.size() == 1)) {
			env.writeln("cd command expected one argument but " + parts.size() + " received.");
			return ShellStatus.CONTINUE;
		}
		Path path;
		try {
			path = env.getCurrentDirectory().resolve(parts.get(0)).normalize();
		} catch (InvalidPathException e) {
			env.writeln("Invalide path given.");
			return ShellStatus.CONTINUE;
		}
		try {
			env.setCurrentDirectory(path);
		} catch (ShellIOException e) {
			env.writeln("Path isn't directory.");
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

}
