package hr.fer.zemris.java.tecaj_13.model.form;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * This model represents web-representation of domain object {@link BlogUser}.
 * 
 * @author af
 *
 */
public class BlogUserForm {
	/**
	 * Lastname
	 */
	private String lastName;
	/**
	 * Firstname
	 */
	private String firstName;
	/**
	 * Email
	 */
	private String email;
	/**
	 * Nick
	 */
	private String nick;
	/**
	 * Password hash
	 */
	private String passwordHash;
	/**
	 * Errors
	 */
	Map<String, String> errors = new HashMap<>();

	/**
	 * Public constructor
	 */
	public BlogUserForm() {
	}

	/**
	 * Getts error
	 * 
	 * @param ime name
	 * @return error
	 */
	public String dohvatiPogresku(String ime) {
		return errors.get(ime);
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
	 * Returns true if there is error
	 * 
	 * @param ime name
	 * @return boolean ,true if there is error
	 */
	public boolean imaPogresku(String ime) {
		return errors.containsKey(ime);
	}

	/**
	 * This method fills from http request
	 * 
	 * @param req request
	 */
	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.firstName = pripremi(req.getParameter("firstname"));
		this.lastName = pripremi(req.getParameter("lastname"));
		this.email = pripremi(req.getParameter("email"));
		this.nick = pripremi(req.getParameter("nick"));

		String passwordHashCalculated = getSha(req.getParameter("password"));
		this.passwordHash = pripremi(passwordHashCalculated);
	}

	/**
	 * This method gets sha
	 * 
	 * @param password
	 * @return
	 */
	private String getSha(String password) {
		MessageDigest sha = null;
		if(password==null || password.isEmpty()) {
			return null;
		}
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

	/**
	 * This method fills from record
	 * 
	 * @param r record
	 */
	public void popuniIzRecorda(BlogUser r) {

		this.firstName = r.getFirstName();
		this.lastName = r.getLastName();
		this.email = r.getEmail();
		this.nick = r.getNick();
		this.passwordHash = r.getPasswordHash();
	}
	

	/**
	 * This method fills in record
	 * 
	 * @param r domain object that should be filled
	 */
	public void popuniURecord(BlogUser r) {

		r.setFirstName(this.firstName);
		r.setLastName(this.lastName);
		r.setEmail(this.email);
		r.setNick(this.nick);
		r.setPasswordHash(passwordHash);
	}

	/**
	 * This method validates if there are errors
	 */
	public void validiraj() {
		errors.clear();

		if (passwordHash.isEmpty()) {
			errors.put("passwordHash", "Password se mora unijeti.");
		}

		if (this.firstName.isEmpty()) {
			errors.put("firstname", "Ime je obavezno!");
		} else if (firstName.length() > 200) {
			errors.put("firstname", "Ime ne smije biti veće od 200 znakova.");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastname", "Prezime je obavezno!");
		} else if (this.lastName.length() > 200) {
			errors.put("lastname", "Prezime ne smije biti veće od 200 znakova.");
		}

		if (email.isEmpty()) {
			errors.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail nije ispravnog formata.");
			}
		}
		if (nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan!");
		} else {
			if (DAOProvider.getDAO().getUser(nick) != null) {
				errors.put("nick", "Nick već postoji.");
			}

		}

	}

	/**
	 * This method prepares string so that it is not null but empty instead
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Getter for lastname
	 * 
	 * @return lastname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastname
	 * 
	 * @param lastname
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for firstname
	 * 
	 * @return firstname
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstname
	 * 
	 * @param firstname firstname
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email value
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for password hash
	 * 
	 * @return
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for password hash
	 * 
	 * @param passwordHash password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
