package GameStateClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.ImageLoader;
import Game.ScreenSize;

public class Credits {
	
	ImageLoader image;// = new ImageLoader();
	BufferedImage menu;// = image.getImage("menuBut");
	BufferedImage creditsPic;// = image.getImage("creditsPic");
	
	/**
	 * creating a static rectangle that will store the location and size
	 */
	public Rectangle menuButton;
	public ScreenSize size;
	
	public Credits(ImageLoader i) {
		image = i;
		size = new ScreenSize();
		menuButton = new Rectangle((int) (size.getScreenWidth() / 4), 800, 400, 100);
		
		saveImages();
	}
	
	/**
	 * saves the image of what the button will look like
	 */
	public void saveImages() {
		menu = image.getImage("menuBut");
		creditsPic = image.getImage("creditsPic");
	}
	
	/**
	 * returns the parameters of the rectangle such as the x and y coordinate and the width and height
	 * @return
	 */
	public Rectangle returnToMenu() {
		return menuButton;
	}
	
	public void render(Graphics g) {
		g.drawImage(creditsPic, 300, 0, null);
		g.drawImage(menu, menuButton.x, menuButton.y, null);
		
		//g.setColor(Color.red);
		//g.fillRect(menuButton.x, menuButton.y, menuButton.width, menuButton.height);
	}
}
