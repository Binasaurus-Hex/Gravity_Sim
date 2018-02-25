package gravity_sim;

import static java.lang.Math.pow;

public class PlanetPhysics {
	
	// The scalar for this project - 
	// The difference between pixel measurements and "real" measurements
	private static final double scalar = 2 * pow(10,7);
	
	/*
	 * Scales a value from a "real" number to a modeled pixel measurement
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
}
