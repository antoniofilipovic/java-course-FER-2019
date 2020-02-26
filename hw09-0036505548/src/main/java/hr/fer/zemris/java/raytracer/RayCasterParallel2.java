package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents a simplification of ray-tracer for rendering of 3D
 * scenes. It uses paralelization for color calculations . It also rotates image
 * so scene is must be redrawn
 * 
 * @author af
 *
 */
public class RayCasterParallel2 {
	/**
	 * This method stars when main program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	/**
	 * This method returns ray traces animator
	 * 
	 * @return
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}

	/**
	 * This method creates and returns {@link IRayTracerProducer} which produces and
	 * calculates all intersections between rays and objects and colors objects
	 * depending on their position in system.
	 * 
	 * @return {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean bool) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D xAxis = viewUp.vectorProduct(eye.sub(view)).normalize();
				Point3D yAxis = eye.sub(view).vectorProduct(xAxis).normalize();

				//Point3D zAxis = yAxis.vectorProduct(xAxis);

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene2();

				// baa

				ForkJoinPool pool = new ForkJoinPool();
				ImportantData data = new ImportantData(scene, xAxis, yAxis, width, height, screenCorner, horizontal,
						vertical, eye, red, green, blue);
				pool.invoke(new RayCasterJob(data, 0, height));// red green blue u data , offset rucno racunat
				pool.shutdown();

				// baa

				System.out.println("Izračuni  gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

	/**
	 * This class represents implementation of job for every thread that will be
	 * done
	 * 
	 * @author af
	 *
	 */
	private static class ImportantData {
		/**
		 * Scene created
		 */
		private Scene scene;
		/**
		 * x axis
		 */
		private Point3D xAxis;
		/**
		 * y axis
		 */
		private Point3D yAxis;
		/**
		 * Width
		 */
		private int width;
		/**
		 * Height
		 */
		private int height;
		/**
		 * corner of screen
		 */
		private Point3D screenCorner;
		/**
		 * length in horizontal
		 */
		private double horizontal;
		/**
		 * Length in vertical
		 */
		private double vertical;
		/**
		 * position of eye
		 */
		private Point3D eye;
		/**
		 * Red color for every pixel
		 */
		private short[] red;
		/**
		 * green color for every pixel
		 */
		private short[] green;
		/**
		 * blue color for every pixel
		 */
		private short[] blue;

		/**
		 * Public constructor for important data
		 * 
		 * @param scene        created
		 * @param xAxis        x axis
		 * @param yAxis        y axis
		 * @param width
		 * @param height
		 * @param screenCorner corner of screen
		 * @param horizontal   horizontal length
		 * @param vertical     vertical length
		 * @param eye          positon of eye
		 * @param red          red color for every pixel
		 * @param green        green color for every pixel
		 * @param blue         blue color for every pixel
		 */

		public ImportantData(Scene scene, Point3D xAxis, Point3D yAxis, int width, int height, Point3D screenCorner,
				double horizontal, double vertical, Point3D eye, short[] red, short[] green, short[] blue) {
			super();
			this.scene = scene;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.width = width;
			this.height = height;
			this.screenCorner = screenCorner;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

	}

	/**
	 * This class represents implementation of job for every thread that will be
	 * done
	 * 
	 * @author af
	 *
	 */
	private static class RayCasterJob extends RecursiveAction {
		private static final long serialVersionUID = 1L;

		/**
		 * Minimum difference between ymin and ymax that will be calculated
		 */
		static final int treshold = 16;
		/**
		 * Important data
		 */
		private ImportantData data;
		/**
		 * min y
		 */
		private int yMin;
		/**
		 * max y
		 */
		private int yMax;

		/**
		 * This is public constructor
		 * 
		 * @param data all important data
		 * @param yMin ymin
		 * @param yMax ymax
		 */

		public RayCasterJob(ImportantData data, int yMin, int yMax) {
			super();
			this.data = data;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(new RayCasterJob(data, yMin, yMin + (yMax - yMin) / 2),

					new RayCasterJob(data, yMin + (yMax - yMin) / 2 + 1, yMax));

		}

		/**
		 * This method calculates directly for every pixel in interval color
		 */
		private void computeDirect() {
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < data.width; x++) {
					if (yMax == data.height)
						continue;
					Point3D screenPoint = data.screenCorner
							.add(data.xAxis.scalarMultiply(x * data.horizontal / (data.width - 1)))
							.sub(data.yAxis.scalarMultiply(y * data.vertical / (data.height - 1)));
					Ray ray = Ray.fromPoints(data.eye, screenPoint);
					short[] rgb = new short[3];
					tracer(data.scene, ray, rgb);
					data.red[y * data.width + x] = rgb[0] > 255 ? 255 : rgb[0];// ofset mozemo rucno racunat
					data.green[y * data.width + x] = rgb[1] > 255 ? 255 : rgb[1];
					data.blue[y * data.width + x] = rgb[2] > 255 ? 255 : rgb[2];
				}
			}

		}
	}

	/**
	 * This method colors objects depending on their position in scene. If there is
	 * no intersection than rgb=0,0,0 but if if there is intersection but no path
	 * from light to object then there is ambient light rgb=15,15,15 , in other case
	 * color is calculated
	 * 
	 * @param scene that was created. It contains objects and light sources
	 * @param ray   for which intersection will be calculated and color given
	 *              depending on light sources
	 * @param rgb   red green and blue color
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}

		determineColorFor(scene, ray, rgb, closest);

	}

	/**
	 * This method determines color for pixel depending on its position in scene and
	 * path to light sources, if light source can reach pixel then color is
	 * calculated depending on difusion and reflection.
	 * 
	 * @param scene        that was created
	 * @param ray          that goes to object through pixel for which color is
	 *                     calculated
	 * @param rgb          intensity of colors
	 * @param intersection bewteen ray and closest object
	 */
	private static void determineColorFor(Scene scene, Ray ray, short[] rgb, RayIntersection intersection) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		List<LightSource> lights = scene.getLights();
		for (LightSource light : lights) {
			Ray rayLight = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			RayIntersection lightIntersection = findClosestIntersection(scene, rayLight);
			if (lightIntersection == null) {
				continue;
			}

			double distanceS = light.getPoint().sub(intersection.getPoint()).norm();
			double distanceS2 = light.getPoint().sub(lightIntersection.getPoint()).norm();
			if (distanceS > distanceS2 + 0.1) {
				continue;
			}
			// difuzija
			double cosFi = light.getPoint().sub(lightIntersection.getPoint()).normalize()
					.scalarProduct(lightIntersection.getNormal());
			if (cosFi < 0) {
				cosFi = 0;
			}
			rgb[0] += (light.getR() * lightIntersection.getKdr() * cosFi);
			rgb[1] += (light.getG() * lightIntersection.getKdg() * cosFi);
			rgb[2] += (light.getB() * lightIntersection.getKdb() * cosFi);

			// refleksija
			Point3D v = ray.start.sub(lightIntersection.getPoint());
			Point3D norm = lightIntersection.getNormal();

			Point3D d = lightIntersection.getPoint().sub(light.getPoint());
			Point3D r = d.sub(norm.scalarMultiply(2 * d.scalarProduct(norm)));

			cosFi = r.normalize().scalarProduct(v.normalize());

			if (cosFi > 0) {
				double cosFiN = Math.pow(cosFi, intersection.getKrn());

				rgb[0] += lightIntersection.getKrr() * cosFiN * light.getR();
				rgb[1] += lightIntersection.getKrg() * cosFiN * light.getG();
				rgb[2] += lightIntersection.getKrb() * cosFiN * light.getB();
			}

		}

	}

	/**
	 * This method calculates closest intersection between ray and object
	 * 
	 * @param scene that was created
	 * @param ray
	 * @return intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		double minDistance = Double.MAX_VALUE;
		RayIntersection closestIntersection = null;
		for (GraphicalObject object : objects) {

			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if (intersection == null) {
				continue;
			}
			double distance = intersection.getDistance();
			if (distance < minDistance) {
				minDistance = distance;
				closestIntersection = intersection;
			}
		}
		return closestIntersection;
	}
}