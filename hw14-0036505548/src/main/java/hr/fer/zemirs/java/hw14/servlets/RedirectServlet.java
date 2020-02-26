package hr.fer.zemirs.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is redirect servlet
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/index.html" })
public class RedirectServlet extends HttpServlet {

	/**
	 * id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method redirects
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("servleti/index.html");

	}

}
