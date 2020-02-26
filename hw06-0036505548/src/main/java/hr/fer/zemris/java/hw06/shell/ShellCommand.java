package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * This interface is used for executing commands into shell.
 * 
 * @author Antonio Filipović
 *
 */

public interface ShellCommand {
	/**
	 * This method executes command into enviroment. It receives always string which
	 * represents everything that user entered after the command name. It is
	 * expected that in case of multiline input, the shell has already concatenated
	 * all lines into a single line and removed MORELINES symbol from line endings
	 * (before concatenation)
	 * 
	 * @param env       enviroment
	 * @param arguments single line with arguments
	 * @return ShellStatus
	 */

	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * This method returns name of command
	 * 
	 * @return name of command.
	 */

	String getCommandName();

	/**
	 * This method returns a read-only list because description can span over more
	 * than one line
	 * 
	 * @return
	 */

	List<String> getCommandDescription();

}
