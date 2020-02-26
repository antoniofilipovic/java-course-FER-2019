package hr.fer.zemirs.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * This class represents servlet that produces xls file with stored results in
 * it
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-xls" })
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method creates xls results
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		
		long pollId=Long.valueOf(String.valueOf(req.getParameter("pollID")));
		List<PollOptions> list=DAOProvider.getDao().getPollOptions(pollId,"votesCount desc");

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("new šit");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Bands");
		rowhead.createCell((short) 1).setCellValue("Votes");

		
		int i = 0;
		for (PollOptions o:list) {
			i++;
			HSSFRow row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(o.getOptionTitle());
			row.createCell((short) 1).setCellValue(o.getVotesCount());
		}

		hwb.write(resp.getOutputStream());
		resp.getOutputStream().close();
		hwb.close();

	}

}
