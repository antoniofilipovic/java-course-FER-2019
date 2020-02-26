package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents implementation of tree command. The tree command
 * expects a single argument: directory name and prints a tree (each directory
 * level shifts output two charatcers to the right). It uses implementation of
 * tree visitor
 * 
 * @author Antonio Filipović
 *
 */
public class TreeShellCommand implements ShellCommand {
	/**
	 * Name of command as referenced in shell
	 */
	private String commandName = "tree";
	/**
	 * Represents description writen when help command called
	 */
	private List<String> description = Arrays
			.asList(new String[] { "The tree command expects a single argument: directory name and prints a tree.",
					"Each directory level shifts output two charatcers to the right." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		}catch(IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		if (parts.size() != 1) {
			env.writeln("One argument was expected for tree command but " + parts.size() + " were given.");
			return ShellStatus.CONTINUE;
		}Path path;
		try {
			path=env.getCurrentDirectory().resolve(parts.get(0));
		}catch(InvalidPathException e) {
			env.writeln("Invalide path.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(path)) {
			env.writeln("Directory was expected.");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(path, new TreeOutput(env));
		}
		catch(IOException e) {
			System.out.println("Error occured while trying to get in directory.");
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
	
	private static class TreeOutput implements FileVisitor<Path>{
		private int level;
		private Environment env;
		public TreeOutput(Environment env) {
			this.env=env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2*level)+dir.getFileName());
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2*(level))+file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;

		}
		
	}

}
