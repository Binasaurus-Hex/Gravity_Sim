package gravity_sim;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput implements MouseListener,MouseWheelListener,MouseMotionListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mousePressed(MouseEvent e) {
		Window.released = false;
        Window.startPoint = MouseInfo.getPointerInfo().getLocation();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Window.released = true;
	}

	@Override 
	public void mouseWheelMoved(MouseWheelEvent e) {
		Window.zoomer = true;
        //Zoom in
        if (e.getWheelRotation() < 0) {
            Window.zoomFactor *= 1.1;
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            Window.zoomFactor /= 1.1;
        }
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point curPoint = e.getLocationOnScreen();
        Window.xDiff = curPoint.x - Window.startPoint.x;
        Window.yDiff = curPoint.y - Window.startPoint.y;
        Window.dragger = true;
        
        
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
