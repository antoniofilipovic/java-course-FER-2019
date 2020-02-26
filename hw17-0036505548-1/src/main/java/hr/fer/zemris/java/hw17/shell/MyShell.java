package hr.fer.zemris.java.hw17.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents implementation of shell simmilar to shell in cmd. It
 * has its own symbol for prompt, symbol for multiple lines and more lines.
 * Commands that are:query, exit, results, type
 * 
 * @author Antonio Filipović
 *
 */
public class MyShell {
	/**
	 * Message writen to user when shell is started.
	 */
	private static final String greeting_Message = "Welcome to MyShell v 2.0";
	private static final String command_Message = "Enter command";

	/**
	 * This method starts when main program stars
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(greeting_Message);
		Environment environment = new EnvironmentImpl();
		ShellStatus status = ShellStatus.CONTINUE;

		if (args.length != 1) {
			System.out.println("Wrong number of arguments, 1 expected.");
			System.exit(-1);
		}

		Path directoryPath = Paths.get(args[0]);
		if (!Files.isDirectory(directoryPath)) {
			System.out.println("Given argument must be folder path.");
			System.exit(-1);
		}

		environment.initializeVocabulary(directoryPath);
		System.out.println("Veličina rječnika je " + environment.getNumberOfWordsVocabulary());

		environment.initializeDocumentsVectors(directoryPath);

		while (status == ShellStatus.CONTINUE) {
			try {
				environment.write(command_Message + String.valueOf(environment.getPromptSymbol()) + " ");
			} catch (Exception e) {
				status = ShellStatus.TERMINATE;
				continue;
			}
			StringBuilder sb = new StringBuilder();
			try {
				boolean moreLines = true;
				while (moreLines) {
					moreLines = false;
					String line = environment.readLine();
					if (line.isEmpty())
						break;
					if (line.lastIndexOf(environment.getMorelinesSymbol()) == line.length() - 1) {
						moreLines = true;
						environment.write(environment.getMultilineSymbol() + " ");
						sb.append(line.substring(0, line.length() - 1));
					} else {
						sb.append(line);
					}

				}

			} catch (ShellIOException e) {
				environment.writeln(e.getMessage());
			}
			String line = sb.toString();
			String commandName = ShellUtilityParser.getCommand(line);
			String arguments = ShellUtilityParser.getArguments(line);
			Command command = environment.commands().get(commandName);
			if (command != null) {
				try {
					status = command.executeCommand(environment, arguments);
				} catch (ShellIOException e) {
					status = ShellStatus.TERMINATE;
				}
			} else {
				System.out.println("Wrong command name.");
			}
		}

	}

}