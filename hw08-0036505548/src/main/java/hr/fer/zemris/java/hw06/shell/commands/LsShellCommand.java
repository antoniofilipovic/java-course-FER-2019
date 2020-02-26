package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents implementation of ls command. It takes one argument -
 * directory - and formats output as follows:The output consists of 4
 * columns.First column indicates if current object is directory (d), readable
 * (r), writable (w) and executable (x).Second column contains object size in
 * bytes that is right aligned and occupies 10 characters. Next follows file
 * creation date/time and finally file name.
 * 
 * @author Antonio Filipović
 *
 */
public class LsShellCommand implements ShellCommand {
	/**
	 * Name of command
	 */
	private String commandName = "ls";
	/**
	 * Description used for help command
	 */
	private List<String> description = Arrays.asList(new String[] {
			"Command ls takes a single argument – directory – and writes a directory listing (not recursive).",
			"The output consists of 4 columns.",
			"First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).",
			"Second column contains object size in bytes that is right aligned and occupies 10 characters.",
			"Follows file creation date/time and finally file name." });

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> parts;
		try {
			parts = new ShellArgumentsParser(arguments).getArgumentsSplitted();
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (parts.size() != 1) {
			env.writeln("ls command expected exactly one argument.");
			return ShellStatus.CONTINUE;
		}
		Path path;
		try {
			path = env.getCurrentDirectory().resolve(parts.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalide path given.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.isDirectory(path)) {
			env.writeln("Path to directory was expected.");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.walkFileTree(path, new FileVisitorImpl(env));
		} catch (IOException e) {
			env.writeln("Error occured while going through directory.");
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
	 * This class represents implementation of file visitor. It is expected to
	 * receive directory so first method called is previsit directory. After that
	 * method was called and visitor entered directory no more directorys will be
	 * visited. It uses method format ouput for writing to console
	 * 
	 * @author Antonio Filipović
	 *
	 */
	private class FileVisitorImpl implements FileVisitor<Path> {
		/**
		 * Date format used for writeing time of creation
		 */
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**
		 * Reference to environment for writing to console
		 */
		private Environment env;
		/**
		 * Max size of second column widht
		 */
		private static final int MAX_SIZE = 10;
		/**
		 * If we visited first directory no more directorys will be visited.
		 */
		private boolean firstTree = true;

		public FileVisitorImpl(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

			env.writeln(formatOutput(attrs, dir));
			if (firstTree) {
				firstTree = false;
				return FileVisitResult.CONTINUE;
			}
			return FileVisitResult.SKIP_SUBTREE;

		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(formatOutput(attrs, file));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * This method is used for formating ouput
		 * 
		 * @param attrs attributes from file/directory
		 * @param p     path to file direcotry
		 * @return String format
		 */
		private String formatOutput(BasicFileAttributes attrs, Path p) {
			StringBuilder sb = new StringBuilder();

			if (attrs.isDirectory())
				sb.append("d");
			else
				sb.append("-");

			if (Files.isReadable(p))
				sb.append("r");
			else
				sb.append("-");

			if (Files.isWritable(p))
				sb.append("w");
			else
				sb.append("-");

			if (Files.isExecutable(p))
				sb.append("x");
			else
				sb.append("-");
			sb.append(" ");

			String size = String.valueOf(attrs.size());
			int numberOfSpaces = MAX_SIZE - size.length();
			sb.append(" ".repeat(numberOfSpaces)).append(size + " ");

			FileTime fileTime = attrs.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			sb.append(formattedDateTime + " ");
			sb.append(p.getFileName());
			return sb.toString();
		}

	}

}
