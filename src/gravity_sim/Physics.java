package gravity_sim;

import static java.lang.Math.*;

public class Physics {
	
	// Newton's Gravitational Constant
	private static final double G = 6.67 * pow(10,-11);
	
	/*
	 * Get the gravitational force between two objects
	 * F = (G * m1 * m2) / (r * r), with r being the distance between the two objects
	 * @param M The first mass
	 * @param m The second mass
	 * @param r The distance between the center point of the two masses 
	 * @return The gravitational force between the objects
	 */
	public static double force(double M, double m, double r){
		return (G * M * m) / pow(r, 2);
	}

	/*
	 * Get the distance between two points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return The distance between the two points
	 */
	public static double distance(double x1, double y1, double x2, double y2){
		double dx = x2 - x1;
		double dy = y2 - y1;
		return sqrt((dx * dx) + (dy * dy));
	}
	
	/*
	 * Gets the angle between the two points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return The angle between the two points
	 */
	public static double angle(double x1, double y1, double x2, double y2){
		double dx = abs(x2 - x1);
		double dy = abs(y2 - y1);
		return Math.atan(dy / dx);
		
	}
	
	/*
	 * Gives you the mass of a spherical object, given its density and radius
	 * @param density
	 * @param radius
	 * @return The mass of the object
	 */
	public static double mass(double density, double radius){
		double volume = (4 / 3) * Math.PI * pow(radius, 3);
		return density * volume;
	}
	
	/*
	 * checks if two circles are intersecting
	 * returns true if they are intersecting
	 */
	public static boolean intersectCircle(double x1,double y1,double r1,double x2,double y2,double r2){
		return distance(x1, y1, x2, y2) < r1 + r2;
	}
	
	public static double[] midpoint(double x1,double y1,double x2,double y2){
		double midx=(x2+x1)/2;
		double midy=(y2+y1)/2;
		double midPos[] ={midx,midy};
		return midPos;
	}
}


