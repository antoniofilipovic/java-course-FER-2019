package hr.fer.zemris.math;

import java.util.Objects;
/**
 * This class represents implementation of vector in 3d
 * @author af
 *
 */
public class Vector3 {
	/**
	 * This represents x component of vector
	 */
	private double x;
	/**
	 * This variable represents Y component of vector
	 */
	private double y;
	/**
	 * This variable represents Z component of vector
	 */

	private double z;
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
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	 * Returns z component of vector
	 * 
	 * @return z component
	 */
	public double getZ() {
		return this.z;
	}

	/**
	 * Norm of vector
	 * 
	 * @return norm of vector
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	/**
	 * Normalized vector
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * This method returns new vector that represents sum of this vector and vector
	 * given in method
	 * 
	 * @param other vector that will be summed
	 * @return new Vector3
	 */
	public Vector3 add(Vector3 other) {
		if (other == null) {
			throw new NullPointerException("Vector cannot be null.");
		}
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}
	/**
	 * This method subs two vectors
	 * @param other vector for sub method
	 * @return new vector, result
	 */
	public Vector3 sub(Vector3 other) {
		if (other == null) {
			throw new NullPointerException("Vector cannot be null.");
		}
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	/**
	 * Scalar product of two vectors
	 * 
	 * @param other vector
	 * @return dot of two vectors
	 */
	public double dot(Vector3 other) {
		if (other == null) {
			throw new NullPointerException("Vector cannot be null.");
		}
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	/**
	 * Cross product between two vectors
	 * 
	 * @param other vector
	 * @return cross product
	 */
	public Vector3 cross(Vector3 other) {
		if (other == null) {
			throw new NullPointerException("Vector cannot be null.");
		}
		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Method scales this vector with coeficient s
	 * 
	 * @param s scale
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Returns cosines angle between two vectors
	 * 
	 * @param other vector
	 * @return cosines angle
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (norm() * other.norm());
	}

	/**
	 * Returns array of double values from vector
	 * 
	 * @return array
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		return String.format("(%6f, %6f, %6f)", x, y, z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector3))
			return false;
		Vector3 other = (Vector3) obj;
		return Math.abs(x - other.x) <= PRECISION && Math.abs(y - other.y) <= PRECISION
				&& Math.abs(z - other.z) <= PRECISION;
	}
}
