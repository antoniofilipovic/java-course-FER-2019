package hr.fer.zemris.java.servlets.glasanje;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * This servlet represents png pie chart picture of results that were stored in
 * txt file
 * 
 * @author af
 *
 */
@WebServlet(urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} This method creates chart and writes it to http page
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		Map<Integer, Data> map = (Map<Integer, Data>) req.getSession().getAttribute("votesResults");

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart(map);
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
	public JFreeChart getChart(Map<Integer, Data> map) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		map.forEach((k, v) -> dataset.setValue(v.getName(), v.getVotes()));

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
