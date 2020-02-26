package hr.fer.zemirs.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * This class represents servlet that produces results from file If there is no
 * file it creates one.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method creates results
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		String pollId=req.getParameter("pollID");
		List<PollOptions> list=DAOProvider.getDao().getPollOptions(Long.valueOf(pollId),"votesCount desc");//,id? 

		List<PollOptions> winners = new ArrayList<>();
		
		if(list!=null && list.size()!=0) {
			final long max=list.get(0).getVotesCount();
			list.forEach(v-> {if(max==v.getVotesCount()) {winners.add(v);}});
		}

		req.getSession().setAttribute("votesResults", list);
		req.getSession().setAttribute("winners", winners);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp?pollID="+Long.valueOf(pollId)).forward(req, resp);
	}

	

}
