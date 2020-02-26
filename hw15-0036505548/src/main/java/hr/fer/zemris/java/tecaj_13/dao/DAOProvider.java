package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class that knows how to return provider of services
 * 
 * @author af
 *
 */
public class DAOProvider {
	/**
	 * 
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Return sample
	 * 
	 * @return dao
	 */
	public static DAO getDAO() {
		return dao;
	}

}