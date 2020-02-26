package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;
/**
 * This class implements position in calc
 * @author af
 *
 */
public class RCPosition {
	/**
	 * row
	 */
	private int row;
	/**
	 * Column
	 */
	private int column;
	/**
	 * Public constructor
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row=row;
		this.column=column;
	}
	/**
	 * Getter for rows
	 * @return
	 */
	public int getRow() {
		return row;
	}
	/**
	 * Getter for columns
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	
}
