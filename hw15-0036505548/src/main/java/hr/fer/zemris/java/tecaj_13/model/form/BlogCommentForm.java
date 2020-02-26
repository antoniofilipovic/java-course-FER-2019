package hr.fer.zemris.java.tecaj_13.model.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * 
 * This model represents web-representation of domain object {@link BlogEntry}.
 * 
 * @author af
 *
 */
public class BlogCommentForm {
	/**
	 * Email
	 */
	private String email;
	/**
	 * Message
	 */
	private String message;
	/**
	 * Errors
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Blog comment form
	 */
	public BlogCommentForm() {
	}

	/**
	 * Getter for error
	 * 
	 * @param name name
	 * @return error
	 */
	public String dohvatiPogresku(String name) {
		return errors.get(name);
	}

	/**
	 * Returns true if there are errors
	 * 
	 * @return true
	 */
	public boolean imaPogresaka() {
		return !errors.isEmpty();
	}

	/**
	 * If there are errors returns true
	 * 
	 * @param name name
	 * @return true if there is error
	 */
	public boolean imaPogresku(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Fills from http request
	 * 
	 * @param req request
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		if(email==null) {
			this.email = pripremi(req.getParameter("email"));
		}
		this.message = pripremi(req.getParameter("message"));
	}

	/**
	 * Fills from record
	 * 
	 * @param comment comment
	 */
	public void popuniIzRecorda(BlogComment comment) {
		this.email = comment.getUsersEMail();
		this.message = comment.getMessage();
	}

	/**
	 * This method fills in record
	 * 
	 * @param comment comment
	 */
	public void popuniURecord(BlogComment comment) {
		comment.setUsersEMail(email);
		comment.setMessage(message);
	}

	/**
	 * This method validates
	 */
	public void validiraj() {
		errors.clear();

		if (this.email.isEmpty()) {
			errors.put("email", "E-Mail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "E-Mail nije ispravan!");
			}
		}

		if (this.message.isEmpty()) {
			errors.put("message", "Tekst je obavezan!");
		}

		if (this.message.length() > 4096) {
			errors.put("message", "Tekst je predugačak!");
		}

	}

	/**
	 * This method prepares string
	 * 
	 * @param s returns string that is not null
	 * @return
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * This method returns email
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method represents getter for message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * This method represents setter for email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method represents setter for message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
