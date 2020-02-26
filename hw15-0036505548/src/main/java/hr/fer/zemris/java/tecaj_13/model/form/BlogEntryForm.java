package hr.fer.zemris.java.tecaj_13.model.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This model represents web-representation of domain object {@link BlogEntry}.
 * 
 * @author af
 *
 */
public class BlogEntryForm {
	/**
	 * Title
	 */
	private String title;
	/**
	 * Text
	 */
	private String text;
	/**
	 * Created at
	 */
	private Date createdAt;
	/**
	 * Errors
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Blog entry form constructor
	 */
	public BlogEntryForm() {
	}

	/**
	 * This method returns error
	 * 
	 * @param name name
	 * @return
	 */
	public String dohvatiPogresku(String name) {
		return errors.get(name);
	}

	/**
	 * This method returns true if there are errors
	 * 
	 * @return
	 */
	public boolean imaPogresaka() {
		return !errors.isEmpty();
	}

	/**
	 * This method returns true if there is error
	 * 
	 * @param name name
	 * @return
	 */
	public boolean imaPogresku(String name) {
		return errors.containsKey(name);
	}

	/**
	 * This method fills from http request
	 * 
	 * @param req request
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {

		this.title = pripremi(req.getParameter("title"));
		this.text = pripremi(req.getParameter("text"));

	}

	/**
	 * This method fills from record
	 * 
	 * @param entry entry
	 */
	public void popuniIzRecorda(BlogEntry entry) {
		this.title = entry.getTitle();
		this.text = entry.getText();

	}

	/**
	 * This method fills in record
	 * 
	 * @param entry
	 */
	public void popuniURecord(BlogEntry entry) {
		entry.setTitle(title);
		entry.setText(text);

	}

	/**
	 * This method validates if there are errors
	 */
	public void validiraj() {
		errors.clear();

		if (this.title.isEmpty()) {
			errors.put("title", "Naziv se mora unijeti");
		}

		if (this.title.length() > 200) {
			errors.put("title", "Maksimalna duljina naslova je 200 znakova.");
		}

		if (this.text.isEmpty()) {
			errors.put("text", "Mora se unijeti nekakav tekst.");
		}

		if (this.title.length() > 1000) {
			errors.put("text", "Maksimalna duljina teksta je 1000 znakova.");
		}

	}

	/**
	 * This method prepares string for filling form
	 * 
	 * @param s string
	 * @return non null string
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * This method returns title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method sets title
	 * 
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method gets text
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * This method setts text
	 * 
	 * @param text text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * This method sets created at
	 * 
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * This method gets created at
	 * 
	 * @return created at
	 */
	public Date getCreatedAt() {
		return this.createdAt;
	}

}