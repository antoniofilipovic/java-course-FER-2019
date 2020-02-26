package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents implementation of help command. If started with no
 * arguments, it lists names of all supported commands but if started with
 * single argument, it print name and the description of selected command.If
 * command doesnt exist user will be informed.
 * 
 * @author Antonio Filipović
 *
 */
public class HelpShellCommand implements ShellCommand {
	/**
	 * Name of command
	 */
	private String commandName = "help";
	/**
	 * Description of every command
	 */
	private List<String> description = Arrays.asList(
			new String[] { "Help command", "If started with no arguments, it lists names of all supported commands.",
					"If started with single argument, it print name and the description of selected command",
					"If command doesnt exist user will be informed." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		if(parts.size()==0) {
			env.commands().forEach((k,v)->env.writeln(k));
		}else if(parts.size()==1) {
			ShellCommand c=env.commands().get(parts.get(0));
			if(c==null) {
				env.writeln("This command doesnt exist.");
				return ShellStatus.CONTINUE;
			}
			env.writeln(c.getCommandName());
			c.getCommandDescription().forEach(v->env.writeln(v));
		}else {
			env.writeln("Illegal number of arguments for help command.");
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
