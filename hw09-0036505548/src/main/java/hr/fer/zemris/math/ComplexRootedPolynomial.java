package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents complex polynomial number that takes list of roots in
 * {@link Complex} form
 * 
 * @author af
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * List of roots
	 */
	private List<Complex> roots;

	/**
	 * Public constructor of ComplexRootedPolynomial. It takes constant and all
	 * roots in complex format
	 * 
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = new ArrayList<>();
		for (Complex c : roots) {
			Objects.requireNonNull(c, "Neither root can be null.");
			this.roots.add(c);
		}
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z point z
	 * @return value
	 */
	public Complex apply(Complex z) {
		Complex result = roots.get(0);
		for (int i = 1; i < roots.size(); i++) {
			result = result.multiply(z.sub(roots.get(i)));
		}
		return result;
	}

	/**
	 * converts this representation to ComplexPolynomial type
	 * 
	 * @return
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(roots.get(0));

		for (int i = 1; i < roots.size(); i++) {
			polynomial = polynomial.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
		}

		return polynomial;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + roots.get(0) + ")");
		for (int i = 1; i < roots.size(); i++) {
			sb.append("*(z-(" + roots.get(i) + "))");
		}
		return sb.toString();
	}

	/**
	 * finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1 first root has index 0, second
	 * index 1, etc
	 * 
	 * @param z        complex number for which it calculates distance to root
	 * @param treshold
	 * @return index of root
	 */

	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double closestDistance = treshold;

		for (int i = 1; i < roots.size(); i++) {
			double distance = z.sub(roots.get(i)).module();
			if (distance <= closestDistance) {
				index = i;
				closestDistance = distance;
			}
		}

		return index;
	}
}
