package hr.fer.zemris.java.hw17.shell;

/**
 * This is helper class used for working with strings. It contains all static
 * methods
 * 
 * @author Antonio Filipović
 *
 */
public class ShellUtilityParser {
	/**
	 * This method returns name of command. Comand name is first word in line
	 * 
	 * @param line line from which it returns command name
	 * @return command name
	 */
	public static String getCommand(String line) {
		String[] parts = line.trim().split("\\s");
		return parts[0];
	}

	/**
	 * This method returns everything after command name. It uses get command
	 * 
	 * @param line from which it returns arguments
	 * @return
	 */
	public static String getArguments(String line) {
		String command = getCommand(line);
		return  line.substring(command.length()).trim();
	}

}
