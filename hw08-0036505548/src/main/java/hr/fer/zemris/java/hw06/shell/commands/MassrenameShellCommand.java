package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.massrename.FilterResult;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.massrename.parser.NameBuilderParser;
import hr.fer.zemris.java.hw06.shell.massrename.parser.NameBuilderParserException;

/**
 * This class represents implementation of massrename command.Command massrename
 * takes four or five arugments.If it receives four arugments then filter or
 * groups methods can be called.If it receives five arugments then show or
 * execute methods can be called.Method filter filters all files depending on
 * pattern.Method groups performs grouping for every file. Method show shows us
 * how file will be renamed.Method execute moves file from one directory to
 * another with given pattern and namebuilder.
 * 
 * @author af
 *
 */
public class MassrenameShellCommand implements ShellCommand {
	/**
	 * Name of command that needs to be writen in console to start this command.
	 */
	private String commandName = "massrename";
	/**
	 * This represents description that will be written to console if help command
	 * is called.
	 */
	private List<String> description = Arrays.asList(new String[] { "Command massrename takes four or five arugments.",
			"If it receives four arugments then filter or groups methods can be called.",
			"If it receives five arugments then show or execute methods can be called.",
			"Method filter filters all files depending on pattern", "Method groups performs grouping for every file.",
			"Method show shows us how file will be renamed",
			"Method execute moves file from one directory to another with given pattern and namebuilder." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		if (parts.size() != 4 && (parts.get(2).equals("filter") || parts.get(2).equals("groups"))) {
			env.writeln("Wrong number of arguments for massrename " + parts.get(2) + " command ");
			return ShellStatus.CONTINUE;
		}
		if (parts.size() != 5 && (parts.get(2).equals("execute") || parts.get(2).equals("show"))) {
			env.writeln("Wrong number of arguments for massrename " + parts.get(2) + " command ");
			return ShellStatus.CONTINUE;
		}
		Path firstDir, secondDir;
		try {
			firstDir = env.getCurrentDirectory().resolve(parts.get(0));
			secondDir = env.getCurrentDirectory().resolve(parts.get(1));
		} catch (InvalidPathException e) {
			env.writeln("Invalide path.");
			return ShellStatus.CONTINUE;
		}

		if (!(Files.isDirectory(firstDir) && Files.isDirectory(secondDir))) {
			env.writeln("Both  arguments must be directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			List<FilterResult> filteredFiles = filter(firstDir, parts.get(3));
			switch (parts.get(2)) {
			case "filter":
				filteredFiles.stream().forEach(p -> env.writeln(p.toString()));
				break;
			case "groups":
				for (FilterResult r : filteredFiles) {
					StringBuilder sb = new StringBuilder();
					sb.append(r.toString() + " ");
					for (int i = 0; i <= r.numberOfGroups(); i++) {
						sb.append(i + ": " + r.group(i) + " ");
					}
					env.writeln(sb.toString());
					sb.setLength(0);
				}
				break;
			case "show":
			case "execute":
				boolean execute = parts.get(2).equals("execute");
				NameBuilder builder = null;
				try {
					NameBuilderParser parser = new NameBuilderParser(parts.get(4));
					builder = parser.getNameBuilder();
				} catch (NameBuilderParserException e) {
					env.writeln("Error occured during parsing. " + e.getMessage());
					return ShellStatus.CONTINUE;
				}

				for (FilterResult file : filteredFiles) {
					StringBuilder sb = new StringBuilder();
					builder.execute(file, sb);
					String newName = sb.toString();
					if (execute) {
						Path movePath = secondDir.resolve(newName);
						Path firstPath = firstDir.resolve(file.toString());
						Files.move(firstPath, movePath, StandardCopyOption.REPLACE_EXISTING);
					}

					else {
						env.writeln(file.toString() + " => " + newName);
					}
				}
				break;

			default:
				env.writeln("Wrong command name. Use help to view what are command names.");
			}
		} catch (IOException | PatternSyntaxException |ShellIOException e) {
			env.writeln("Exception occured. " + e.getMessage());
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

	/**
	 * This method filters all files depending on filter pattern. It then creates
	 * and returns list of file results. It only filters files.
	 * 
	 * @param dir     directory for which filter will be performed
	 * @param pattern pattern on which filter will be performed
	 * @return list of filter results
	 * @throws IOException if something happens with creating directory stream
	 */
	public static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> fiteredFiles = new ArrayList<>();
		Pattern p = Pattern.compile(pattern);
		Matcher m;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				if (!Files.isDirectory(entry)) {
					m = p.matcher(entry.getFileName().toString());
					if (m.matches()) {
						fiteredFiles.add(new FilterResult(entry, p, m));
					}
				}
			}
		}
		return fiteredFiles;
	}

}
