package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents servlet that produces html that gives user possibility
 * to vote to their favourite band.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method prepares site for voting
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<Integer> ids = new ArrayList<>();
		Map<Integer, Data> data = new TreeMap<>();

		for (String s : lines) {
			String[] parts = s.split("\\t+");
			int id = Integer.parseInt(parts[0]);
			data.put(id, new Data(parts[0], parts[1], parts[2]));
			ids.add(id);

		}
		Collections.sort(ids);

		data = GlasanjeUtil.readAllBands(fileName);

		req.getSession().setAttribute("podatci", data);
		req.setAttribute("ids", ids);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
