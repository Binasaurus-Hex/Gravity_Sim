package gravity_sim;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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
	
	public static void main(String[] a) throws InterruptedException{
		
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
	public Window(){
		Spawner spawn = new Spawner(planetList,windowSize);
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
