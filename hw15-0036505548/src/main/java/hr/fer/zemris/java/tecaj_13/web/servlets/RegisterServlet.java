package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.form.BlogUserForm;

/**
 * This class represents register servlet
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/register" })
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} Prepares possiblitiy for registering user
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BlogUser r = new BlogUser();
		BlogUserForm f = new BlogUserForm();

		f.popuniIzRecorda(r);

		req.setAttribute("zapis", f);

		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	/**
	 * Registers user if there are no errors
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		BlogUserForm f = new BlogUserForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("zapis", f);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		System.err.println(f.dohvatiPogresku("passwordHash"));

		BlogUser user = new BlogUser();
		f.popuniURecord(user);
		

		DAOProvider.getDAO().createUser(user);
		
		
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		
		req.setAttribute("users",DAOProvider.getDAO().getUsers());

		resp.sendRedirect("main");


	}

}
