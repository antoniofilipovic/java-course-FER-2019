package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command receives one or two arguments. The first argument is mandatory
 * and it represents path to file that should be opened and read from. The
 * second arugment is not mandatory and represents charset that will be used for
 * reading from file. This command reads file and writes it to console.
 * 
 * @author Antonio Filipović
 *
 */
public class CatShellCommand implements ShellCommand {
	/**
	 * Name of command that needs to be writen in console to start this command.
	 */
	private String commandName = "cat";
	/**
	 * This represents description that will be written to console if help command
	 * is called.
	 */
	private List<String> description = Arrays.asList(new String[] { "Command cat takes one or two arguments.",
			"The first argument is mandatory and it represents path to file that should be opened and read from. ",
			"The second arugment is not mandatory and represents charset that will be used for reading from file.",
			"This command reads file and writes it to console." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		if (!(parts.size() == 1 || parts.size() == 2)) {
			env.writeln("cat command expected one or two arguments but " + parts.size() + " received.");
			return ShellStatus.CONTINUE;
		}
		Path path;
		try {
			path = Paths.get(parts.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalide path.");
			return ShellStatus.CONTINUE;
		}
		Charset charset = Charset.defaultCharset();
		if (parts.size() == 2) {
			try {
				charset = Charset.forName(parts.get(1));
			} catch (IllegalArgumentException e) {
				env.writeln("Unsupported charset.");
				return ShellStatus.CONTINUE;
			}

		}

		if (!Files.exists(path) || Files.isDirectory(path)) {
			env.writeln("Wrong argument. Second argument must be file that exists.");
			return ShellStatus.CONTINUE;
		}

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(path)), charset))) {
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				env.writeln(line);
			}

		} catch (IOException e) {
			env.writeln("Error occured while trying to read from file.");
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
