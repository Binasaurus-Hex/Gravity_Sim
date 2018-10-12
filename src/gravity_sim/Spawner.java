package gravity_sim;

import static java.lang.Math.pow;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Spawner {
	
	
	public Spawner(CopyOnWriteArrayList<Planet> planetList,int[] windowSize){
		Box2D window = new Box2D(0,windowSize[1],0,windowSize[0]);
		int planetNumber = 200;
		Random r = new Random();
		
		// Create a large "Sun" planet (for demo purposes)
		double sunRadius = 1.3 * pow(10, 9);
		double sunMass = Physics.mass(2000, sunRadius);
		Planet sun = new PlanetBuilder()
				.withRadius(sunRadius)
				.withMass(sunMass*10)
				.withColour(Color.YELLOW)
				.withFill(true)
				.withRawVelocity(1,1)
				.withMovement(false)
				.build();
		
		//attempt to spawn the sun
		spawnRandom(window,sun);
		planetList.add(sun);
		
		
				
		//create and add the specified number of random planets		
		for (int i = 0; i < planetNumber; i++) {
			
			// Generate radius
			double rangeMin = 1.7 * pow(10, 6);
			double rangeMax = 6 * pow(10, 7);
			double radius = rangeMin + ((rangeMax - rangeMin) * r.nextDouble());
			// Generate the x and y velocities
			double Vx = 0.5*(r.nextDouble()-0.5);
			double Vy = 0.5*(r.nextDouble()-0.5);
			// Generate mass
			double density = 2000;
			double mass = Physics.mass(density, radius);
			
			// Create each planet and add to array
			Planet planet = new PlanetBuilder()
					.withMass(mass*10)
					.withRadius(radius)
					.withScaledVelocity(Vx,Vy)
					.withRandomColour()
					.withFill(true)
					.build();
			//spawn the current planet
			spawnRandom(window,planet);
			planetList.add(planet);
		}
		
	}
	
	/*
	 * takes in two Box2D objects and finds if the second box has space inside the container
	 * free space is donated by 0's in an array and taken space denoted by 1's
	 * @param container - Box2D
	 * @param box - Box2D
	 * @returns boolean
	 */
	private boolean allocateSpace(Box2D container, Box2D box){
		try{
		int[][] windowArray = container.getArray();
		//iterates through the rows
		for(int row = box.getRowStart(); row<(box.getRowEnd() + 1); row++){
			//iterates through the columns
			for(int column = box.getColumnStart(); column<(box.getColumnEnd() + 1); column++){
				if((windowArray[row][column])==1){
					return false;
				}
				else{
					windowArray[row][column]=1;
				}
			}
		}
		//sets master array to include 1's where the box is.
		container.setArray(windowArray);
		return true;
		}
		
		catch(Exception e){
			return false;
		}
	}
	/*
	 * attempts to spawn a planet at the specified location
	 * @param container
	 * @param planet
	 * @param x
	 * @param y
	 * @returns boolean
	 */
	private boolean spawnStatic(Box2D container,Planet planet,int x,int y){
		planet.setPos(PlanetPhysics.scaleToReal(x),PlanetPhysics.scaleToReal(y));
		Box2D box = planet.toBox();
		return allocateSpace(container,box);
	}
	private void spawnRandom(Box2D container,Planet planet){
		Random rand = new Random();
		boolean spawned = false;
		while(spawned == false){
			double x = rand.nextInt(container.getWidth());
			double y = rand.nextInt(container.getHeight());
			planet.setPos(PlanetPhysics.scaleToReal(x),PlanetPhysics.scaleToReal(y));
			Box2D box = planet.toBox();
			if(allocateSpace(container,box)){
				spawned=true;
			}
		}
	}
}
