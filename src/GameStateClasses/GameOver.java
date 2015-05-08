package GameStateClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Game.ImageLoader;
import Game.ScreenSize;

public class GameOver {
	ImageLoader image;// = new ImageLoader();
	BufferedImage menuButton;// = image.getImage("menuBut");
	BufferedImage gameOverPic;// = image.getImage("gameOverPic");
	
	private Rectangle restartButton;
	public ScreenSize size = new ScreenSize();
	
	public GameOver(ImageLoader i) {
		image = i;
		restartButton = new Rectangle((int) (size.getScreenWidth() / 4), 700, 400, 200);
		
		saveImages();
	}
	
	public void saveImages() {
		menuButton = image.getImage("menuBut");
		gameOverPic = image.getImage("gameOverPic");
	}
	
	/**
	 * returns the parameters of the rectangle such as the x and y coordinate and the width and height
	 * @return
	 */
	public Rectangle restartBut() {
		return restartButton;
	}
	
	public void render(Graphics g) {
		g.drawImage(gameOverPic, (int) (size.getScreenWidth() / 8), 100, null);
		g.drawImage(menuButton, restartButton.x, restartButton.y, null);
		//g.setColor(Color.red);
		//g.fillRect(restartButton.x, restartButton.y, restartButton.width, restartButton.height);
	}
}
