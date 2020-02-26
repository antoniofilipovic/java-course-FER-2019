package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command prints to console everything from stack from the last element
 * added to the first one.
 * 
 * @author af
 *
 */
public class ListdShellCommand implements ShellCommand {
	/**
	 * Name of command that needs to be writen in console to start this command.
	 */
	private String commandName = "listd";
	/**
	 * This represents description that will be written to console if help command
	 * is called.
	 */
	private List<String> description = Arrays.asList(new String[] { "Command listd takes  zero arguments.",
			"It prints to console everything from stack from the last element added to the first one." });

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
			env.writeln("listd command expected zero arguments but " + parts.size() + " received.");
			return ShellStatus.CONTINUE;
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack=(Stack<Path>)env.getSharedData("cdstack");
		if(stack==null) {
			env.writeln("Stack wasn't created yet.");
			return ShellStatus.CONTINUE;
		}
		int size=stack.size();
		for(int i=0;i<size;i++) {
			env.writeln(String.valueOf(stack.get(size-i-1)));
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
