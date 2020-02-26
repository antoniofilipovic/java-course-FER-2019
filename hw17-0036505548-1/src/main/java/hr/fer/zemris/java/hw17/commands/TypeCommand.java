package hr.fer.zemris.java.hw17.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw17.model.Document;
import hr.fer.zemris.java.hw17.shell.Command;
import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
/**
 * This class represents type command
 * @author af
 *
 */
public class TypeCommand implements Command {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		int number = 0;
		try {
			number = Integer.parseInt(arguments);
		} catch (Exception e) {
			env.writeln("Argument must be only one integer.");
			return ShellStatus.CONTINUE;
		}

		Map<Double, Document> results = env.getResults();

		if (results == null) {
			env.writeln("Query command must be called first.");
			return ShellStatus.CONTINUE;
		}

		if (number < 0 || number >= results.size()) {
			env.writeln("Argument needs to be positive number between 0 and " + (results.size() - 1));
			return ShellStatus.CONTINUE;
		}

		Collection<Document> resultsColl = results.values();
		Document d = null;
		int i = 0;
		for (Document d1 : resultsColl) {

			if (i == number) {
				d = d1;
				break;
			}
			i++;
		}
		String text=null;
		try {
			text=new String(Files.readAllBytes(d.getFilePath()));
		} catch (IOException e) {
			env.writeln("Error while trying to read from file.");
			return ShellStatus.CONTINUE;
			
		}
		env.writeln("Dokument:"+ d.getFilePath());
		env.writeln(text);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "type";
	}

}
