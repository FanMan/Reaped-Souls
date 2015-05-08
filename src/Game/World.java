package Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class World {
	
	ImageLoader image;// = new ImageLoader();
	BufferedImage airBlock, grassBlock, dirtBlock, finishLine;// = image.getImage("air");
	
	BufferedImage background;// = image.getImage("backGround");
	
	private int currLevel;
	
	private LevelReader level;
	private Character character;
	private Enemy enemy;
	private Enemy2 enemy2;
	private Music music;
	
	/**
	 * takes in an ImageLoader which will allow each class access to that ImageLoader 
	 */
	
	public World(ImageLoader i) {
		image = i;
		currLevel = 1;
		level = new LevelReader(currLevel);
		enemy = new Enemy(level, image);
		enemy2 = new Enemy2(level, image);
		character = new Character(level, enemy, enemy2, image);
		music = new Music();
	}
	
	/**
	 * when this is called for the first time, it saves the images of each class in
	 * their own method so that it can be rendered properly
	 */
	public void init()
	{
		character.saveImages();
		enemy.saveImages();
		enemy2.saveImages();
		//music.playMusic();
		//music.play();
		
		airBlock = image.getImage("air");
		grassBlock = image.getImage("grass");
		dirtBlock = image.getImage("dirt");
		finishLine = image.getImage("finishLine");
		background = image.getImage("backGround");
	}
	
	/**
	 * the following two methods were to try to get music to work but there
	 * were errors occurring when called in the update method as mutliple
	 * instances would occur
	 */
	public void musicStart() {
		music.playMusic();
		music.play();
	}
	
	public void musicExit() {
		music.stop();
	}
	
	/**
	 * tried implementing the ability to change levels but when the next level tries to load,
	 * the game would crash as the character would load first before the map finishes loading
	 */
	public void nextWorld() {
		
		/**
		 * 
		 */
		if(character.getXPos() >= level.levelLocX() 
				&& character.getYPos() >= level.levelLocY())
		{
			//currLevel++;
			level = new LevelReader(currLevel);
			
		}
		
	}
	
	/**
	 * restarts the world when the player dies
	 */
	public void restartWorld() {
		if(character.getLives() < 0)
		{
			//currLevel = 2;
			character.setLives(3);
			System.out.println("Restarting game");
		}
	}
	
	public void update() {
		character.update();
		enemy.update();
		enemy2.update();
		
		//createWorld();
		nextWorld();
		//restartWorld();
	}
	
	public void render(Graphics2D g) {
		
		g.drawImage(background, 0, 200, null);
		
		for(int row = 0; row < level.getMapHeight(); row++) {
			for(int col = 0; col < level.getMapWidth(); col++) {
				String temp = level.getType(row, col);
				
				/*if(level.getType(row, col).equals("AA")
						|| level.getType(row, col).equals("AB")) {
					//g.setColor(Color.orange);
					g.drawImage(dirtBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
					
				}
				else {
					//g.setColor(Color.black);
					g.drawImage(airBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
				}*/
				
				/**
				 * @param switch(temp) gets the string type of each individual block and renders them 
				 * onto the screen
				 */
				switch(temp)
				{
				case "AA" :
					g.drawImage(grassBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
					break;
				case "AB" :
					g.drawImage(dirtBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
					break;
				case "FL" :
					g.drawImage(finishLine, level.getBlockX(row, col), level.getBlockY(row, col), null);
					break;
				default :
					g.drawImage(airBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
					break;
				}
				
				//g.fillRect(level.getBlockX(row, col), level.getBlockY(row, col),
				//	level.getBlockSize(row, col), level.getBlockSize(row, col));
			}
		}
		
		enemy.render(g);
		enemy2.render(g);
		character.render(g);
	}
	
	/**
	 * returns the character class to be used in the display class
	 * @return
	 */
	public Character getCharacter() {
		return character;
	}
	
	/**
	 * returns the LevelReaer class to be used in the display class
	 * @return
	 */
	public LevelReader getLevel() {
		return level;
	}
}
