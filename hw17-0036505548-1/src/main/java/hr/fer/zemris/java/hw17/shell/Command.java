package hr.fer.zemris.java.hw17.shell;



/**
 * This class represnets command
 * @author af
 *
 */
public interface Command {

	/**
	 * This method executes command
	 * @param env environment
	 * @param arguments arguments
	 * @return
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * This method returns name of command
	 * 
	 * @return name of command.
	 */

	String getCommandName();


	

}
