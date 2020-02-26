package coloring.algorithms;

import java.util.Objects;

/**
 * This class represents pixels
 * 
 * @author af
 *
 */
public class Pixel {
	/**
	 * x coordinate of pixel
	 */
	private int x;
	/**
	 * y coordinate of pixel
	 */
	private int y;

	/**
	 * Public constructor for pixel
	 * 
	 * @param x coordinate of pixel
	 * @param y coordinate of pixel
	 */
	public Pixel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x coordinate
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x coordinate
	 * 
	 * @param x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for y coordinate
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for y coordinate
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}

}
