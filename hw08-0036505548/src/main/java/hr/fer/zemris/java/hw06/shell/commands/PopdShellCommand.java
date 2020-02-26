package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents implementation of popd command. It pops from stack last
 * entry if one exists
 * 
 * @author af
 *
 */
public class PopdShellCommand implements ShellCommand {
	/**
	 * Name of command that needs to be writen in console to start this command.
	 */
	private String commandName = "cd";
	/**
	 * This represents description that will be written to console if help command
	 * is called.
	 */
	private List<String> description = Arrays.asList(new String[] { "Command pod takes zero arguments.",
			"It changes current directory to  path from stack if one exists.", "It removes last entry from stack.",
			"If stack is empty it throws exception." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		if (!(parts.size() == 0)) {
			env.writeln("popd command expected argument but " + parts.size() + " received.");
			return ShellStatus.CONTINUE;
		}

		try {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
			if (stack == null) {
				env.writeln("Stack isn't yet created.");
				return ShellStatus.CONTINUE;
			}
			Path stackPath = stack.pop();
			if (Files.exists(stackPath))
				env.setCurrentDirectory(stackPath);
		} catch (EmptyStackException e) {
			env.writeln("There wasn't anything to pop from stack.");
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
