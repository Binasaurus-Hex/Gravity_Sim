package gravity_sim;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;
import static java.lang.Math.*;

public class Window extends JComponent {
	public static boolean planetTrailToggle = true;
	private static final long serialVersionUID = 1L; 
	
	public static CopyOnWriteArrayList<Planet> planetList = new CopyOnWriteArrayList<Planet>();
	public static CopyOnWriteArrayList<Point> planetTrails = new CopyOnWriteArrayList<Point>();
	public static int[] windowSize = {1920, 1080};
	public static final int TICK_TIME = 1;  // ms per tick
	
	public static void main(String[] a) throws InterruptedException {
		
		// Create a new window
		Window animation = new Window();
		JFrame f = new JFrame("Planet Sim");
		f.getContentPane().setBackground( Color.black );
		f.setSize(windowSize[0], windowSize[1]);	// Window size
		f.add(animation);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.addKeyListener(new KeyListener() {
	        @Override
	        public void keyPressed(KeyEvent e){
	            if(e.getKeyCode()== 84){
	            	planetTrailToggle = !planetTrailToggle;
	            }
	        }

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
	    });
		// Main loop of program
		while (true) {
			Thread.sleep(Window.TICK_TIME);  // ms per tick
			for (Planet p : planetList) {
				p.move(planetList);
			}
			for (Planet p: planetList){
				p.collisionChecker(planetList);
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
			double rangeMin = 1 * pow(10, 7);
			double rangeMax = 3 * pow(10, 8);
			double radius = rangeMin + ((rangeMax - rangeMin) * r.nextDouble());
			// Generate the x and y velocities
			double Vx = 0.5*(r.nextDouble()-0.5);
			double Vy = 0.5*(r.nextDouble()-0.5);
			// Generate mass
			double density = 2000;
			double mass = Physics.mass(density, radius);
			
			// Create each planet and add to array
			Planet planet = new PlanetBuilder()
					
					.withMass(mass)
					.withRadius(radius)
					.withScaledLocation(r.nextInt(windowSize[0]), r.nextInt(windowSize[1]))
					.withScaledVelocity(Vx,Vy)
					.withRandomColour()
					.withFill(true)
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
				.withMovement(true)
				.withColour(Color.YELLOW)
				.withFill(true)
				.build();
		planetList.add(0, sun);
		
		
	}
	
	/*
	 * Paint the planets onto the screen
	 */
	
	
	public void paintComponent(Graphics g) {
		// Paint the trails into existence
		g.setColor(Color.white);
		for(Point t: planetTrails) {
			g.fillOval(
					t.x, 
					t.y, 
					2, 
					2
					);
		}
		if(planetTrailToggle) {
			planetTrails.clear();
		}
		
		// Paint each planet into existence
		for(Planet p: planetList) {
			p.draw(g);
			// Add planet trail
			double[] position = p.getPos();
			Point l = new Point();
			l.setLocation(
					PlanetPhysics.scaleToPixel(position[0]), 
					PlanetPhysics.scaleToPixel(position[1])
					);
			planetTrails.add(l);
		}
	}
}
