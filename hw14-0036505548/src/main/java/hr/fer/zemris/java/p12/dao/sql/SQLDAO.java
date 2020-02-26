package hr.fer.zemris.java.p12.dao.sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollOptions;
import hr.fer.zemris.java.p12.model.Polls;

/**
 * This class represents implementation of subsystem DAO using technology
 * SQL.This concrete implementation expects connection
 * {@link SQLConnectionProvider} class.
 * 
 * @author af
 */
public class SQLDAO implements DAO {

	@Override
	public void createTablePolls(Connection con) throws DAOException {

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("CREATE TABLE POLLS " + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ " title VARCHAR(150) NOT NULL, " + " message CLOB(2048) NOT NULL " + ")");
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception e) {
			if (e instanceof SQLException) {
				if (((SQLException) e).getSQLState().equals("X0Y32")) {
					return;
				}
			}
			throw new DAOException("Exception occured while creating table", e);
		}

	}

	@Override
	public void crateTablePollOptions(Connection con) throws DAOException {

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
							+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
							+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception e) {
			if (e instanceof SQLException) {
				if (((SQLException) e).getSQLState().equals("X0Y32")) {
					return;
				}
			}
			throw new DAOException("Exception occured while creating table", e);
		}

	}

	@Override
	public void fillTablePollOptions(Connection con, long pollId, String path, ServletContext sce)
			throws DAOException {

		String fileNameDefinition = sce.getRealPath(path);

		List<String> linesDefinition = null;
		try {
			linesDefinition = Files.readAllLines(Paths.get(fileNameDefinition));
		} catch (IOException e) {
			System.out.println("Exception while reading fileNameDefinition file");
		}

		for (String s : linesDefinition) {
			PreparedStatement pst = null;
			String[] parts = s.split("\\t+");
			try {
				try {
					pst = con.prepareStatement(
							"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)");

					pst.setString(1, parts[1]);
					pst.setString(2, parts[2]);
					pst.setLong(3, pollId);
					pst.setLong(4, 0);
					int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1

					if (numberOfAffectedRows != 1) {
						System.out.println("ništa nije insertano u options ");
					}

				} finally {
					try {
						pst.close();
					} catch (SQLException ignorable) {
					}
				}

			} catch (Exception e) {
				throw new DAOException("Exception occured while updating table polls options.", e);
			}

		}

	}

	@Override
	public void fillTablePolls(Connection con, ServletContext sce) throws DAOException {

		String fileName = sce.getRealPath("/WEB-INF/polls.txt");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			System.out.println("Exception occured while reading from file polls.txt ");
			System.exit(-1);
		}

		try {
			PreparedStatement stat = con.prepareStatement("select count(*) from Polls");
			ResultSet results = stat.executeQuery();
			while (results.next()) {
				long count = results.getLong(1);
				if (count == lines.size()) {
					return;
				}
			}
		} catch (SQLException e1) {
			throw new DAOException("Exception occured while reading from table polls", e1);
		}

		for (String s : lines) {
			PreparedStatement pst = null;
			String[] parts = s.split("\\t+");
			try {
				try {

					pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
							Statement.RETURN_GENERATED_KEYS);
					String path = parts[3];
					pst.setString(2, parts[2]);
					pst.setString(1, parts[1]);

					int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1

					if (numberOfAffectedRows != 1) {
					}
					ResultSet rset = pst.getGeneratedKeys();

					rset.next();
					long pollID = rset.getLong(1);

					fillTablePollOptions(con, pollID, path, sce);
				} finally {
					try {
						pst.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}

			} catch (Exception ex) {
				throw new DAOException("Exception occured while updating table polls.", ex);
			}

		}

	}

	@Override
	public List<Polls> getPolls() throws DAOException {
		List<Polls> list = new ArrayList<>();
		PreparedStatement pst = null;
		Connection con = SQLConnectionProvider.getConnection();
		try {
			pst = con.prepareStatement("SELECT * from POLLS order by id");
			ResultSet rset = pst.executeQuery();
			try {
				while (rset.next()) {
					Polls poll = new Polls();

					poll.setId(rset.getLong(1)); // ili rset.getLong("id");
					poll.setTitle(rset.getString(2)); // ili rset.getString("title");
					poll.setMessage(rset.getString(3)); // ili rset.getString("message")
					list.add(poll);
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {

			}
		}
		return list;
	}

	@Override
	public List<PollOptions> getPollOptions(long pollId, String orderBy) throws DAOException {
		List<PollOptions> list = new ArrayList<>();
		PreparedStatement pst = null;
		Connection con = SQLConnectionProvider.getConnection();
		try {
			pst = con.prepareStatement("SELECT * from POLLOPTIONS where pollID=? order by " + orderBy);
			pst.setLong(1, pollId);
			ResultSet rset = pst.executeQuery();
			try {
				while (rset.next()) {
					PollOptions poll = new PollOptions();

					poll.setId(rset.getLong(1)); // ili rset.getLong("id");
					poll.setOptionTitle(rset.getString(2)); // ili rset.getString("title");
					poll.setOptionLink(rset.getString(3)); // ili rset.getString("message");
					poll.setPollID(rset.getLong(4));
					poll.setVotesCount(rset.getLong(5));
					list.add(poll);
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {

			}
		}
		return list;
	}

	@Override
	public void addVote(long id, long pollID) throws DAOException {
		PreparedStatement pst = null;
		Connection con = SQLConnectionProvider.getConnection();
		long votes = 0;
		try {
			pst = con.prepareStatement("select votesCount from POLLOPTIONS where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						votes = rs.getLong(1);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata glasova.", ex);
		}

		try {
			pst = con.prepareStatement("UPDATE POLLOPTIONS set votesCount=? WHERE id=?");
			pst.setLong(1, votes + 1);
			pst.setLong(2, id);
			pst.executeUpdate();

		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {

			}
		}

	}

}