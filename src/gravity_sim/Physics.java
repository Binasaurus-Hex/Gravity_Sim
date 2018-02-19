package gravity_sim;
import static java.lang.Math.*;
public class Physics {
	
	static double G = (6.67* pow(10,-11));
	
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
}


