package hr.fer.zemris.java.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class represents servlet that sets color 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/setcolor" })
public class SetColorsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc} This method sets colors
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color=req.getParameter("color");
		if(color!=null) {
			req.getSession().setAttribute("pickedBgCol", color);
		}
		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
		
	}

}
