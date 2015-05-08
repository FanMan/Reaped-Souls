package GameStateClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import Game.ImageLoader;
import Game.MouseInput;
import Game.ScreenSize;
import States.GameState;

public class Menu {
	
	private Rectangle buttonPlay, buttonCredits, buttonExit;
	private ScreenSize size;
	private MouseInput mouse;
	
	private GameState state;
	ImageLoader image;// = new ImageLoader();
	
	private BufferedImage MenuScreenPic;// = image.getImage("menuScreenPic");
	private BufferedImage startButton;// = image.getImage("startBut");
	private BufferedImage creditsButton;// = image.getImage("creditsBut");
	private BufferedImage exitButton;// = image.getImage("exitBut");
	
	public Menu(MouseInput m, GameState s, ImageLoader i) {
		image = i;
		mouse = m;
		state = s;
		size = new ScreenSize();
		
		/**
		 * creates each button as a rectangle
		 */
		buttonPlay = new Rectangle((int) (size.getScreenWidth() / 4), 200, 400, 100);
		buttonCredits = new Rectangle((int) (size.getScreenWidth() / 4), 400, 400, 100);
		buttonExit = new Rectangle((int) (size.getScreenWidth() / 4), 600, 400, 100);
		
		/**
		 * calls the saveImages function as it doesn't need an update function
		 */
		saveImages();
	}
	
	public void saveImages() {
		MenuScreenPic = image.getImage("menuScreenPic");
		startButton = image.getImage("startBut");
		creditsButton = image.getImage("creditsBut");
		exitButton = image.getImage("exitBut");
	}
	
	/**
	 * returns the parameters of the rectangle such as the x and y coordinate and the width and height
	 * @return
	 */
	public Rectangle play() {
		return buttonPlay;
	}
	
	/**
	 * returns the parameters of the rectangle such as the x and y coordinate and the width and height
	 * @return
	 */
	public Rectangle exit() {
		return buttonExit;
	}
	
	/**
	 * returns the parameters of the rectangle such as the x and y coordinate and the width and height
	 * @return
	 */
	public Rectangle credits() {
		return buttonCredits;
	}
	
	public void render(Graphics g) {
		g.drawImage(MenuScreenPic, 200, 100, null);
		
		g.drawImage(startButton, buttonPlay.x, buttonPlay.y, null);
		g.drawImage(creditsButton, buttonCredits.x, buttonCredits.y, null);
		g.drawImage(exitButton, buttonExit.x, buttonExit.y, null);
		//g.drawRect(buttonPlay.x, buttonPlay.y, buttonPlay.width, buttonPlay.height);
		//g.drawRect(buttonCredits.x, buttonCredits.y, buttonCredits.width, buttonCredits.height);
		//g.drawRect(buttonExit.x, buttonExit.y, buttonExit.width, buttonExit.height);
	}
}
