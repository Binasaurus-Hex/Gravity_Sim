package gravity_sim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

public class Planet {
	
	public static int planetCount = 0;
	private double mass, radius;
	private double Vx, Vy, x, y;
	private boolean canMove = true;
	private Color colour = Color.BLACK;
	private boolean isFilled = false;
	
	public Planet(double mass, double radius, double x, double y, double Vx, double Vy) {
		this.mass = mass;
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.Vx = Vx;
		this.Vy = Vy;
		Planet.planetCount++;
	}
	
	public void setMovement(boolean m) {
		this.canMove = m;
	}
	
	public void setColour(Color c) {
		this.colour = c;
	}
	
	public void setFilled(boolean f) {
		this.isFilled  = f;
	}
	
	@Override
	public String toString() {
		return String.format("Planet[x=%.2f, y=%.2f, r=%.2f]",
				this.x,
				this.y,
				this.radius);
	}
	
	/*
	 * Set the position of the planets
	 * @param x
	 * @param y
	 */
	public void setPos(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Set the velocity component of the planet
	 * @param Vx
	 * @param Vy
	 */
	public void setVel(double Vx,double Vy){
		this.Vx = Vx;
		this.Vy = Vy;
	}
	
	/*
	 * Gets the position of the planet
	 */
	public double[] getPos(){
		double[] pos= {x, y};
		return pos;
	}
	
	/*
	 * Gets the velocity of the planet
	 */
	public double[] getVelocity(){
		double[] vel= {Vx, Vy};
		return vel;
	}
	
	/*
	 * Gets the radius of the planet
	 */
	public double getRadius() {
		return this.radius;
	}
	
	/*
	 * Get the mass of the planet
	 */
	public double getMass() {
		return this.mass;
	}
	
	/*
	 * Says which direction the planet is moving in
	 * If 1, then it's moving positively (right, down)
	 */
	public int direction(double x1, double x2) {
		int xdir = -1;
		if(x2 > x1){
			xdir = 1;
		}
		return xdir;
	}
	
	/*
	 * Gets the distance between this and another planet
	 */
	public double getDistance(Planet p) {
		double[] aC = this.getPos();
		double[] bC = p.getPos();
		return Physics.distance(aC[0], aC[1], bC[0], bC[1]);
	}
	
	/*
	 * Gets the angle between this and another planet (radians)
	 */
	public double getAngle(Planet p) {
		double[] aC = this.getPos();
		double[] bC = p.getPos();
		return Physics.angle(aC[0], aC[1], bC[0], bC[1]);
	}
	
	/*
	 * Gets the gravitational force between this and another planet
	 * @param p The planet to be checked against
	 * @return The force between the two planets
	 */
	public double getForce(Planet p) {
		double M = this.getMass();
		double m = p.getMass();
		double d = this.getDistance(p);
		return Physics.force(M, m, d);
	}

	public void draw(Graphics g) {
		g.setColor(this.colour);
		int radius = (int) PlanetPhysics.scaleToPixel(this.getRadius());
		double position[] = this.getPos();
		if (this.isFilled) {
			g.fillOval(
					(int) PlanetPhysics.scaleToPixel(position[0]) - radius, 
					(int) PlanetPhysics.scaleToPixel(position[1]) - radius, 
					2 * radius, 
					2 * radius
					);
		} else {
			g.drawOval(
					(int) PlanetPhysics.scaleToPixel(position[0]) - radius, 
					(int) PlanetPhysics.scaleToPixel(position[1]) - radius, 
					2 * radius, 
					2 * radius
					);
		}
	}
	
	/*
	 * Checks if this planet has collided with another, given, planet
	 * @param p The planet to be checked against
	 * @return Whether or not the two have collided
	 */
	public boolean hasCollided(Planet p) {
		double[] aP = this.getPos();
		double[] bP = p.getPos();
		double aR = this.getRadius();
		double bR = p.getRadius();
		return Physics.intersectCircle(aP[0], aP[1], aR, bP[0], bP[1], bR);
	}
	
	/*
	 * Goes through each planet in the array, checking if it has collided with another planet
	 * If it has, it removes the smaller of the two (possibly itself) from the array
	 */
	public void collisionChecker(CopyOnWriteArrayList<Planet> planetList){
		for (Planet p: planetList){
			if (p != this){
				if (this.hasCollided(p)) {
					if (this.radius > p.getRadius()){
						// Combine the mass of this and the collided planet
						double combinedMass = this.mass + p.getMass();
						this.mass = combinedMass;
						
						// Increase radius slightly?
						// pass
						
						// Combine the velocities
						double[] aV = this.getVelocity();
						double[] bV = p.getVelocity();
						double combinedVelX = aV[0] + bV[0];
						double combinedVelY = aV[1] + bV[1];
						this.Vx = combinedVelX;
						this.Vy = combinedVelY;
						
						// Remove the other planet from the list
						planetList.remove(p);
						
						// Print out that the planet has been destroyed
						System.out.print(p);
						System.out.print(" : Remaining ");
						Planet.planetCount--;
						System.out.println(Planet.planetCount);
						
						return; // Since it can only collide with something once before it stops existing
					}
				}
			}
		}
	}
	
	/*
	 * Moves the current planet, should be run once a tick
	 */
	public void move(CopyOnWriteArrayList<Planet> planetList) {
		if (!this.canMove) return;

		double resultantforceX = 0;
		double resultantforceY = 0;
		for(Planet p: planetList){	//iterate through the other planets
			if(p != this){
				double angle = this.getAngle(p);	//calculates the x and y components of the resultant force
				double force = this.getForce(p);
				int xdir = direction(this.x, p.x);
				int ydir = direction(this.y, p.y);
				double xforce = xdir * force * Math.cos(angle);
				double yforce = ydir * force * Math.sin(angle);
				resultantforceX += xforce;
				resultantforceY += yforce;
			}
		}
		double resultantAccX = resultantforceX / this.mass;  //calculates distance and moves planet accordingly
		double resultantAccY = resultantforceY / this.mass;
		double time = 1;  // Time over which the acceleration is applied (s)
		double displacementX = (this.Vx*time) + (0.5*resultantAccX*Math.pow(time, 2.0));
		double displacementY = (this.Vy*time) + (0.5*resultantAccY*Math.pow(time, 2.0));
		this.Vx=this.Vx+(resultantAccX*time);
		this.Vy=this.Vy+(resultantAccY*time);
		this.x = this.x + displacementX;
		this.y = this.y + displacementY;
		
	}
	
}
