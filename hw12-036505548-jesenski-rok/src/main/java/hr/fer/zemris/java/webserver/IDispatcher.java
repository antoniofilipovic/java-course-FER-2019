package hr.fer.zemris.java.webserver;

/**
 * This class represnets dispatcher
 * 
 * @author af
 *
 */
public interface IDispatcher {
	/**
	 * This method does something with request
	 * 
	 * @param urlPath path
	 * @throws Exception exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
