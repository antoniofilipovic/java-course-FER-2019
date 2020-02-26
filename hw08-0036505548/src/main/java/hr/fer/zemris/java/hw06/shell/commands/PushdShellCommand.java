package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command pushd pushes current directory to stack if given path exists, creates
 * stack if it wasn't already created.
 * 
 * @author af
 *
 */
public class PushdShellCommand implements ShellCommand {
	/**
	 * Name of command that needs to be writen in console to start this command.
	 */
	private String commandName = "pushd";
	/**
	 * This represents description that will be written to console if help command
	 * is called.
	 */
	private List<String> description = Arrays.asList(new String[] { "Command pushd takes  one argument.",
			"It changes current directory to given path if one exists.", "It pushes current directory to stack." });

	@SuppressWarnings("unchecked")
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
			env.writeln("pushd command expected argument but " + parts.size() + " received.");
			return ShellStatus.CONTINUE;
		}
		Path path;
		try {
			path = env.getCurrentDirectory().resolve(parts.get(0)).normalize();
		} catch (InvalidPathException e) {
			env.writeln("Invalide path.");
			return ShellStatus.CONTINUE;
		}
		try {
			Path oldPath = env.getCurrentDirectory();
			env.setCurrentDirectory(path);
			Object o = env.getSharedData("cdstack");
			Stack<Path> stack;
			if (o != null) {
				stack = (Stack<Path>) (o);
			} else {
				stack = new Stack<>();
				env.setSharedData("cdstack", stack);
			}
			stack.push(oldPath);

		} catch (ShellIOException e) {
			env.writeln("Pushd failed." + e.getMessage());
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
