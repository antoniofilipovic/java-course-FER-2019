package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface towards subsystem for persistancy of data
 * 
 * @author af
 *
 */
public interface DAO {

	/**
	 * Gets blog entry with given id
	 * 
	 * @param id id
	 * @return entry
	 * @throws DAOException exception
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * This method creates user
	 * 
	 * @param user user
	 * @throws DAOException exception
	 */
	void createUser(BlogUser user) throws DAOException;

	/**
	 * This method gets user
	 * 
	 * @param nick nickname
	 * @return user
	 * @throws DAOException exception
	 */
	BlogUser getUser(String nick) throws DAOException;

	/**
	 * Getter for users
	 * 
	 * @return list of users
	 * @throws DAOException exception
	 */
	List<BlogUser> getUsers() throws DAOException;

	/**
	 * Getter for blog entries
	 * 
	 * @param user user
	 * @return list of blog entries
	 * @throws DAOException exception
	 */
	List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;

	/**
	 * This method creates blog entry
	 * 
	 * @param user user
	 * @throws DAOException exception
	 */
	void createBlogEntry(BlogEntry user) throws DAOException;

	/**
	 * This method represents getter for blog comments
	 * 
	 * @param entry entry
	 * @return list of blog comments
	 * @throws DAOException daoexception
	 */
	List<BlogComment> getBlogComments(BlogEntry entry) throws DAOException;

	/**
	 * This method creates blog comment
	 * 
	 * @param comment comment
	 * @throws DAOException exception
	 */
	void createBlogComment(BlogComment comment) throws DAOException;

}