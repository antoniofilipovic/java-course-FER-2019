package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents servlet that calculates trigonometric values, sin and
 * cos for angles in degrees from a to b. It uses jsp to produce web page.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@inheritDoc} This method calculates sin and cos for numbers from a to b
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String valueA = req.getParameter("a");
		String valueB = req.getParameter("b");
		int a = 0;
		int b = 360;
		try {
			a = Integer.parseInt(valueA);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			b = Integer.parseInt(valueB);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (b < a) {
			int temp = b;
			b = a;
			a = temp;
		}
		System.out.println(a + "," + b);
		if (b > a+720) {
			b=a+720;
		}
		req.setAttribute("a", String.valueOf(a));
		req.setAttribute("b", String.valueOf(b));
		for (int i = a; i <= b; i++) {
			double sinx = Math.sin(i * Math.PI / 180);
			double cosx = Math.cos(i * Math.PI / 180);
			req.setAttribute("sin(" + i + ")", sinx);
			req.setAttribute("cos(" + i + ")", cosx);
		}

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

}
