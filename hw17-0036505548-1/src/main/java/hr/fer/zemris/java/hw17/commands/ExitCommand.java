package hr.fer.zemris.java.hw17.commands;



import hr.fer.zemris.java.hw17.shell.Command;
import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
/**
 * This class represents exit command
 * @author af
 *
 */
public class ExitCommand implements Command{

	/**
	 * This is command name that needs tu be inputed in shell to call this command
	 */
	private String commandName="exit";
	
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

}
