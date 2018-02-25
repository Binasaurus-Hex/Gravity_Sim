package gravity_sim;

import static java.lang.Math.pow;

public class PlanetPhysics {
	
	// The scalar for this project - 
	// The difference between pixel measurements and "real" measurements
	private static final double scalar = 2 * pow(10,7);
	
	/*
	 * Scales a value from a "real" number to a modelled pixel measurement
	 * @param value The value you want to scale
	 * @return The scaled down value
	 */
	public static double scaleToPixel(double value) {
		return value / scalar;
	}
	
	/*
	 * Scales a value from a pixel measurement to a "real" number
	 * @param value The value you want to scale
	 * @return The scaled up value
	 */
	public static double scaleToReal(double value) {
		return value * scalar;
	}
	
	/*
	 * takes in the radii of two planets and returns the "combined" radius
	 * @param r1
	 * @param r2
	 * @returns combinedRadius
	 */
	public static double combinedSize(double r1,double r2){
		double area1 = Physics.circleArea(r1);
		double area2 = Physics.circleArea(r2);
		double combinedArea = area1+area2;
		double combinedRadius = Math.sqrt(combinedArea/Math.PI);
		return combinedRadius;
	}
}
