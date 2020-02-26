package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents implementation of one kind of fractal images: fractals
 * derived from Newton-Raphson iteration.
 * 
 * @author af
 *
 */
public class Newton {
	private static final String GREETING_MESSAGE = "Welcome to Newton-Raphson iteration-based fractal viewer.\r\n"
			+ "Please enter at least two roots, one root per line. Enter 'done' when done.";

	public static void main(String[] args) {
		System.out.println(GREETING_MESSAGE);
		int i = 0;
		List<Complex> roots = new ArrayList<>();
		roots.add(Complex.ONE);
		while (true) {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			output(++i);
			String s = sc.nextLine();
			if (s.equals("done")) {
				sc.close();
				if (i <= 2) {
					System.out.println("You haven't entered at least two roots");
					System.exit(-1);
				}
				break;
			}
			try {
				Complex c = Complex.parse(s);
				roots.add(c);
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println("Wrong input for complex number.");
				System.exit(-1);
			}

		}
		ComplexRootedPolynomial rooted = new ComplexRootedPolynomial(roots.toArray(new Complex[roots.size()]));
	
		FractalViewer.show(new FractalProducer(rooted));
	}

	/**
	 * Output to console
	 * 
	 * @param i
	 */
	private static void output(int i) {
		System.out.printf("Root " + i + "> ");

	}
}
