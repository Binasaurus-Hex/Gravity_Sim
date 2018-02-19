package gravity_sim;
public class Planet {
	private double mass, radius;
	private double Vx,Vy,x,y;
	
	
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
	 * Gets the angle between this and another planet
	 */
	private double getAngle(Planet p) {
		double[] bC = p.getPos();
		return Physics.angle(this.x, this.y, bC[0], bC[1]);
	}
	
	/*
	 * Gets the effective force between this and another planet
	 */
	private double getForce(Planet p) {
		return Physics.force(this.mass, p.mass, this.getDistance(p));
	}
	
	public void move(Planet PlanetArray[]) {
		 double resultantforceX = 0;
		 double resultantforceY = 0;
		 for(Planet p: PlanetArray) {
			 if(p != this){
				 double angle = this.getAngle(p);
				 double force = this.getForce(p);
				 int xdir = direction(this.x, p.x);
				 int ydir = direction(this.y, p.y);
				 double xforce = xdir * force * Math.cos(angle);
				 double yforce = ydir * force * Math.sin(angle);
				 resultantforceX += xforce;
				 resultantforceY += yforce;
			 }
		 }
		 System.out.println("resultant x:"+resultantforceX);
		 System.out.println("resultant y:"+resultantforceY);
		 double resultantAccX = resultantforceX/this.mass;
		 double resultantAccY = resultantforceX/this.mass;
		 double time = 1;
		 double displacementX = (this.Vx*time) + (0.5*resultantAccX*Math.pow(time, 2.0));
		 double displacementY = (this.Vy*time) + (0.5*resultantAccY*Math.pow(time, 2.0));
		 this.x = this.x + (displacementX/(6*Math.pow(10,5)));
		 this.y = this.y + (displacementY/(6*Math.pow(10,5)));
		 System.out.println("x="+this.x);
		 System.out.println("y="+this.y);
	}
	
}
