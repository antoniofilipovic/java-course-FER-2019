package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents Sphere. It extends GraphicalObject. In sphere point of
 * intersection is calculated of ray and sphere is calculated.
 * 
 * @author af
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Center of sphere
	 */
	private Point3D center;
	/**
	 * Radius
	 */
	private double radius;
	/**
	 * coef of difusion of red color
	 */
	private double kdr;
	/**
	 * coef of difusion of green color
	 */
	private double kdg;
	/**
	 * coef of difusion of blue color
	 */
	private double kdb;
	/**
	 * Coef of reflection of red color
	 */
	private double krr;
	/**
	 * Coef of reflection of green color
	 */
	private double krg;
	/**
	 * Coef of reflection of blue color
	 */
	private double krb;
	/**
	 * shininess factor (n)
	 */
	private double krn;

	/**
	 * Public constructor for sphere
	 * 
	 * @param center center of sphere
	 * @param radius radius of sphere
	 * @param kdr    coef of difusion of red color
	 * @param kdg    coef od difusion of green color
	 * @param kdb    coef of difusioon of blue color
	 * @param krr    Coef of reflection of red color
	 * @param krg    Coef of reflection of green color
	 * @param krb    Coef of reflection of blue color
	 * @param krns   hininess factor (n)
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * This method returns closest intersection with given ray if one exists,
	 * otherwise it returns null.
	 * 
	 * @param ray ray for which intersection will be found
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D direction = ray.direction;
		Point3D start = ray.start;
		double distance = intersect(direction, start);
		if (distance < 0) {
			return null;
		}
		Point3D point = start.add(direction.scalarMultiply(distance));
		boolean outer = point.sub(start).norm() > radius;

		return new RayIntersection(point, distance, outer) {

			@Override
			public Point3D getNormal() {
				return point.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};

	}

	// izvor formule
	// https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection
	/**
	 * This method calculates closest intersection, if one exists between ray and
	 * sphere. It returns distance between start of ray in direction of ray to sphere. If distance is negative(that means no intersection)
	 * it returns -1
	 * 
	 * @param ray
	 * @param direction direction of ray
	 * @param start     start of ray
	 * @return distance if intersection exists, otherwise -1
	 */
	private double intersect(Point3D direction, Point3D start) {
		double t0, t1; // solutions for t if the ray intersects

		Point3D L = start.sub(center);
		double a = direction.scalarProduct(direction);
		double b = 2 * direction.scalarProduct(L);
		double c = L.scalarProduct(L) - radius * radius;

		double discriminant = b * b - 4 * a * c;
		if (discriminant < 0)
			return -1;
		else if (discriminant == 0) {
			t0 = t1 = -0.5 * b / a;
		} else {
			double q = (b > 0) ? -0.5 * (b + Math.sqrt(discriminant)) : -0.5 * (b - Math.sqrt(discriminant));
			t0 = q / a;
			t1 = c / q;
		}
		if (t0 > t1) {
			double t = t0;
			t0 = t1;
			t1 = t;
		}

		if (t0 < 0) {
			t0 = t1; // if t0 is negative, let's use t1 instead
			if (t0 < 0)
				return -1; // both t0 and t1 are negative
		}

		return t0;

		// return true;
	}

	

}