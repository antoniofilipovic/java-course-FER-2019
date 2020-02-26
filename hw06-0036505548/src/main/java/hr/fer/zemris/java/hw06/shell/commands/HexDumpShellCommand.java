package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
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

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellArgumentsParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * This class represents implementation of hexdump command. It expects a single
 * argument: file name, and produces hex-output with four colums. First column
 * is current position. Second column is first eight bytes interpreted as hex
 * byte. Next follows separation symobl '|'. Third column is same as second
 * column. Last column represents characters. Only subset of them is supported.
 * For others '.' is printed instead (all bytes whose value is less than 32 or
 * greater than 127 )
 * 
 * @author Antonio Filipović
 *
 */
public class HexDumpShellCommand implements ShellCommand {
	/**
	 * This is name of commad as entered in shell.
	 */
	private String commandName = "hexdump";
	/**
	 * This is output that will follow when help command is called
	 */
	private List<String> description = Arrays.asList(new String[] {
			"Hexdump command  expects a single argument: file name, and produces hex-output with four colums.",
			"First column is current position in text. Second column is first eight bytes interpreted as hex bytes.",
			"Next follows separation symobl '|'. Third column is same as second column.",
			"Last column represents characters. Only subset of them is supported.",
			"For others '.' is printed instead (all bytes whose value is less than 32 or greater than 127." });
	/**
	 * Format for outputs
	 */
	private static final int HEX_DUMP_FORMAT = 16;
	/**
	 * Min character that is accepted
	 */
	private static final int MIN = 32;
	/**
	 * Max character that is accepted.
	 */
	private static final int MAX = 127;

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
			env.writeln("One argument expected but " + parts.size() + " given.");
			return ShellStatus.CONTINUE;
		}
		Path filePath;
		try {
			filePath = Paths.get(parts.get(0));
		} catch (InvalidPathException e) {
			env.writeln("Invalide path.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(filePath)) {
			env.writeln("Argument cant be directory.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(filePath)) {
			env.writeln("File doesn't exist.");
			return ShellStatus.CONTINUE;
		}

		try (BufferedInputStream is = new BufferedInputStream(
				Files.newInputStream(filePath, StandardOpenOption.READ))) {

			int position = 0;

			while (true) {
				byte[] buff = new byte[HEX_DUMP_FORMAT];
				int r = is.read(buff);
				if (r < 1)
					break;
				env.writeln(formatOutput(position, r, buff));
				position += HEX_DUMP_FORMAT;
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

	/**
	 * This method formats output as explained before.
	 * 
	 * @param position current position, first column
	 * @param r        number of bytes that were read
	 * @param buff     buffer of bytes
	 * @return String for output
	 */
	private static String formatOutput(int position, int r, byte[] buff) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%08x: ", position));
		for (int i = 0; i < HEX_DUMP_FORMAT; i++) {
			if (i >= r) {
				sb.append("   ");
			} else if (i < r) {
				sb.append(Util.bytetohex(new byte[] {buff[i]})).append(" ");
			}
			if (i == 7 || i == 15) {
				if (i == 7)
					sb.setLength(sb.length() - 1);
				sb.append("|");
				if (i == 15)
					sb.append(" ");
			}
		}
		for (int i = 0; i < r; i++) {
			if (buff[i] < MIN || buff[i] > MAX) {
				sb.append(".");
			} else {
				sb.append(String.format("%c", buff[i]));
			}
		}

		return sb.toString();

	}
}
