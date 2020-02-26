package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This class represents main servlet
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method renders main page from post method, it also logs in
	 * user
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		String errorMessage = "";

		if (nick != null) {
			if (password != null) {
				String passwordSha = getSha(password);
				BlogUser user = DAOProvider.getDAO().getUser(nick);
				if (user == null) {
					errorMessage = "Kriva lozinka ili password.";
				} else if (user != null && !user.getPasswordHash().equals(passwordSha)) {
					errorMessage = "Kriva lozinka ili password.";
				} else if (user != null && user.getPasswordHash().equals(passwordSha)) {
					
					req.getSession().setAttribute("current.user.id", user.getId());
					req.getSession().setAttribute("current.user.fn", user.getFirstName());
					req.getSession().setAttribute("current.user.ln", user.getLastName());
					req.getSession().setAttribute("current.user.nick", user.getNick());

				}
			} else {
				errorMessage = "Kriva lozinka ili password.";
			}
		} else {
			if (password != null) {
				nick = "";
				errorMessage = "Kriva lozinka ili password";
			}
		}

		req.setAttribute("errorMessage", errorMessage);
		if (!errorMessage.isEmpty()) {
			System.out.println("ima errora");
			req.setAttribute("nick", nick);
		}

		List<BlogUser> users = DAOProvider.getDAO().getUsers();

		req.setAttribute("users", users);

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	/**
	 * {@inheritDoc} This method renders page from get method
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<BlogUser> users = DAOProvider.getDAO().getUsers();

		req.setAttribute("users", users);

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);

	}

	/**
	 * This method calculates sha for String(password for us)
	 * 
	 * @param password password
	 * @return calculated sha
	 */
	private String getSha(String password) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new DAOException("Exception.");
		}
		try {
			sha.update(password.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new DAOException("exception.");
		}

		byte[] result = sha.digest();

		StringBuilder sb = new StringBuilder();
		for (byte b : result) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
