package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This command returns ShellStatus.TERMINATE when arguments String is empty
 * @author Antonio Filipović
 *
 */
public class ExitShellCommand implements ShellCommand {
	/**
	 * This is command name that needs tu be inputed in shell to call this command
	 */
	private String commandName="exit";
	/**
	 * This is description of exit command when help method is called
	 */
	private List<String> description = Arrays.asList(new String[] {
			"Command exit terminates shell. It cannot contain any other words"
	});
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isEmpty()) {
			env.writeln("Exit must be called without any arguments.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
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
