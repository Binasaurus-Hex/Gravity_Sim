package gravity_sim;
import static java.lang.Math.*;
public class Physics {
	
	private static double G = 6.67 * pow(10,-11);
	private static double scalar= 2 * pow(10,7);
	/*
	 * Get the gravitational force between two planets
	 * @param M Planet 1's mass
	 * @param m Planet 2's mass
	 * @param r Distance between the two planets 
	 * @return The gravitational force between two planets
	 */
	public static double force(double M, double m, double r){
		double force = (G*M*m)/pow(r,2);
		return force;
	}

	/*
	 * The distance between the two coordinates
	 */
	public static double distance(double x1, double y1, double x2, double y2){
		double dx = x2-x1;
		double dy = y2-y1;
		double d = sqrt(pow(dx,2)+pow(dy,2));
		return d;
	}
	
	/*
	 * Gets the angle between the two planets
	 */
	public static double angle(double x1,double y1,double x2,double y2){
		double dx = abs(x2-x1);
		double dy = abs(y2-y1);
		double angle = Math.atan(dy/dx);
		return angle;
		
	}
	
	/*
	 * Gives you the mass of a planet
	 */
	public static double mass(double density, double radius){
		double volume = (4/3)*PI*pow(radius,3);
		double mass = density*volume;
		return mass;
	}
	/*
	 * scales between pixel and to scale measurements for distance
	 * has a parameter direction for scaling up: Direction.UP =(pixel to scale)
	 * 							or scaling down: Direction.DOWN = (scale to pixel)
	 */
	public static double scale(double value, Direction direction) {
		if(direction==Direction.UP) {
			return value * scalar;
			
		}
		else {
			return value / scalar;
		}
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


