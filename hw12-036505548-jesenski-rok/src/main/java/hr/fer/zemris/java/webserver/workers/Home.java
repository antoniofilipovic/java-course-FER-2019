package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * This class represents home worker
 * @author af
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color=context.getPersistentParameter("bgcolor");
		
		context.setTemporaryParameter("background", color==null?"7F7F7F":color);
		
		IDispatcher dispatcher = context.getIDispatcher();
		dispatcher.dispatchRequest("/private/pages/home.smscr");
		
		
	}

}
