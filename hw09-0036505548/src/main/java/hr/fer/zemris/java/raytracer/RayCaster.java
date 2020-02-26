package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
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
 * scenes
 * 
 * @author af
 *
 */
public class RayCaster {
	/**
	 * This method stars when main program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
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

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1)))
								.sub(yAxis.scalarMultiply(y * vertical / (height - 1)));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni  gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
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
			if (distanceS > distanceS2 + 0.01) {
				continue;
			}
			// difuzija izvor formule knjiga
			double cosFi = light.getPoint().sub(lightIntersection.getPoint()).normalize()
					.scalarProduct(lightIntersection.getNormal());
			if (cosFi < 0) {
				cosFi = 0;
			}
			rgb[0] += (light.getR() * lightIntersection.getKdr() * cosFi);
			rgb[1] += (light.getG() * lightIntersection.getKdg() * cosFi);
			rgb[2] += (light.getB() * lightIntersection.getKdb() * cosFi);

			// refleksija
			// izvor formule
			// https://math.stackexchange.com/questions/13261/how-to-get-a-reflection-vector
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