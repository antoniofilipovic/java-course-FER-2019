package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class represents listener. It memorizes time when server was started.
 * 
 * @author af
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("time", System.currentTimeMillis());

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// sce.getServletContext().removeAttribute("time");

	}

}
