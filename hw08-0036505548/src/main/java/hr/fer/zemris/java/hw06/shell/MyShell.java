package hr.fer.zemris.java.hw06.shell;

/**
 * This class represents implementation of shell simmilar to shell in cmd. It
 * has its own symbol for prompt, symbol for multiple lines and more lines.
 * Commands that are:symbol,ls,cat,charsets,copy,exit, mkdir,tree ,help
 * 
 * @author Antonio Filipović
 *
 */
public class MyShell {
	/**
	 * Message writen to user when shell is started.
	 */
	private static final String greeting_Message = "Welcome to MyShell v 1.0";

	/**
	 * This method starts when main program stars
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(greeting_Message);
		Environment environment = new EnvironmentImpl();
		ShellStatus status = ShellStatus.CONTINUE;
		while (status == ShellStatus.CONTINUE) {
			try {
				environment.write(String.valueOf(environment.getPromptSymbol()) + " ");
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
			System.out.println(line);
			String commandName = ShellUtilityParser.getCommand(line);
			String arguments = ShellUtilityParser.getArguments(line);
			ShellCommand command = environment.commands().get(commandName);
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
