package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * This is initialization class that implements context listener and sets up
 * connection
 * 
 * @author af
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {
	/**
	 * {@inheritDoc} This method setts up connection
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String fileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		Properties props = new Properties();
		try {
			props.load(Files.newInputStream(Paths.get(fileName)));
		} catch (IOException e) {
			System.out.println("Error while reading from database settings properties");
			System.exit(-1);
		}

		String dbName = props.getProperty("name");
		String host = props.getProperty("host");
		String user = props.getProperty("user");
		String password = props.getProperty("password");

		String connectionURL = "jdbc:derby://" + host + ":" + props.getProperty("port") + "/" + dbName + "; user="
				+ user + ";" + "password=" + password + ";create=true";

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error while initalizing poll.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

//		ComboPooledDataSource cpds = new ComboPooledDataSource();
//		cpds.setProperties(props);
//		cpds.setProperties(properties);
//		try {
//			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
//		} catch (PropertyVetoException e1) {
//			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
//		}
//		

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		ServletContext servletContext = sce.getServletContext();

		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
		}

		System.out.println("+");

		DAOProvider.getDao().createTablePolls(con);
		DAOProvider.getDao().crateTablePollOptions(con);
		DAOProvider.getDao().fillTablePolls(con, servletContext);
	}

	/**
	 * {@inheritDoc} This method destroys source when context is destroyed
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Uništavamo nešto");
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}