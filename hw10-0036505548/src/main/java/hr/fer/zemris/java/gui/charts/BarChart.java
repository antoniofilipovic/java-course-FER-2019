package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents bar chart. It holds all important values and miny, maxy
 * and diff for y values. Miny cant be negative, and every value must be biger
 * than miny
 * 
 * @author af
 *
 */
public class BarChart {
	/**
	 * Values
	 */
	private List<XYValue> values;
	/**
	 * Name of xaxis
	 */
	private String xAxis;
	/**
	 * name of y axis
	 */
	private String yAxis;
	/**
	 * Min y value
	 */
	private int minY;
	/**
	 * Max y value
	 */
	private int maxY;
	/**
	 * differnce between x and y
	 */
	private int diff;

	/**
	 * Public constructor for barchart
	 * 
	 * @param values values
	 * @param xAxis  name of xaxis
	 * @param yAxis  name of y axis
	 * @param minY   min y
	 * @param maxY   max y
	 * @param diff   differnece betwwen y values
	 */
	public BarChart(List<XYValue> values, String xAxis, String yAxis, int minY, int maxY, int diff) {
		super();
		if (minY < 0 || maxY <= minY) {
			throw new RuntimeException();
		}
		for (XYValue value : values) {
			if (value.getY() < minY) {
				throw new RuntimeException();
			}
		}
		if ((maxY - minY) % diff != 0) {
			maxY = maxY + (maxY - minY) % diff;
		}
		this.values = values;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.minY = minY;
		this.maxY = maxY;
		this.diff = diff;
	}

	/**
	 * Getter for values
	 * 
	 * @return values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter for xaxis
	 * 
	 * @return xaxis
	 */
	public String getxAxis() {
		return xAxis;
	}

	/**
	 * Getter for y axis
	 * 
	 * @return yaxis
	 */
	public String getyAxis() {
		return yAxis;
	}

	/**
	 * Getter for miny y
	 * 
	 * @return
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Getter for maxy
	 * 
	 * @return
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * getter for dif
	 * 
	 * @return diff
	 */
	public int getDiff() {
		return diff;
	}

}
