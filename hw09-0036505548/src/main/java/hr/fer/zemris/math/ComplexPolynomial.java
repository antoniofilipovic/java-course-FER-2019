package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents complex polynomial form
 * 
 * @author af
 *
 */
public class ComplexPolynomial {
	private Complex[] polynoms;

	/**
	 * Public constructor for ComplexPolynomial
	 * 
	 * @param factors
	 */
	public ComplexPolynomial(Complex... factors) {
		polynoms = factors;
		for (int i = 0; i < factors.length; i++) {
			Objects.requireNonNull(factors[i], "Complex factor cant be null.");
		}
	}

	/**
	 * returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * 
	 * @return order of polynom
	 */
	public short order() {
		return (short) (polynoms.length - 1);
	}

	/**
	 * computes a new polynomial this*p
	 * 
	 * @param p complexpolynomial
	 * @return new complexpolynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {

		int firstOrder = this.order();
		int secondOrder = p.order();
		int order = firstOrder + secondOrder + 1;
		Complex[] factors = new Complex[firstOrder + secondOrder + 1];

		for (int i = 0; i < order; i++) {
			factors[i] = Complex.ZERO;
		}

		for (int i = 0; i < polynoms.length; i++) {
			for (int j = 0; j < p.polynoms.length; j++) {
				factors[i + j] = factors[i + j].add(polynoms[i].multiply(p.polynoms[j]));
			}
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * computes first derivative of this polynomial; for example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return derivative
	 */
	public ComplexPolynomial derive() {
		int order = this.order();
		Complex[] factors = new Complex[order];
		for (int i = 0; i < order; i++) {
			factors[i] = Complex.ZERO;
		}
		for (int i = 0; i < order; i++) {
			factors[i] = polynoms[i + 1].multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(factors);
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z parametar for which value will be calculated
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;

		for (int i = 0; i < polynoms.length; i++) {
			result = result.add(z.power(i).multiply(polynoms[i]));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int order = this.order();
		for (int i = order; i >= 0; i--) {
			sb.append("(" + polynoms[i] + ")*").append(i == 0 ? "" : "z^" + i).append("+");
		}
		sb.setLength(sb.length() - 2);
		return sb.toString();

	}
}
