package hr.fer.zemirs.java.hw14.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptions;

/**
 * This servlet represents png pie chart picture of results that were stored in
 * txt file
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method creates chart and writes it to http page
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		
		long pollId=Long.valueOf(String.valueOf(req.getParameter("pollID")));
		
		OutputStream outputStream = resp.getOutputStream();
		List<PollOptions> list=DAOProvider.getDao().getPollOptions(pollId,"id");
		JFreeChart chart = getChart(list);
		int width = 500;
		int height = 350;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);

	}

	/**
	 * This method returns chart
	 * 
	 * @param map map of data
	 * @return chart
	 */
	public JFreeChart getChart(List<PollOptions> list) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		list.forEach(v -> dataset.setValue(v.getOptionTitle(),v.getVotesCount()));

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Bands", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.GREEN);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
