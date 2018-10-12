package gravity_sim;

import static java.lang.Math.*;
/*
 * a class for physics specific to planets
 */
public class PlanetPhysics {
	
	// The scalar for this project - 
	// The difference between pixel measurements and "real" measurements
	private static final double scalar = 2 * pow(10,6);
	
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
	/*
	 * inputs a 2d double array containing a vector and outputs the scalar 'length'
	 * @param x
	 * @returns scalar
	 */
	public static double scalar(double[] v){
		double x = v[0];
		double y = v[1];
		double scalar  = Physics.length(x, y);
		return scalar;
	}
	/*
	 * Says which direction the planet is moving in
	 * If 1, then it's moving positively (right, down)
	 */
	public static int direction(double x1, double x2) {
		int xdir = -1;
		if(x2 > x1){
			xdir = 1;
		}
		return xdir;
	}
	
	/*
	 * finds the polar angle of the inputed vector
	 * @param vector - the inputed vector
	 * @returns angle - the polar angle of the vector(rads)
	 */
	public static double vectorAngle(double[] vector){
		double angle = Physics.angle(0, 0, vector[0], vector[1]);
		int[] array = new int[2];
		for(int i = 0; i < vector.length; i++){
			int value = direction(0,vector[i]);
			array[i]= value;
		}
		
		if((array[0]==1)&&(array[1]==1)){	//top right
			return angle;
		}
		else if((array[0]==1)&&(array[1]==-1)){ //bottom right
			return (2*PI)-angle;
		}
		else if((array[0]==-1)&&(array[1]==1)){ //top left
			return PI-angle;
		}
		else if((array[0]==-1)&&(array[1]==-1)){ //bottom left
			return PI+angle;
		}
		else{
			return 0;
		}	
	}
	
	/*
	 * calculates the final velocities of two colliding planets
	 * @param mass1 - mass of main planet(kg)
	 * @param mass2 - mass of secondary planet(kg)
	 * @param phi - angle between the two planets (with x and y direction)(rads)
	 * @param angle1 - velocity angle of main planet (polar coordinates)(rads)
	 * @param angle2 - velocity angle of secondary planet (polar coordinates)(rads)
	 * @speed1 - scalar quantity of the main planets velocity(m/s)
	 * @speed2 - scalar quantity of the secondary planets velocity(m/s)
	 * @returns result - resultant x and y velocities of main planet 
	 */
	private static double[] collision(
			double mass1,
			double mass2,
			double phi,
			double angle1,
			double angle2,
			double speed1,
			double speed2){
		double part1 = (speed1*Math.cos(angle1-phi)*(mass1-mass2))+(2*mass2*speed2*Math.cos(angle2-phi));
		double part2 = part1/(mass1+mass2);
		double partX = speed1*Math.sin(angle1-phi)*Math.cos(phi+(PI/2));
		double partY = speed1*Math.sin(angle1-phi)*Math.sin(phi+(PI/2));
		
		double vX = (part2*Math.cos(phi))+partX;
		double vY = (part2*Math.sin(phi))+partY;
		double[] result = {vX,vY};
		return result;
		
	}
	
	public static double[] collidedVel(Planet a, Planet b){
		double[] aVel = a.getVelocity();
		double[] bVel = b.getVelocity();
		double[] aPos = a.getPos();
		double[] bPos = b.getPos();
		double aMass = a.getMass();
		double bMass = b.getMass();
		double aSpeed = Physics.length(aVel[0], aVel[1]);
		double bSpeed = Physics.length(bVel[0], bVel[1]);
		double aAngle = vectorAngle(aVel);
		double bAngle = vectorAngle(bVel);
		double dirX = direction(aPos[0],bPos[0]);
		double dirY = direction(aPos[1],bPos[1]);
		double phi = dirX*dirY*abs(Physics.angle(aPos[0],aPos[1],bPos[0],bPos[1]));
		double[] result = collision(aMass, bMass, phi, aAngle, bAngle, aSpeed, bSpeed);
		return result;
		
	}
}
