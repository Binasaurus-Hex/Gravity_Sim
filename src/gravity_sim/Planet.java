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
		String string = "Planet : position:"+
				" x = "+PlanetPhysics.scaleToPixel(this.x)+
				" y = "+PlanetPhysics.scaleToPixel(this.y)+
				" radius = "+PlanetPhysics.scaleToPixel(this.radius);
		return string;
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
	 * Gets the position of the planet
	 */
	public double[] getPos(){
		double[] pos= {x, y};
		return pos;
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
	 * Gets the distance between this and another planet
	 */
	public double getDistance(Planet p) {
		double[] aC = this.getPos();
		double[] bC = p.getPos();
		return Physics.distance(aC[0], aC[1], bC[0], bC[1]);
	}
	
	/*
	 * Gets the angle between this and another planet (rads)
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
	/*
	 * returns a Box2D object of the planet
	 * @returns box
	 */
	public Box2D toBox(){
		int pixRadius = (int)Math.ceil(PlanetPhysics.scaleToPixel(radius));
		int x = (int)Math.ceil(PlanetPhysics.scaleToPixel(this.x));
		int y = (int)Math.ceil(PlanetPhysics.scaleToPixel(this.y));
		int rowStart = y - pixRadius;
		int rowEnd = y + pixRadius;
		int columnStart = x - pixRadius;
		int columnEnd = x + pixRadius;
		Box2D box = new Box2D(rowStart,
							  rowEnd,
							  columnStart,
							  columnEnd);
		return box;
		
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
		double[] aPos = this.getPos();
		double[] bPos = p.getPos();
		double aRad = this.getRadius();
		double bRad = p.getRadius();
		return Physics.intersectCircle(aPos[0], aPos[1], aRad, bPos[0], bPos[1], bRad);
	}
	
	/*
	 * Goes through each planet in the array, checking if it has collided with another planet
	 * If it has, it removes the smaller of the two (possibly itself) from the array
	 */
	public void collisionChecker(CopyOnWriteArrayList<Planet> planetList){
		for (Planet p: planetList){
			if (p != this){
				if (this.hasCollided(p)) {
					double[] aVelNew = PlanetPhysics.collidedVel(this, p);
					this.setVel(aVelNew[0], aVelNew[1]);
					
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
				int xdir = PlanetPhysics.direction(this.x, p.x);
				int ydir = PlanetPhysics.direction(this.y, p.y);
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
