package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class draws barchart from values in document
 * 
 * @author af
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * bar chart
	 */
	private BarChart barChart;
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor for barchat
	 * 
	 * @param barchart2 barchart
	 * @param args      arg
	 */
	public BarChartDemo(BarChart barchart2, String args) {
		super();
		this.barChart = barchart2;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChart");
		setLocation(20, 20);
		setSize(500, 500);

		initGUI(args);
	}

	/**
	 * This method initializes gui
	 * 
	 * @param args
	 */
	private void initGUI(String args) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new JLabel(Paths.get(args) + ""), BorderLayout.NORTH);
		cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);
		cp.setBackground(Color.WHITE);

	}

	/**
	 * This method starts when main program stars
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Wrong number of arguments.");
			System.exit(-1);
		}
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Exception was thrown while reading from file.");
			System.exit(0);
		}
		if (lines.size() < 6) {
			System.out.println("Wrong number of parameters");
			System.exit(-1);
		}
		try {
			final BarChart barchart = prepareAll(lines);
			SwingUtilities.invokeLater(() -> {
				new BarChartDemo(barchart, args[0]).setVisible(true);
			});
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

	}

	/**
	 * This method reads all values, creates barchart and returns it
	 * 
	 * @param lines
	 * @return
	 */
	private static BarChart prepareAll(List<String> lines) {
		List<XYValue> xyvalues = new ArrayList<>();
		String[] values = lines.get(2).split("\\s+");
		for (String s : values) {
			String[] parts = s.split(",");
			if (parts.length != 2) {
				throw new RuntimeException("Wrong number of arguments for XYValue");
			}
			xyvalues.add(new XYValue(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}
		int yMin = Integer.parseInt(lines.get(3));
		int yMax = Integer.parseInt(lines.get(4));
		int diff = Integer.parseInt(lines.get(5));
		return new BarChart(xyvalues, lines.get(0), lines.get(1), yMin, yMax, diff);

	}

}
