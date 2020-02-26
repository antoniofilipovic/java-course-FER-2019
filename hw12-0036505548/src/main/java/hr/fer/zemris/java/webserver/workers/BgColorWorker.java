package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * This class represents bg color worker
 * @author af
 *
 */
public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color=context.getParameter("bgcolor");
		boolean colorUpdated=false;
		if(color!=null) {
			colorUpdated=true;
			color=color.toUpperCase();
			boolean isHex = color.matches("[0-9A-F]+");
			if(isHex) {
				String oldColor=context.getPersistentParameter("bgcolor");
				
				context.setPersistentParameter("bgcolor", color);
			}
		}
		//IDispatcher dispatcher=context.getIDispatcher();
		//dispatcher.dispatchRequest("/index2.html");
		if(colorUpdated) {
			context.write("Boja se promjenila <a href=/index2.html>Home</a> ");//
		}else {

			context.write("Boja se nije promjenila <a href=/index2.html>Home</a>");
		}
		
	}

}
