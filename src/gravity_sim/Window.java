package gravity_sim;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
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
	public static int[] windowSize = {10000 , 10000};
	public static final int TICK_TIME = 1;  // ms per tick
	public static double zoomFactor = 1;
	public static double prevZoomFactor = 1;
	public static boolean zoomer = true;
	public static boolean dragger;
	public static boolean released;
	public static double xOffset = 0;
	public static double yOffset = 0;
	public static int xDiff;
	public static int yDiff;
    public static Point startPoint;
    public static boolean centre = false;
    public static JFrame f;
    
	
	public static void main(String[] a) throws InterruptedException{
		
		// Create a new window
		Window animation = new Window();
		f = new JFrame("Planet Sim");
		f.getContentPane().setBackground( Color.black );
		f.setSize(windowSize[0], windowSize[1]);	// Window size
		f.add(animation);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.addMouseMotionListener(new MouseInput());
	    f.addMouseWheelListener(new MouseInput());
	    f.addMouseListener(new MouseInput());
	    
	    
		// Main loop of program
		while (true) {
			Thread.sleep(Window.TICK_TIME);  // ms per tick
			for (Planet p: planetList){
				p.collisionChecker(planetList);
			}
			for (Planet p : planetList) {
				p.move(planetList);
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
		Graphics2D g2 = (Graphics2D)g;
		
		
		if (zoomer) {
			AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g2.transform(at);
            
	    }
		
		if (dragger) {
            AffineTransform at = new AffineTransform();
            at.translate(xDiff/zoomFactor, yDiff/zoomFactor);
            g2.transform(at);
            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragger = false;
            }

        }
		if(centre){
			Planet p = planetList.get(0);
			double[] pos = p.getPos();
			double x = PlanetPhysics.scaleToPixel(pos[0]);
			double y = PlanetPhysics.scaleToPixel(pos[1]);
			Point point = getLocationOnScreen();
			AffineTransform at = new AffineTransform();
			System.out.println("x ="+point.getX());
			System.out.println("y ="+point.getY());
			g2.transform(at);
			centre = false;
			
		}
		
		// Paint the trails into existence
		
		g2.setColor(Color.white);
		for(Point t: planetTrails) {
			g2.fillOval(
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
			p.draw(g2);
			// Add planet trail
		}
		g2.dispose();
	}
}
