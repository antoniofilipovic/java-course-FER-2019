package hr.fer.zemris.java.hw16.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * This class is used for initialization of database
 * @author af
 *
 */
@WebListener
public class InitializationDB implements ServletContextListener {
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		GaleryDB.initDB(sce.getServletContext());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
