package gravity_sim;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Planet {
	private double mass, radius;
	private double Vx,Vy,x,y;
	public boolean canMove = true;
	
	
	public Planet(double mass,double radius){
		this.mass = mass;
		this.radius = radius;
	}
	
	public Planet(double mass, double radius, double x, double y, double Vx, double Vy) {
		this.mass = mass;
		this.radius = radius;
		this.x = x;
		this.y = y;
		this.Vx = Vx;
		this.Vy = Vy;
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
	public double[] getVel(){
		double[] vel= {Vx,Vy};
		return vel;
	}
	
	/*
	 * Gets the radius of the planet
	 */
	public double getRadius() {
		return this.radius;
	}
	public double getMass(){
		return this.mass;
	}
	
	/*
	 * Says which direction the planet is moving in
	 */
	private int direction(double x1, double x2){
		int xdir = -1;
		if(x2 > x1){
			xdir = 1;
		}
		return xdir;
	}
	
	/*
	 * Gets the distance between this and another planet
	 */
	private double getDistance(Planet p) {
		double[] aC = this.getPos();
		double[] bC = p.getPos();
		return Physics.distance(aC[0], aC[1], bC[0], bC[1]);
	}
	
	/*
	 * Gets the angle between this and another planet (rads)
	 */
	private double getAngle(Planet p) {
		double[] bC = p.getPos();
		return Physics.angle(this.x, this.y, bC[0], bC[1]);
	}
	
	/*
	 * Gets the gravitational force between this and another planet
	 */
	private double getForce(Planet p) {
		return Physics.force(this.mass, p.mass, this.getDistance(p));
	}
	
	/*
	 * checks if the current planet is colliding with the inputed planet
	 */
	public void collision(CopyOnWriteArrayList<Planet> planetList){
		for(Planet p: planetList){
			if(p != this){
				double[] bC = p.getPos();
				double bR = p.getRadius();
				if(Physics.intersectCircle(this.x, this.y,this.radius,bC[0],bC[1],bR)){
					if(this.radius>p.getRadius()){
						double combinedMass=this.mass+p.getMass();
						double[] aV =this.getVel();
						double[] bV =p.getVel();
						double combinedVelX = aV[0]+bV[0];
						double combinedVelY = aV[1]+bV[1];
						this.mass=combinedMass;
						this.Vx=combinedVelX;
						this.Vy=combinedVelY;
						planetList.remove(p);
					}
				}
			}
		}
	}
	/*
	 * Moves the current planet
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
		double resultantAccX = resultantforceX/this.mass;	//calculates distance and moves planet accordingly
		double resultantAccY = resultantforceY/this.mass;
		double time = 1;	//time over which the acceleration is applied
		double displacementX = (this.Vx*time) + (0.5*resultantAccX*Math.pow(time, 2.0));
		double displacementY = (this.Vy*time) + (0.5*resultantAccY*Math.pow(time, 2.0));
		this.Vx=this.Vx+(resultantAccX*time);
		this.Vy=this.Vy+(resultantAccY*time);
		this.x = this.x + displacementX;
		this.y = this.y + displacementY;
		
	}
	
}
