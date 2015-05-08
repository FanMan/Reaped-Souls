package Game;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenSize {
	public int screenWidth, screenHeight;
	
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	public ScreenSize()
	{
		//screenWidth = dim.getHeight();
	}
	
	public double getScreenWidth()
	{
		return dim.getWidth();
	}
	
	public double getScreenHeight()
	{
		return dim.getHeight();
	}
	
	public Dimension dimension()
	{
		return dim;
	}
}
