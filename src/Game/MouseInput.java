package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	
	public int clickX, clickY, pressX, pressY;
	
	public void mouseClicked(MouseEvent e) {
		int mouse = e.getButton();
		clickX = e.getX();
		clickY = e.getY();
		System.out.println(clickX);
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
}
