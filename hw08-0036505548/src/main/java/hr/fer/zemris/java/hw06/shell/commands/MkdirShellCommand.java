package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class creates directory structure from one and only argument
 * 
 * @author Antonio Filipović
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	private String commandName = "mkdir";
	/**
	 * Description used in help method
	 */
	private List<String> description = Arrays.asList(new String[] {
			"The mkdir command takes a single argument: directory name, and creates the appropriate directory structure." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ShellArgumentsParser sp=new ShellArgumentsParser(arguments);
		List<String> parts;
		try {
			parts=sp.getArgumentsSplitted();
		}
		catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
	
		
		if (parts.size() != 1) {
			env.writeln("mkdir command expected exactly one argument.");
			return ShellStatus.CONTINUE;
		}

		String pathString=String.valueOf(env.getCurrentDirectory().resolve(parts.get(0)));
		try {
			new File(pathString).mkdirs();
		} catch (SecurityException e) {
			env.writeln("Problem occured when creating directory.");
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
