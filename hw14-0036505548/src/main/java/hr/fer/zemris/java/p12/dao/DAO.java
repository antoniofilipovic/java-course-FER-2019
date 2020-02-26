package hr.fer.zemris.java.p12.dao;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;

import hr.fer.zemris.java.p12.model.PollOptions;
import hr.fer.zemris.java.p12.model.Polls;

/**
 * Interface towards subsystem for persistancy of data
 * 
 * @author af
 *
 */
public interface DAO {
	/**
	 * This method creates table polls if one doesn't exist
	 * 
	 * @param con connection
	 * @throws DAOException exception
	 */
	void createTablePolls(Connection con) throws DAOException;

	/**
	 * This method creates table poll options
	 * 
	 * @param con
	 * @throws DAOException
	 */
	void crateTablePollOptions(Connection con) throws DAOException;

	/**
	 * This method fills table poll options
	 * 
	 * @param con    connection
	 * @param pollId pollid
	 * @param path   path
	 * @param sce    context
	 * @throws DAOException exception
	 */
	void fillTablePollOptions(Connection con, long pollId, String path, ServletContext sce) throws DAOException;

	/**
	 * This method fills table polls
	 * 
	 * @param con connection
	 * @param sce context
	 * @throws DAOException
	 */
	void fillTablePolls(Connection con, ServletContext sce) throws DAOException;

	/**
	 * This method returs polls
	 * 
	 * @return list of polls
	 * @throws DAOException exception
	 */
	List<Polls> getPolls() throws DAOException;

	/**
	 * This method returns pollOptions
	 * 
	 * @param pollId
	 * @param orderBy
	 * @return
	 * @throws DAOException
	 */
	List<PollOptions> getPollOptions(long pollId, String orderBy) throws DAOException;

	/**
	 * This method adds vote
	 * 
	 * @param id     id
	 * @param pollID poll id
	 * @throws DAOException exception
	 */
	void addVote(long id, long pollID) throws DAOException;

}