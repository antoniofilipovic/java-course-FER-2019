package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class represents layout defined speciall for calculator, created in
 * another package.
 * 
 * @author af
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Max rows
	 */
	private final int MAX_ROWS = 5;
	/**
	 * MAx columns
	 */
	private final int MAX_COLUMNS = 7;
	/**
	 * Min rows and cols
	 */
	private final int MIN_ROWS_COLUMNS = 1;
	/**
	 * gap
	 */
	private int gap;
	/**
	 * Map that holds components, if there isnt componetn value is set to null
	 */
	private Map<RCPosition, Component> map;

	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new RuntimeException("Interspace cant be negative.");
		}
		this.gap = gap;
		map = new LinkedHashMap<>();
		fillMap();
	}

	public CalcLayout() {
		this(0);
	}

	/**
	 * This method fills map with possible positions
	 */
	private void fillMap() {
		for (int i = 1; i <= MAX_ROWS; i++) {
			for (int j = 1; j <= MAX_COLUMNS; j++) {
				if (i == 1 && j > 1) {
					continue;
				} else {
					map.put(new RCPosition(i, j), null);
				}
			}
		}

	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (Component c : map.values()) {
			if (c == comp) {
				map.values().remove(c);
				System.out.println("Component removed.");
			}
		}

	}

	@Override
	public void layoutContainer(Container parent) {
		Set<Entry<RCPosition, Component>> set = map.entrySet();
		double  x, y, width, height;
		x = (int) getLayoutAlignmentX(parent);
		y = (int) getLayoutAlignmentY(parent);
		Insets insets = parent.getInsets();
		
		 width = (parent.getWidth() - 6 * gap-insets.left-insets.right) / 7;
		height = (parent.getHeight() - 4 * gap-insets.bottom-insets.top) / 5;
		
		double restWidth = parent.getWidth() - 6 * gap - 7 * width - 3 * 1-insets.right-insets.left;
		double restHeight = parent.getHeight() - 4 * gap - 5 * height - 2 * 1-insets.bottom-insets.top;
		for (Entry<RCPosition, Component> e : set) {
			RCPosition rcposition = e.getKey();
			int row = rcposition.getRow();
			int column = rcposition.getColumn();

			int addWidth = (column - 1) % 2 == 0 ? 1 : 0;
			int addHeight = (row - 1) % 2 == 0 ? 1 : 0;
			Component c = e.getValue();
			if (c == null) {
				continue;
			}

			if (row == 1 && column == 1) {
				c.setBounds((int)(x + ((row - 1) + (row - 1) * gap) * width+insets.left),(int)( y + ((column - 1) + (column - 1) * gap) * height+insets.top),
						(int)(width * 5 + addWidth * 2 + gap * 4), (int)height);
				continue;
			}

			c.setBounds((int)(x + (column - 1) * width + gap * (column - 1)+insets.left) ,(int)(y + (row - 1) * height + gap * (row - 1)+insets.top),
					(int)(width + addWidth + (column == MAX_COLUMNS ? restWidth : 0)),
					(int)(height + addHeight + (row == MAX_ROWS ? restHeight : 0)));

		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints == null) {
			throw new UnsupportedOperationException("Constraint can't be null.");
		}
		if (constraints instanceof String) {
			String position = (String) constraints;
			String[] parts = position.split(",");
			try {
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);
				constraints = new RCPosition(row, column);
			} catch (NumberFormatException e) {
				throw new CalcLayoutException(
						"Wrong coordinates in string. Coordinates must be integer value, splited with \",\".");
			}

		}

		if (constraints instanceof RCPosition) {
			RCPosition rcposition = (RCPosition) constraints;
			if (rcposition.getRow() < MIN_ROWS_COLUMNS || rcposition.getRow() > MAX_ROWS
					|| rcposition.getColumn() < MIN_ROWS_COLUMNS || rcposition.getColumn() > MAX_COLUMNS) {
				throw new CalcLayoutException(
						"Row must be set between 1 and 5 and column must be set between 1 and 7.");
			}
			if(rcposition.getRow()==1 && !(rcposition.getColumn()==1 || rcposition.getColumn()==6 || rcposition.getColumn()==7) ) {
				throw new CalcLayoutException(
						"Column for row 1 must be set either to 1, 6 or 7.");
			}
			Component c = map.get(rcposition);
			if (c != null) {
				throw new CalcLayoutException("There is already component on that position.");
			}
			map.put(rcposition, comp);// provjeriti može li se dodati null kao komponenta

		}

	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return getDimension(parent, "maximum");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent, "minimum");
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent, "preferred");

	}

	/**
	 * This method isolates same work for three different methods that call her
	 * 
	 * @param parent container
	 * @param type   can be maximum,minimum or preferred
	 * @return
	 */
	private Dimension getDimension(Container parent, String type) {
		Dimension dim = new Dimension(0, 0);

		Set<Entry<RCPosition, Component>> set = map.entrySet();
		int height = 0;
		int width = 0;
		for (Entry<RCPosition, Component> e : set) {
			Component c = e.getValue();
			if (c == null) {
				continue;
			}
			Dimension d;

			d = getSize(c, type);

			if (d == null) {
				continue;
			}
			RCPosition rcposition = e.getKey();
			int currentHeight = d.height;
			int currentWidth = d.width;
			if (rcposition.getRow() == 1 && rcposition.getColumn() == 1) {
				currentWidth = (currentWidth - 4 * gap) / 5;
			}

			if (currentHeight > height) {
				height = currentHeight;
			}
			if (currentWidth > width) {
				width = currentWidth;
			}

		}

		Insets insets = parent.getInsets();

		dim.width += insets.left + insets.right + width * 7 + gap * 6;
		dim.height += insets.top + insets.bottom + height * 5 + gap * 4;

		return dim;
	}

	/**
	 * This method returns dimensions depeding on type
	 * 
	 * @param c    component for which it returns size
	 * @param type can be maximum,minimum or preferred
	 * @return dimension
	 */
	private Dimension getSize(Component c, String type) {
		switch (type) {
		case "preferred":
			return c.getPreferredSize();
		case "minimum":
			return c.getMinimumSize();
		case "maximum":
			return c.getMaximumSize();
		default:
			throw new RuntimeException("baa");
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

}
