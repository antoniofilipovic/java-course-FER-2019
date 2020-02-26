package hr.fer.zemirs.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Polls;

/**
 * This class represents rendering of polls servlet
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/index.html" })
public class RenderPollsServlet extends HttpServlet {

	/**
	 * id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method gets list of polls and calls index that will render them
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Polls> list = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", list);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

	}

}
