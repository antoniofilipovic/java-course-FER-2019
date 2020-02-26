package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.form.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.model.form.BlogEntryForm;

/**
 * This class represents servlet that enables loged in user to enter new blog or
 * edit previous blogs and also for other users to comment on those blogs.
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/author/*" })
public class AuthorServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method calls other method with parametar get for method
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp, "get");
	}

	/**
	 * {@inheritDoc} This method calls other method with parametar post for method
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp, "post");
	}

	/**
	 * This method depending on path and method calls other methods
	 * 
	 * @param req    request
	 * @param resp   response
	 * @param method method
	 * @throws ServletException exception
	 * @throws IOException      ioexception
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp, String method)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.err.println(pathInfo);
		String[] parts = pathInfo.substring(1).split("/");

		String nick = parts[0];
		System.err.println("Nick je:" + nick);
		req.setAttribute("author", nick);

		if (1 >= parts.length) {
			switch (method) {
			case "get":
				renderBlogEntriesGetMethod(req, resp, nick);
				return;
			default:
				req.setAttribute("error", "Pogreška pri pozivu stranice.");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}
		}

		boolean isEID;
		String operation;
		long eid = -1;
		String temp = parts[1];
		isEID = false;
		operation = null;
		try {
			eid = Long.parseLong(temp);
			isEID = true;
		} catch (NumberFormatException e) {
			operation = temp;
		}
		if (isEID) {
			switch (method) {
			case "get":
				renderBlogEntryGetMethod(req, resp, eid, nick);
				return;

			case "post":
				renderBlogEntryPostMethod(req, resp, eid, nick);
				return;

			default:
				req.setAttribute("error", "Pogreška pri pozivu stranice.");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}

		}

		switch (method) {
		case "get":
			renderOperationGetMethod(req, resp, operation, nick);
			return;
		case "post":
			renderOperationPostMethod(req, resp, operation, nick);
			return;
		default:
			req.setAttribute("error", "Pogrešna metoda");
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}

	}

	/**
	 * This method renders operation get new or edit from get method
	 * 
	 * @param req       request
	 * @param resp      response
	 * @param operation operation
	 * @param nick      nick
	 * @throws ServletException exception
	 * @throws IOException      exception
	 */
	private void renderOperationGetMethod(HttpServletRequest req, HttpServletResponse resp, String operation,
			String nick) throws ServletException, IOException {
		if (operation.equals("edit")) {
			String loggedinUser=String.valueOf(req.getSession().getAttribute("current.user.nick"));
			if (loggedinUser==null || !loggedinUser.equals(nick)) {
				req.setAttribute("error", "Samo vlasnik može uređivati unose u blogu.");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}
			String eid = req.getParameter("EID");
			long id = Long.parseLong(eid);

			BlogEntry r = DAOProvider.getDAO().getBlogEntry(id);
			if (r == null) {
				req.setAttribute("error", "Ne postoji unos bloga s tim ID-jem");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}
			BlogEntryForm f = new BlogEntryForm();

			f.popuniIzRecorda(r);
			if (r.getCreatedAt() == null) {
				System.err.println("Created at je null.");
			}
			f.setCreatedAt(r.getCreatedAt());

			req.setAttribute("unos", f);
			req.setAttribute("id", "notEmpty");
			req.setAttribute("action", "edit?EID=" + eid);
			req.getRequestDispatcher("/WEB-INF/pages/authorNew.jsp").forward(req, resp);

		} else if (operation.equals("new")) {
			String loggedinUser=String.valueOf(req.getSession().getAttribute("current.user.nick"));
		
			if (loggedinUser==null || !loggedinUser.equals(nick)) {
				req.setAttribute("error", "Samo vlasnik može dodavati nove unose u blogu.");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}

			BlogEntry r = new BlogEntry();
			BlogEntryForm f = new BlogEntryForm();

			f.popuniIzRecorda(r);
			req.setAttribute("id", "");
			req.setAttribute("unos", f);
			req.setAttribute("action", "new");
			req.getRequestDispatcher("/WEB-INF/pages/authorNew.jsp").forward(req, resp);

		} else {
			req.setAttribute("error", "Pogrešna operacija.");
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
		}

	}

	/**
	 * This method renders operation get or edit from post method
	 * 
	 * @param req       request
	 * @param resp      response
	 * @param operation operation
	 * @param nick      nick
	 * @throws ServletException exception
	 * @throws IOException      exception
	 */
	private void renderOperationPostMethod(HttpServletRequest req, HttpServletResponse resp, String operation,
			String nick) throws ServletException, IOException {
		if (operation.equals("edit")) {
			System.out.println("zovemo edit u post metod");
			if (!req.getSession().getAttribute("current.user.nick").equals(nick)) {
				req.setAttribute("error", "Samo vlasnik može uređivati unose u blogu.");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}
			String eid = req.getParameter("EID");
			long id = Long.parseLong(eid);

			req.setCharacterEncoding("UTF-8");

			BlogEntryForm f = new BlogEntryForm();
			f.popuniIzHttpRequesta(req);
			f.validiraj();

			if (f.imaPogresaka()) {
				req.setAttribute("unos", f);
				req.setAttribute("id", "notEmpty");
				req.setAttribute("action", "edit?EID=" + eid);
				req.getRequestDispatcher("/WEB-INF/pages/authorNew.jsp").forward(req, resp);
				return;
			}

			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

			f.popuniURecord(entry);

			Date date = new Date();
			entry.setLastModifiedAt(date);

			resp.sendRedirect(req.getContextPath() + "/servleti/main");

		} else if (operation.equals("new")) {

			if (!req.getSession().getAttribute("current.user.nick").equals(nick)) {
				req.setAttribute("error", "Samo vlasnik može dodavati nove unose u blogu.");
				req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
				return;
			}

			req.setCharacterEncoding("UTF-8");

			BlogEntryForm f = new BlogEntryForm();
			f.popuniIzHttpRequesta(req);
			f.validiraj();

			if (f.imaPogresaka()) {
				req.setAttribute("id", "");
				req.setAttribute("unos", f);
				req.setAttribute("action", "new");
				req.getRequestDispatcher("/WEB-INF/pages/authorNew.jsp").forward(req, resp);
				return;
			}

			BlogEntry entry = new BlogEntry();

			f.popuniURecord(entry);

			entry.setCreator(DAOProvider.getDAO().getUser(nick));

			Date date = new Date();
			entry.setCreatedAt(date);

			DAOProvider.getDAO().createBlogEntry(entry);
			resp.sendRedirect(req.getContextPath() + "/servleti/main");

		} else {
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
		}
	}

	/**
	 * This method prepares blog entry and comments from get method
	 * 
	 * @param req  request
	 * @param resp response
	 * @param eid  id of entry
	 * @param nick nick
	 * @throws ServletException exception
	 * @throws IOException      ioexception
	 */
	private void renderBlogEntryGetMethod(HttpServletRequest req, HttpServletResponse resp, long eid, String nick)
			throws ServletException, IOException {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
		if (entry == null) {
			req.setAttribute("error", "Ne postoji unos bloga s tim ID-jem");
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		List<BlogComment> blogComments = DAOProvider.getDAO().getBlogComments(entry);

		req.setAttribute("komentari", blogComments);

		BlogComment r = new BlogComment();
		BlogCommentForm f = new BlogCommentForm();

		f.popuniIzRecorda(r);

		req.setAttribute("komentar", f);
		req.setAttribute("eid", eid);
		req.setAttribute("potpuniUnos", entry);
		req.getRequestDispatcher("/WEB-INF/pages/authorBlogEntry.jsp").forward(req, resp);

	}

	/**
	 * This method renders blog entry from post method and its comments
	 * 
	 * @param req  request
	 * @param resp response
	 * @param eid  entry id
	 * @param nick nick
	 * @throws ServletException exception
	 * @throws IOException      exception
	 */
	private void renderBlogEntryPostMethod(HttpServletRequest req, HttpServletResponse resp, long eid, String nick)
			throws ServletException, IOException {
		
		
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
		if (entry == null) {
			req.setAttribute("error", "Ne postoji unos bloga s tim ID-jem");
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		List<BlogComment> blogComments = DAOProvider.getDAO().getBlogComments(entry);

		req.setAttribute("komentari", blogComments);
		req.setAttribute("potpuniUnos", entry);

		req.setCharacterEncoding("UTF-8");

		BlogCommentForm commentForm = new BlogCommentForm();
		
		if(!String.valueOf(req.getSession().getAttribute("current.user.nick")).isEmpty()) {
			commentForm.setEmail(DAOProvider.getDAO().getUser(nick).getEmail());
		}
		commentForm.popuniIzHttpRequesta(req);
		
		
		commentForm.validiraj();

		if (commentForm.imaPogresaka()) {
			req.setAttribute("komentar", commentForm);
			req.getRequestDispatcher("/WEB-INF/pages/authorBlogEntry.jsp").forward(req, resp);
			return;
		}

		BlogComment comment = new BlogComment();
		commentForm.popuniURecord(comment);

		Date date = new Date();
		comment.setBlogEntry(entry);
		comment.setPostedOn(date);
		
		

		DAOProvider.getDAO().createBlogComment(comment);
		// ovo prepravit
		
		blogComments = DAOProvider.getDAO().getBlogComments(entry);
		entry.addComment(comment);
		req.setAttribute("komentari", blogComments);
		req.setAttribute("eid", eid);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
		// req.getRequestDispatcher("/WEB-INF/pages/authorBlogEntry.jsp").forward(req,
		// resp);

	}

	/**
	 * This method renders blog entries from only get method
	 * 
	 * @param req  request
	 * @param resp response
	 * @param nick nick
	 * @throws ServletException exception
	 * @throws IOException      exception
	 */
	private void renderBlogEntriesGetMethod(HttpServletRequest req, HttpServletResponse resp, String nick)
			throws ServletException, IOException {

		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if (user == null) {
			System.out.println("user je null");
		}
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntries(user);
		// req.setAttribute("author", nick);
		req.setAttribute("blogEntries", blogEntries);
		req.getRequestDispatcher("/WEB-INF/pages/blogEntries.jsp").forward(req, resp);

	}
}
