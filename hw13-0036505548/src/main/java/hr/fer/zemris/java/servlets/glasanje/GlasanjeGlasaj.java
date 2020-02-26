package hr.fer.zemris.java.servlets.glasanje;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet stores vote that was registered to file, or creates new file and
 * stores that vote.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasaj extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method registers vote
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		if (!Files.exists(filePath)) {
			File newFile = new File(fileName);
			newFile.createNewFile();
		}

		List<String> lines = Files.readAllLines(Paths.get(fileName));

		StringBuilder sb = new StringBuilder();
		String id = req.getParameter("id");
		boolean lineInserted = false;

		for (String s : lines) {
			if (s.startsWith(id)) {
				String[] parts = s.split("\\s+");
				int number = Integer.parseInt(parts[1]);
				number++;
				String newLine = id + "\t" + number + "\n";
				sb.append(newLine);
				lineInserted = true;
			} else {
				sb.append(s + "\n");
			}
		}
		if (!lineInserted) {
			String newLine = id + "\t1\n";
			sb.append(newLine);
		}
		OutputStream os = Files.newOutputStream(Paths.get(fileName));
		os.write(sb.toString().getBytes("UTF-8"));
		os.close();

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}

}
