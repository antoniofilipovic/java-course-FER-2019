package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

/**
 * This class represents barchart component. It extends Jcomponent and creates
 * bar chart
 * 
 * @author af
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to barchatr
	 */
	private BarChart barChart;
	
	private static final int xRight=10;
	private static final int xLeft=10;
	private static int xBegin=50;
	private static int xAxisLength;
	private static final int yFromUp=10;
	private static final int yFromBottom=40;
	private static int yAxisLength;
	private static final int yAxisNumberConst=15;
	private static int yFontConst;
	private static final int arrowConst=10;

	/**
	 * Public construcotr for barchart
	 * 
	 * @param barChart
	 */

	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g2d) {
		Graphics2D g = (Graphics2D) g2d;
		FontMetrics fm = g.getFontMetrics();
		Font prevFont = g.getFont();
		
		int height = getHeight();
		int width = getWidth();

		int yString=fm.stringWidth(findYMax()+"");
		fm.getAscent();
		
		xBegin = 50+yString+fm.getAscent()+xLeft;
		xAxisLength = width - xBegin - xRight;
		
		yAxisLength = height - yFromBottom-yFromUp;
		yFontConst = 20;
		
		List<Double> heightValues = new ArrayList<>();

		g.setStroke(new BasicStroke(3));

		g.setColor(Color.GRAY);
		// xaxis and yaxis
		g.drawLine(xBegin, height - yFromBottom, width - xRight, height - yFromBottom);
		g.drawLine(xBegin, height - yFromBottom, xBegin, 0);

		int yMin = barChart.getMinY();
		int yMax = barChart.getMaxY();
		int yDiff = barChart.getDiff();
		double heightIncrease = (height - yFromBottom - yFromUp)*1.0 / ((yMax - yMin) / yDiff);
		//System.out.println(heightIncrease+","+(height-yFromBottom-yFromUp));

		for (int i = 0; i <= (yMax - yMin); i += yDiff) {
			heightValues.add(height - yFromBottom - heightIncrease * i / yDiff);
		}

		g.setStroke(new BasicStroke(1));

		

		Font numberFont = new Font(prevFont.getName(), Font.BOLD, prevFont.getSize() + 4);
		g.setFont(numberFont);
		// horizontalne linije
		for (int i = 0; i <= yMax - yMin; i += yDiff) {
			g.setColor(Color.GRAY);
			g2d.drawString(String.valueOf(i + yMin) , xBegin - fm.stringWidth(String.valueOf(i+yMin)) - yAxisNumberConst,
					(int)(heightValues.get(i / yDiff) + fm.getAscent() / 2));
			g.drawLine(xBegin - 6, (int)(heightValues.get(i / yDiff)+0), xBegin,(int)( heightValues.get(i / yDiff)+0));
			if (i == yMin)
				continue;// za prvi ne crtamo ove crtice sa strane
			g.setColor(Color.ORANGE);
			g.drawLine(xBegin,(int) (heightValues.get(i / yDiff)+0), width,(int) (heightValues.get(i / yDiff)+0));// crtica sa
																								// strane

		}
		
		int numberOfValues = findXMax();

		int widthIncrease = xAxisLength / numberOfValues;
		// vertikalne linije
		for (int i = 1; i <= numberOfValues; i++) {
			g.setColor(Color.ORANGE);
			g.drawLine(xBegin + widthIncrease * i, height - yFromBottom, xBegin + widthIncrease * i, 0);
			g.setColor(Color.GRAY);
			g.drawLine(xBegin + widthIncrease * i, height - yFromBottom + 5, xBegin + widthIncrease * i,
					height - yFromBottom);

		}
		g.setColor(Color.ORANGE);
		List<XYValue> values = barChart.getValues();

		// kvadrati
		for (int i = 0; i < values.size(); i++) {
			g.setColor(Color.ORANGE);
			XYValue value = values.get(i);
			g.fillRect(xBegin + widthIncrease * (value.getX() - 1),(int) (heightValues.get((value.getY() - yMin) / yDiff)+0),
					widthIncrease - 1, (int)(heightIncrease * (value.getY() - yMin) / yDiff));
			int xTextBegin = (xBegin + widthIncrease * (value.getX() - 1) + (widthIncrease - 1) / 2)
					+ fm.stringWidth(String.valueOf(i + 1)) / 2;
			int yText = height - yFromBottom + (fm.getHeight() - fm.getDescent()) + yFontConst / 3;
			g.setColor(Color.GRAY);
			g.drawString((value.getX()) + "", xTextBegin, yText);

		}
		g.setFont(prevFont);
		int xTextBegin = xBegin + xAxisLength / 2 - fm.stringWidth(barChart.getxAxis()) / 2;
		int yText = height - yFromBottom + (fm.getHeight() - fm.getDescent()) + yFontConst;
		g.drawString(barChart.getxAxis(), xTextBegin, yText);

		Font textFont = new Font(prevFont.getName(), Font.PLAIN, prevFont.getSize() + 5);
		g.setFont(textFont);

		AffineTransform defaultAt = g.getTransform();
		// rotates the coordinate by 90 degree counterclockwise
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g.transform(at);
		
		g.drawString(barChart.getyAxis(), -(this.getHeight()/2+fm.stringWidth(barChart.getyAxis())/2), fm.getAscent()+xLeft);
	
		
		g.setTransform(defaultAt);
		drawArrowLine(g, xBegin, yFromUp, xBegin, 0, arrowConst, arrowConst);
		drawArrowLine(g, width - xRight, height - yFromBottom, width, height - yFromBottom, arrowConst, arrowConst);

	}

	/**
	 * This method finds max y value for all values
	 * 
	 * @return
	 */
	private int findXMax() {
		List<XYValue> values = barChart.getValues();
		int max = values.get(0).getX();
		for (XYValue v : values) {
			if (v.getX() > max) {
				max = v.getX();
			}
		}
		return max;
	}
	
	private int findYMax() {
		List<XYValue> values = barChart.getValues();
		int max = values.get(0).getY();
		for (XYValue v : values) {
			if (v.getY() > max) {
				max = v.getY();
			}
		}
		return max;
	}

	/**
	 * This method draws arrow head
	 * 
	 * @param g  graphics
	 * @param x1 first x
	 * @param y1 first y
	 * @param x2 second x
	 * @param y2 second y
	 * @param d  depth
	 * @param h  height
	 */
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - d, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);
	}

}
