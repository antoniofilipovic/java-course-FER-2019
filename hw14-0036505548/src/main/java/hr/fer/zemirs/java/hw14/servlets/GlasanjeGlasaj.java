package hr.fer.zemirs.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * This servlet stores vote that was registered to file, or creates new file and
 * stores that vote.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-glasaj" })
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
		
		
		String id = req.getParameter("id");
		String pollId=req.getParameter("pollID");
		req.setAttribute("pollId", Long.valueOf(pollId));
		DAOProvider.getDao().addVote(Long.valueOf(id),Long.valueOf(pollId));
	

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+Long.valueOf(pollId));

	}

}
