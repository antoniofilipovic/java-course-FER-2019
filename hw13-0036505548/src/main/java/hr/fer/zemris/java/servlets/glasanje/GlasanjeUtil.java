package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is help class to read from txt file all bands
 * 
 * @author af
 *
 */
public class GlasanjeUtil {
	/**
	 * This method reads all bands
	 * 
	 * @param fileName filename
	 * @return map of bands
	 * @throws IOException exception if one happens
	 */
	public static Map<Integer, Data> readAllBands(String fileName) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<Integer, Data> data = new TreeMap<>();

		for (String s : lines) {
			String[] parts = s.split("\\t+");
			int id = Integer.parseInt(parts[0]);
			data.put(id, new Data(parts[0], parts[1], parts[2]));

		}
		return data;

	}
}
