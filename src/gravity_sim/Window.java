package gravity_sim;
import java.awt.*;
import java.util.Random;

import javax.swing.*;
import static java.lang.Math.*;
public class Window extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final Planet[] PlanetArray = new Planet[10];

	public static void main(String[] a) throws InterruptedException{
		Window animation = new Window();
		JFrame f = new JFrame("Planet Sim");
		f.setSize(600, 600);
		f.add(animation);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while (true) {
			Thread.sleep(1);
			for (Planet p : animation.PlanetArray) {
				p.move(animation.PlanetArray,p);
			}
			f.repaint();
			System.out.println("print");
		}
	}
	public Window() {
		Random r = new Random();
		for (int i = 0; i < PlanetArray.length; i++) {
			int x=r.nextInt(500);
			int y=r.nextInt(500);
			int radius = (int) (10 + r.nextInt(50));
			double mass =Physics.mass(pow(10,13),radius);
			PlanetArray[i] = new Planet(mass,radius);
			PlanetArray[i].set_pos(x, y);
			PlanetArray[i].set_vel(10, -10);
			
		}
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.blue);
		for(Planet p: PlanetArray){
			g.setColor(Color.BLACK);
			int radius=(int)p.radius;
			g.drawOval((int)p.x, (int)p.y,2*radius,2*radius);
			
		}
	}
}