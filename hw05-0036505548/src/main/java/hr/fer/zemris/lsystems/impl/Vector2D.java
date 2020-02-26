package hr.fer.zemris.lsystems.impl;

import java.util.Objects;

/**
 * This class represents vector implementation.
 * 
 * @author Antonio Filipovic
 *
 */
public class Vector2D {
	/**
	 * This represents x component of vector
	 */
	private double x;
	/**
	 * This variable represents Y component of vector
	 */
	private double y;
	/**
	 * This is precision used in equals method
	 */
	
	private static final double PRECISION = 1E-6;
	/**
	 * This is public constructor that creates vector with x and y values
	 * 
	 * @param x value of vector
	 * @param y value of vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns x component of vector
	 * 
	 * @return x component
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns y component of vector
	 * 
	 * @return y component
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Translates vector for given offset vector
	 * 
	 * @param offset for which vector will be translated
	 */
	public void translate(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException("Vector can not be null.");
		}
		this.x = this.x + offset.getX();
		this.y = this.y + offset.getY();
	}

	public Vector2D translated(Vector2D offset) {
		if (offset == null) {
			throw new NullPointerException("Vector can not be null.");
		}
		return new Vector2D(x + offset.getX(), y + offset.getY());
	}

	/**
	 * This method rotates this vector
	 * 
	 * @param angle for which these vector is rotated
	 */
	public void rotate(double angle) {
		double xRotated = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double yRotated = this.x * Math.sin(angle) + this.y * Math.cos(angle);

		this.x = xRotated;
		this.y = yRotated;
	}

	/**
	 * This method returns new vector rotated for given angle, but this vector
	 * remains untouched
	 * 
	 * @param angle for which vector is rotated
	 * @return new Vector
	 */
	public Vector2D rotated(double angle) {
		double xRotated = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double yRotated = this.x * Math.sin(angle) + this.y * Math.cos(angle);

		return new Vector2D(xRotated, yRotated);
	}

	/**
	 * This method scales this vector for {@link scaler}
	 * 
	 * @param scaler that scales vector
	 */
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;
	}

	/**
	 * Returns new scaled vector
	 * 
	 * @param scaler for which vector is scaled
	 * @return new vector
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Returns copy of this vector
	 * 
	 * @return new vector that is copy
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
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
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		return Math.abs(x -other.x)<=PRECISION
				&& Math.abs(y - other.y)<=PRECISION;
	}
	

}
