package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/***
 * This class represents echo worker
 * 
 * @author af
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Set<String> parameterNames = context.getParameterNames();
		context.setMimeType("text/html");

		context.write("<html><head>");
		context.write("<style>");
		context.write("table, th, td {" + "border: 1px solid black;" + "}");
		context.write("</style></head><body>");
		context.write("<h1>Hello!!!</h1>");
		context.write("<table id=\"Tablica parametara\">");
		context.write("<tr>");
		context.write("<th>Parametar</th>");
		context.write("<th>Vrijednost</th>");
		context.write("<tr>");

		for (String s : parameterNames) {
			context.write("<tr>");
			context.write("<td>" + s + "</td>");
			context.write("<td>" + context.getParameter(s) + "</td>");
			context.write("<tr>");

		}
		context.write("</table>");

	}

}
