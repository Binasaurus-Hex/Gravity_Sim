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
	public static int[] windowSize = {1920, 1080};
	public static final int TICK_TIME = 1;  // ms per tick
	private static double zoomFactor = 1;
	private static double prevZoomFactor = 1;
	private static boolean zoomer = true;
	private static boolean dragger;
	private static boolean released;
	private static double xOffset = 0;
	private static double yOffset = 0;
	private static int xDiff;
	private static int yDiff;
    private static Point startPoint;
	
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
	    f.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				zoomer = true;

		        //Zoom in
		        if (e.getWheelRotation() < 0) {
		            zoomFactor *= 1.1;
		            f.repaint();
		        }
		        //Zoom out
		        if (e.getWheelRotation() > 0) {
		            zoomFactor /= 1.1;
		            //f.repaint();
		        }
				
			}
	    	
	    });
	    f.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				Point curPoint = e.getLocationOnScreen();
		        xDiff = curPoint.x - startPoint.x;
		        yDiff = curPoint.y - startPoint.y;
		        dragger = true;
		        f.repaint();
		        
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
	    	
	    });
	    f.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				released = false;
		        startPoint = MouseInfo.getPointerInfo().getLocation();
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				f.repaint();
				released = true;
				
		        
				
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
			double[] position = p.getPos();
			Point l = new Point();
			l.setLocation(
					PlanetPhysics.scaleToPixel(position[0]), 
					PlanetPhysics.scaleToPixel(position[1])
					);
			
		}
	}
}
