package gravity_sim;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import static java.lang.Math.*;

public class Window extends JComponent {
	
	private static final long serialVersionUID = 1L; 
	
	public static CopyOnWriteArrayList<Planet> planetList = new CopyOnWriteArrayList<Planet>();
	public static int[] windowSize = {1920, 1080};
	
	public static void main(String[] a) throws InterruptedException {
		
		// Create a new window
		Window animation = new Window();
		JFrame f = new JFrame("Planet Sim");
		f.setSize(windowSize[0], windowSize[1]);  // Window size
		f.add(animation);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Main loop of program
		while (true) {
			Thread.sleep(1);  // 1ms per tick
			for (Planet p : planetList) {
				p.move(planetList);
				
			}
			for (Planet p: planetList){
				p.collision(planetList);
			}
			f.repaint();
		}
	}
	
	/*
	 * Generate each of the planets
	 */
	public Window() {
		int planetNumber = 100;
		Random r = new Random();
		for (int i = 0; i < planetNumber; i++) {
			
			// Generate radius
			double rangeMin = 2 * pow(10, 6);
			double rangeMax = 7 * pow(10, 8);
			double radius = rangeMin + ((rangeMax - rangeMin) * r.nextDouble());
			
			// Generate mass
			double density = 2000;
			double mass = Physics.mass(density, radius);
			
			// Create each planet and add to array
			Planet planet = new PlanetBuilder()
					.withMass(mass)
					.withRadius(radius)
					.withScaledLocation(r.nextInt(windowSize[0]), r.nextInt(windowSize[1]))
					.withScaledVelocity(0.1, -0.1)
					.build();
			planetList.add(planet);
		}
		
		// Create a large "Sun" planet (for demo purposes)
		double radius = 6 * pow(10, 9);
		double mass = Physics.mass(2000, radius);
		Planet sun = new PlanetBuilder()
				.withRadius(radius)
				.withMass(mass)
				.withScaledLocation(810, 540)
				.withMovement(false)
				.build();
		planetList.add(0, sun);
		
	}
	
	/*
	 * Paint the planets onto the screen
	 */
	public void paintComponent(Graphics g){
		
		// Paint each planet into existence
		for(Planet p: planetList){
			g.setColor(Color.BLACK);
			int radius = (int) PlanetPhysics.scaleToPixel(p.getRadius());
			double position[] = p.getPos();
			g.drawOval(
					(int) PlanetPhysics.scaleToPixel(position[0]) - radius, 
					(int) PlanetPhysics.scaleToPixel(position[1]) - radius, 
					2*radius, 
					2*radius
					);
		}
	}
}
