package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class represents servlet that produces powers of numbers from a to b,
 * both included and calls jsp that produces web page.
 * 
 * @author af
 *
 */

@WebServlet(urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	/**
	 * {@inheritDoc} This method xls file of powers of numbers from a to b
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/vnd.ms-excel");
		String valueA = req.getParameter("a");
		String valueB = req.getParameter("b");
		String valueN = req.getParameter("n");
		int a = 1, b = 100, n = 3;
		try {
			a = Integer.parseInt(valueA);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		try {
			b = Integer.parseInt(valueB);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		try {
			n = Integer.parseInt(valueN);
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.getSession().setAttribute("exception",
					"Starting and ending numbers must be between -100 and 100, and number of pages must be betweem 1 and 5.");
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}
		
		

		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("new sheet " + i);
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Numbers");
			rowhead.createCell((short) 1).setCellValue("Power");
			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow((short) j - a + 1);
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
		}

		hwb.write(resp.getOutputStream());
		resp.getOutputStream().close();
		hwb.close();

	}
}
