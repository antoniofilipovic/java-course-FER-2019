package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

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
public class Complex {
	/**
	 * real part of complex number
	 */
	private double re;
	/**
	 * imaginary part of complex number
	 */
	private double im;
	/**
	 * complex number zero
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * complex number one
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * complex number negative number one
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * complex number imaginary one
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex number imaginary negative
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	public Complex() {
		this(0, 0);
	}

	/**
	 * Public constructor that receives two parameters and creates new complex
	 * number.
	 * 
	 * @param real      part of complex number
	 * @param imaginary part of complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * returns module of complex number
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
	}

	/**
	 * Multiplies two complex number
	 * 
	 * @param c other complex number
	 * @return
	 */
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		double newReal = this.re * c.re - this.im * c.im;
		double newImaginary = this.re * c.im + this.im * c.re;
		return new Complex(newReal, newImaginary);

	}

	/**
	 * Divides two complex numbers, this and one given in method
	 * 
	 * @param c other complex number
	 * @return new complex number
	 */
	public Complex divide(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		if (c.re == 0 && c.im == 0) {
			return new Complex(Double.NaN, Double.NaN);
		}
		double newReal = this.re * c.re + this.im * c.im;
		double newImaginary = this.im * c.re - this.re * c.im;
		double div = Math.pow(c.module(), 2);
		return new Complex(newReal / div, newImaginary / div);
	}

	/**
	 * Adds two complex number
	 * 
	 * @param c other complex number
	 * @return
	 */
	public Complex add(Complex c) {
		if (c == null) {
			throw new NullPointerException("complex number cant be null");
		}
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * subs two complex numbers
	 * 
	 * @param c complex number
	 * @return sub (this-c)
	 */
	public Complex sub(Complex c) {
		if (c == null) {
			throw new NullPointerException();
		}
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Returns this -1
	 * 
	 * @return
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Method powers complex number on given n.
	 * 
	 * @param n power
	 * @return new complex number from result of operation
	 * @throws IllegalArgumentException if n is smaller than zero
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		if (n == 0) {
			return new Complex(1.0, 0.0);
		}
		Complex output = new Complex(this.re, this.im);
		for (int i = 1; i < n; i++) {
			output = multiply(output);
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
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		List<Complex> numbers = new ArrayList<>();
		if (n == 1) {
			numbers.add(this);
			return numbers;
		}
		double angle = this.getAngle() * 180 / Math.PI;
		double r = Math.pow(this.module(), 1.0 / n);
		for (int i = 0; i < n; i++) {
			double expression = ((angle + 360 * i) / n) * Math.PI / 180;
			numbers.add(new Complex(r * Math.cos(expression), r * Math.sin(expression)));
		}
		return numbers;
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

	public static Complex parse(String s) {

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
		if (counter == 2 && realImag[1].length() == 1 && realImag[1].contains("i")) {
			realImag[1] = realImag[1] + "1";
		}
		if (counter == 2 && realImag[0].contains("i")
				|| (counter == 2 && realImag[1].indexOf("i") == realImag[1].length() - 1)
				|| (counter == 1 && realImag[0].indexOf("i") > 0 && realImag[0].length() > 1)) {
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
		return new Complex(real, imag);

	}

	/**
	 * Returns angle between zero and 2pi
	 * 
	 * @return angle
	 */
	private double getAngle() {
		if (im == 0 && re == 0) {
			throw new ArithmeticException();
		}
		if (im == 0) {
			if (re > 0) {
				return 0;
			}
			return Math.PI;
		}
		if (re == 0) {
			if (im > 0) {
				return Math.PI / 2;
			}
			return 3 * Math.PI / 2;
		}
		double angle = Math.atan(im / re);
		if (re < 0) {
			return angle + Math.PI;
		}

		if (re > 0 && im < 0) {
			return angle + 2 * Math.PI;
		}
		return angle;
	}

	@Override
	public String toString() {
		return this.re + (this.im < 0 ? "-" : "+") + "i" + Math.abs(im);

	}
}