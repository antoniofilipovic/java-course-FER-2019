package hr.fer.zemris.java.servlets.glasanje;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class represents servlet that produces xls file with stored results in
 * it
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/glasanje-xls" })
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method creates xls results
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		Map<Integer, Data> map = (Map<Integer, Data>) req.getSession().getAttribute("votesResults");

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("new šit");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Bands");
		rowhead.createCell((short) 1).setCellValue("Votes");

		Set<Entry<Integer, Data>> set = map.entrySet();
		int i = 0;
		for (Entry<Integer, Data> e : set) {
			i++;
			HSSFRow row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(e.getValue().getName());
			row.createCell((short) 1).setCellValue(e.getValue().getVotes());
		}

		hwb.write(resp.getOutputStream());
		resp.getOutputStream().close();
		hwb.close();

	}

}
