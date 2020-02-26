package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * This class represents implementation and support for working with complex
 * numbers. It has two private variables: real - represents real part of complex
 * number imaginary - represents imaginary part of complex number
 * {@link PRECISION} represents precision on to the which two parts of complex
 * numbers can differ to.
 *
 * @author Antonio Filipovic
 *
 */

public class ComplexNumber {
	private double real = 0;
	private double imaginary = 0;
	private static final double PRECISION = 1E-6;

	/**
	 * Public constructor that receives two parameters and creates new complex
	 * number.
	 * 
	 * @param real      part of complex number
	 * @param imaginary part of complex number
	 */

	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * This static method returns Complex number just from its real part.
	 * 
	 * @param real part of complex number
	 * @return Complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}

	/**
	 * This static method returns Complex number just from its imaginary part.
	 * 
	 * @param imaginary part
	 * @return complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}

	/**
	 * This static method creates new complex number from magnitude and angle.
	 * 
	 * @param magnitude represents magnitude
	 * @param angle     represents angle
	 * @return complex number
	 * @throws IllegalArgumentException if maginitude is smaller than zero
	 */

	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real, imag;
		if (magnitude < 0) {
			throw new IllegalArgumentException();
		}
		real = magnitude * Math.cos(angle);
		imag = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imag);
	}

	/**
	 * This method parses String into complex number. E.g of good inputs:"351i",
	 * "-317i", "3.51i","351", "-317", "3.51", "-3.17" "-2.71-3.15i" "31+24i",
	 * "-1-i". It cant parse string if it contains anything else rather than numbers
	 * and "i". It cant parse string if "i" is before number or if string contains
	 * real and imag part and imag part is before real part of complex number. E.g
	 * of bad inputs "-+2.71", "--2.71", "-2.71+-3.15i ,"i351", "-i317" ,"i+1" itd
	 * 
	 * @param s which it uses
	 * @return new complex number
	 * @throws NullPointerException     if s is null
	 * @throws IllegalArgumentException if string cannot be parsed
	 */

	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException();
		}
		if (s.isBlank()) {
			throw new IllegalArgumentException();
		}
		double real = 0.0, imag = 0.0;
		s = s.replaceAll("\\s+", "");
		if (s.contains("++") || s.contains("+-") || s.contains("-+") || s.contains("--")) {
			throw new IllegalArgumentException();
		}
		String[] parts = s.split("\\+|\\-");
		String[] realImag = new String[2];
		int counter = 0;
		for (int i = 0; i < parts.length; i++) {
			if (!parts[i].isBlank()) {
				realImag[counter] = parts[i];
				counter++;
			}
		}
		if (counter == 2 && realImag[0].contains("i")
				|| (counter == 2 && realImag[1].indexOf("i") != realImag[1].length() - 1)
				|| (counter == 1 && realImag[0].indexOf("i") == 0 && realImag[0].length() > 1)) {
			throw new IllegalArgumentException();
		}
		if (counter == 2) {// (+)+ ++ (+)- +- -+ --
			String realString = realImag[0];
			String imagString = realImag[1];
			imagString = imagString.replace("i", "");
			if (imagString.isBlank()) {
				imagString += "1";
			}
			try {
				real = Double.parseDouble(realString);
				imag = Double.parseDouble(imagString);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}

			if (s.indexOf("+") < 0) {// (+)- ili --
				imag *= -1;
				if (s.indexOf("-") == 0) {// --
					real *= -1;
				}
			} else if (s.indexOf("-") >= 0 && s.indexOf("+") >= 0) { // +- -+
				if (s.indexOf("+") == 0) {
					imag *= -1;
				} else {
					real *= -1;
				}
			}
			// (+)+ ili ++ do nothing
		} else {
			if (realImag[0].contains("i")) {
				realImag[0] = realImag[0].replace("i", "");
				if (realImag[0].isBlank()) {
					realImag[0] += "1";
				}
				try {
					imag = Double.parseDouble(realImag[0]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}

				if (s.indexOf("-") == 0) {
					imag *= -1;
				}

			} else {
				try {
					real = Double.parseDouble(realImag[0]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}

				if (s.indexOf("-") == 0) {
					real *= -1;
				}
			}
		}
		return new ComplexNumber(real, imag);

	}

	/**
	 * Method returns real part of number
	 * 
	 * @return double real
	 */

	public double getReal() {
		return real;
	}

	/**
	 * Method returns imag part of number
	 * 
	 * @return imag part
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns magnitude calculated from real and imag part
	 * 
	 * @return magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Returns angle between zero and 2pi
	 * 
	 * @return angle
	 */
	public double getAngle() {
		if (imaginary == 0 && real == 0) {
			throw new ArithmeticException();
		}
		if (imaginary == 0) {
			if (real > 0) {
				return 0;
			}
			return Math.PI;
		}
		if (real == 0) {
			if (imaginary > 0) {
				return Math.PI / 2;
			}
			return 3 * Math.PI / 2;
		}
		double angle = Math.atan(imaginary / real);
		if (real < 0) {
			return angle + Math.PI;
		}

		if (real > 0 && imaginary < 0) {
			return angle + 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Method adds two number
	 * 
	 * @param c complex number which we add with
	 * @return new complex number from result of operation
	 * @throws NullPointerException if c is null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}

	/**
	 * Method subs two number
	 * 
	 * @param c complex number which we sub with
	 * @return new complex number from result of operation
	 * @throws NullPointerException if c is null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}

	/**
	 * Method muls two number
	 * 
	 * @param c complex number which we mul with
	 * @return new complex number from result of operation
	 * @throws NullPointerException if c is null
	 */

	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newReal = this.real * c.real - this.imaginary * c.imaginary;
		double newImaginary = this.real * c.imaginary + this.imaginary * c.real;
		return new ComplexNumber(newReal, newImaginary);

	}

	/**
	 * Method divs two number.Doesnt throw exception for div with zero.
	 * 
	 * @param c complex number which we div with
	 * @return new complex number from result of operation
	 * @throws NullPointerException if c is null
	 * 
	 */

	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException();
		}
		if (c.real == 0 && c.imaginary == 0) {
			return new ComplexNumber(Double.NaN, Double.NaN);
		}
		double newReal = this.real * c.real + this.imaginary * c.imaginary;
		double newImaginary = this.imaginary * c.real - this.real * c.imaginary;
		double div = Math.pow(c.getMagnitude(), 2);
		return new ComplexNumber(newReal / div, newImaginary / div);

	}

	/**
	 * Method powers complex number on given n.
	 * 
	 * @param n power
	 * @return new complex number from result of operation
	 * @throws IllegalArgumentException if n is smaller than zero
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		if (n == 0) {
			return new ComplexNumber(1.0, 0.0);
		}
		ComplexNumber output = new ComplexNumber(this.real, this.imaginary);
		for (int i = 1; i < n; i++) {
			output = mul(output);
		}
		return output;
	}

	/**
	 * Method gets all nth roots from complex number .
	 * 
	 * @param n nth roots
	 * @return array of ComplexNumber objects
	 * @throws IllegalArgumentException if n is smaller or equal to zero
	 */

	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] numbers = new ComplexNumber[n];
		if (n == 1) {
			numbers[0] = this;
			return numbers;
		}
		double angle = this.getAngle() * 180 / Math.PI;
		double r = Math.pow(this.getMagnitude(), 1.0 / n);
		for (int i = 0; i < n; i++) {
			double expression = ((angle + 360 * i) / n) * Math.PI / 180;
			numbers[i] = new ComplexNumber(r * Math.cos(expression), r * Math.sin(expression));
		}
		return numbers;
	}

	/**
	 * Returns string representation of complex number
	 * 
	 * @return String representation
	 */

	public String toString() {
		String real = this.real + "";
		String imag = "";
		if (imaginary < 0) {
			imag = imaginary + "i";
		}

		else {
			imag = "+" + imaginary + "i";
		}

		return real + imag;
	}

	/**
	 * Method calculates hash from imag and real part
	 * 
	 * @return int representation of has
	 */
	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	/**
	 * Method checks if two complex numbers are equal by comparing their real and
	 * imag parts on to the precision
	 * 
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return Math.abs(imaginary - other.imaginary) <= PRECISION && Math.abs(real - other.real) <= PRECISION;
	}

}
