package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class represents fractal producer. It receives complex polynomial number
 * and for each pixel on image it generates iterations until it reaches a
 * predefined number of iterations (for example 16) or until module |zn+1-zn|
 * becomes adequately small (for example, convergence threshold 1E-3). Once
 * stopped, it finds the closest function root for final point zn, and color the
 * point c based on index of that root what is closest root. It uses
 * parallelization to speed up the rendering
 * 
 * @author af
 *
 */
public class FractalProducer implements IFractalProducer {
	/**
	 * rooted polynomial
	 */
	private ComplexRootedPolynomial complexRootedPolynomial;
	/**
	 * Number of available processors
	 */
	private int numberOfAvailableProcessors;
	/**
	 * Number of tracks
	 */
	private int numberOfTracks;

	private ExecutorService pool;

	/**
	 * Public constructor for fractal producer
	 * 
	 * @param complexRootedPolynomial complex polynomial
	 */
	public FractalProducer(ComplexRootedPolynomial complexRootedPolynomial) {
		this.complexRootedPolynomial = complexRootedPolynomial;
		numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();
		numberOfTracks = 8 * numberOfAvailableProcessors;
		pool = Executors.newFixedThreadPool(numberOfAvailableProcessors, new DaemonicThreadFactory());

	}

	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer,AtomicBoolean bool) {
		System.out.println("Starting calculations...");
		int m = 16 * 16 * 16;
		short[] data = new short[width * height];
		int brojYPoTraci = height / numberOfTracks;
	
		
		List<Future<Void>> rezultati = new ArrayList<>();

		for (int i = 0; i < numberOfTracks; i++) {
			int yMin = i * brojYPoTraci;
			int yMax = (i + 1) * brojYPoTraci - 1;
			if (i == numberOfTracks - 1) {
				yMax = height - 1;
			}
			CalculationJob posao = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
					complexRootedPolynomial);
			rezultati.add(pool.submit(posao));
		}
		for (Future<Void> posao : rezultati) {

			try {
				posao.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		//pool.shutdown();

		System.out.println("Calculations done. Informing observer, -> GUI!");
		observer.acceptResult(data, (short) (complexRootedPolynomial.toComplexPolynom().order() + 1), requestNo);
	}

	/**
	 * This class is helper class to speed things up with calculations
	 * 
	 * @author af
	 *
	 */
	public static class CalculationJob implements Callable<Void> {
		/**
		 * Minimum real
		 */
		private double reMin;
		/**
		 * Max real
		 */
		private double reMax;
		/**
		 * imaginary min
		 */
		private double imMin;
		/**
		 * Imaginary max
		 */
		private double imMax;
		/**
		 * Width
		 */
		private int width;
		/**
		 * Height
		 */
		private int height;
		/**
		 * ymin
		 */
		private int yMin;
		/**
		 * Ymax
		 */
		private int yMax;
		/**
		 * number of pixels in 3d
		 */
		private int m;
		/**
		 * data
		 */
		private short[] data;
		/**
		 * convergence
		 */
		private double convergenceTreshold = 0.001;
		/**
		 * root
		 */
		private double rootTreshold = 0.002;
		/**
		 * Complex polynomial
		 */
		private ComplexPolynomial complexPolynomial;
		/**
		 * Complex rooted polynomial
		 */
		private ComplexRootedPolynomial complexRootedPolynomial;
		/**
		 * Derived complex number
		 */
		private ComplexPolynomial derived;

		/**
		 * Public constructor for calculation job
		 * 
		 * @param reMin                   re min
		 * @param reMax                   real max
		 * @param imMin                   imag min
		 * @param imMax                   imag max
		 * @param width                   widht
		 * @param height                  height
		 * @param yMin                    y min
		 * @param yMax                    ymax
		 * @param m                       m
		 * @param data                    data
		 * @param complexRootedPolynomial polynomial for which we calculate
		 */
		public CalculationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, ComplexRootedPolynomial complexRootedPolynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.complexRootedPolynomial = complexRootedPolynomial;
			this.complexPolynomial = complexRootedPolynomial.toComplexPolynom();
			derived = complexPolynomial.derive();
		}

		@Override
		public Void call() {
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					double module;
					int iter = 0;
					do {
						Complex numerator = complexPolynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						Complex znold = zn.sub(fraction);
						module = znold.sub(zn).module();
						zn = znold;
						iter++;
					} while (module > convergenceTreshold && iter < m);
					int index = complexRootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
					data[y * width + x] = index == -1 ? 0 : (short) (index);
				}
			}

			return null;
		}

	}
}
