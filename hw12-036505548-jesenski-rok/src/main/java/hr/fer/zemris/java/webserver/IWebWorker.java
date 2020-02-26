package hr.fer.zemris.java.webserver;
/**
 * This class represents web worker
 * @author af
 *
 */
public interface IWebWorker {
	/**
	 * This method processes request
	 * @param context context
	 * @throws Exception exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
