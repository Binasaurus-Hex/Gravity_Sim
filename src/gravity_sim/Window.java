package gravity_sim;
import java.awt.*;
import java.util.Random;

import javax.swing.*;
import static java.lang.Math.*;
public class Window extends JComponent {
	
	private static final long serialVersionUID = 1L;
	public final Planet[] PlanetArray = new Planet[10];

	public static void main(String[] a) throws InterruptedException {
		
		// Create a new window
		Window animation = new Window();
		JFrame f = new JFrame("Planet Sim");
		f.setSize(600, 600);
		f.add(animation);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Main loop of program
		while (true) {
			Thread.sleep(1);  // 1ms per tick
			for (Planet p : animation.PlanetArray) {
				p.move(animation.PlanetArray);
			}
			f.repaint();
			System.out.println("print");
		}
	}
	
	/*
	 * Generate each of the planets
	 */
	public Window() {
		
		Random r = new Random();
		for (int i = 0; i < PlanetArray.length; i++) {
			
			// Generate coords
			double x = Physics.scale(r.nextInt(600),Direction.UP);
			double y = Physics.scale(r.nextInt(600),Direction.UP);
			
			// Generate radius
			double rangeMin=2*pow(10,6);
			double rangeMax=7*pow(10,8);
			double radius = rangeMin+((rangeMax-rangeMin)*r.nextDouble());
			
			// Generate mass
			double mass = Physics.mass(2000, radius);
			
			// Create each planet and add to array
			PlanetArray[i] = new Planet(mass, radius, x, y, 10, -10);
		}
	}
	
	/*
	 * Paint the planets onto the screen
	 */
	public void paintComponent(Graphics g){
		
		// Paint each planet
		g.setColor(Color.blue);
		for(Planet p: PlanetArray){
			g.setColor(Color.BLACK);
			int radius = (int) Physics.scale(p.getRadius(),Direction.DOWN);
			double position[] = p.getPos();
			g.drawOval((int) Physics.scale(position[0],Direction.DOWN)-radius, (int)Physics.scale(position[1], Direction.DOWN)-radius, 2*radius, 2*radius);
		}
	}
}
