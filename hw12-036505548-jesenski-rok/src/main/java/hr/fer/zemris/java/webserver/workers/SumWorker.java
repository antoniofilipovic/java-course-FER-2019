package hr.fer.zemris.java.webserver.workers;



import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * This class represents sum worker
 * @author af
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String aText = context.getParameter("a");
		String bText = context.getParameter("b");
		int a = 1;
		int b = 2;
		if (aText != null) {
			try {
				a = Integer.parseInt(aText);
			} catch (NumberFormatException e) {
				//
			}
		}
		if (bText != null) {
			try {
				b= Integer.parseInt(bText);
			} catch (NumberFormatException e) {
				//
			}
		}
	
		int sum = a + b;
		String imgName = null;
		if (sum % 2 == 0) {
			imgName = "images/first.jpg";
		} else {

			imgName = "images/second.jpg";
		}
		context.setTemporaryParameter("zbroj", String.valueOf(sum));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		context.setTemporaryParameter("imgName", imgName);
		
		
		IDispatcher dispatcher = context.getIDispatcher();
		dispatcher.dispatchRequest("/private/pages/calc.smscr");

	}

}
