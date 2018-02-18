package gravity_sim;
public class Planet {
	double mass,radius;
	public double Vx,Vy,x,y;
	
	
	public Planet(double mass,double radius){
		this.mass=mass;
		this.radius=radius;
		
	}
	public void set_pos(double x,double y){
		this.x=x;
		this.y=y;
	}
	public void set_vel(double Vx,double Vy){
		this.Vx=Vx;
		this.Vy=Vy;
	}
	public double[] pos(){
		double[] pos= {x,y};
		return pos;
	}
	public double[] vel(){
		double[] vel= {Vx,Vy};
		return vel;
	}
	private int direction(double x1,double x2){
		int xdir=-1;
		if(x2>x1){
			xdir=1;
		}
		return xdir;
		
	}
	public void move(Planet PlanetArray[],Planet i) {
		 double resultantforceX = 0;
		 double resultantforceY = 0;
		 for(Planet p:PlanetArray){
			 if(p!=i){
				 double distance=Physics.distance(this.x,this.y,p.x,p.y);
				 double angle=Physics.angle(this.x,this.y,p.x,p.y);
				 double force=Physics.force(this.mass, p.mass, distance);
				 int xdir=direction(this.x,p.x);
				 int ydir=direction(this.y,p.y);
				 double xforce=xdir*force*Math.cos(angle);
				 double yforce=ydir*force*Math.sin(angle);
				 resultantforceX+=xforce;
				 resultantforceY+=yforce;
			 }
		 System.out.println("resultant x:"+resultantforceX);
		 System.out.println("resultant y:"+resultantforceY);
		 double resultantAccX=resultantforceX/this.mass;
		 double resultantAccY=resultantforceX/this.mass;
		 double time=1;
		 double displacementX=(this.Vx*time)+(0.5*resultantAccX*Math.pow(time, 2.0));
		 double displacementY=(this.Vy*time)+(0.5*resultantAccY*Math.pow(time, 2.0));
		 this.x=this.x+(displacementX/(6*Math.pow(10,5)));
		 this.y=this.y+(displacementY/(6*Math.pow(10,5)));
		 System.out.println("x="+this.x);
		 System.out.println("y="+this.y);
		 }
		
	}
	
}
