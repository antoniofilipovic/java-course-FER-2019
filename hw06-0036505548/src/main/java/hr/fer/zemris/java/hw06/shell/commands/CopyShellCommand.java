package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This command copies from one file to another. If other file is directory it
 * copies to that directory. If that file already exists it asks user for
 * permission to copy.
 * 
 * @author Antonio Filipović
 *
 */
public class CopyShellCommand implements ShellCommand {
	/**
	 * Command name
	 */
	private String commandName = "copy";
	/**
	 * Description used for help method
	 */
	private List<String> description = Arrays.asList(new String[] {
			"The copy command expects two arguments: source file name and destination file name (i.e. paths and names).",
			"If destination file exists, user is asked toallowed to overwrite it.", "It works only with files.",
			"If the second argument is directory,it copies the original file into that directory using the original file name." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (parts.size() != 2) {
			env.writeln("Two arguments expected but " + parts.size() + " given.");
			return ShellStatus.CONTINUE;
		}
		Path firstPath, secondPath;
		try {
			firstPath = Paths.get(parts.get(0));
			secondPath = Paths.get(parts.get(1));
		} catch (InvalidPathException e) {
			env.writeln("Invalide path.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(firstPath)) {
			env.writeln("First argument cant be directory.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.exists(firstPath)) {
			env.writeln("File doesnt exist.");
			return ShellStatus.CONTINUE;
		}
		if (Files.isDirectory(secondPath)) {
			secondPath = Paths.get(secondPath + "\\" + firstPath.getFileName());
		}
		
		try {
			if(Files.exists(secondPath) && Files.isSameFile(firstPath, secondPath) ) {
				env.writeln("Cant copy file to itself.");
				return ShellStatus.CONTINUE;
			}
		} catch (IOException e1) {
			env.writeln("Error occured");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(secondPath)) {
			env.write("File already exists.Would you like to overwrite it?(Y/N)");
			env.write(env.getPromptSymbol() + " ");
			String answer = env.readLine();
			if (!answer.equals("Y")) {
				env.writeln("File wont be copied.");
				return ShellStatus.CONTINUE;
			}
		}

		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(firstPath, StandardOpenOption.READ));
				BufferedOutputStream os = new BufferedOutputStream(
						Files.newOutputStream(secondPath, StandardOpenOption.CREATE))) {

			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(buff, 0, r);
			}

		} catch (IOException e) {
			env.writeln("Error occured while copying file.");

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
