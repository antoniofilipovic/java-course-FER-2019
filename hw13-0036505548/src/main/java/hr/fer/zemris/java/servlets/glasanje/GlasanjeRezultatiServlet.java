package hr.fer.zemris.java.servlets.glasanje;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents servlet that produces results from file If there is no
 * file it creates one.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method creates results
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		if (!Files.exists(filePath)) {
			File newFile = new File(fileName);
			newFile.createNewFile();
		}
		List<String> lines = Files.readAllLines(Paths.get(fileName));

		Map<Integer, Data> data = (Map<Integer, Data>) req.getSession().getAttribute("podatci");

		if (data == null) {
			req.setAttribute("exception", "No data in glasanje-definicija.txt");
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(req, resp);
			return;
		}

		data.forEach((k, v) -> v.setVotes(0));

		Map<Integer, Integer> idVote = new HashMap<>();
		int maxVotes = 0;
		for (String s : lines) {
			String[] parts = s.split("\\s+");
			try {
				int id = Integer.parseInt(parts[0]);
				int votes = Integer.parseInt(parts[1]);
				idVote.put(id, votes);
				data.get(id).setVotes(votes);
				if (votes > maxVotes) {
					maxVotes = votes;
				}
			} catch (Exception e) {
				req.setAttribute("exception", "Can't parse votes.");
				req.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(req, resp);
				return;
			}
		}

		idVote = sortByValue(idVote);

		Map<Integer, Data> votesMap = new LinkedHashMap<>();

		final int max = maxVotes;

		Map<Integer, Data> winners = new LinkedHashMap<>();
		idVote.forEach((k, v) -> {
			if (v == max)
				winners.put(k, data.get(k));
		});

		idVote.forEach((k, v) -> votesMap.put(k, data.get(k)));

		req.getSession().setAttribute("votesResults", votesMap);
		req.getSession().setAttribute("winners", winners);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * This method sorts values in map by value
	 * 
	 * @param <K> Key
	 * @param <V> Value
	 * @param map map
	 * @return sorted map by value
	 */
	private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());
		Collections.reverse(list);

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

}
