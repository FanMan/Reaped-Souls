package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class World {
	
	ImageLoader image;// = new ImageLoader();
	BufferedImage airBlock;// = image.getImage("air");
	BufferedImage dirtBlock;// = image.getImage("dirt");
	
	BufferedImage background;// = image.getImage("backGround");
	
	private int currLevel;
	
	private LevelReader level;
	private Character character;
	private Enemy enemy;
	private Music music;
	
	// takes in a level number so it knows which level to load
	public World(ImageLoader i) {
		image = i;
		currLevel = 1;
		level = new LevelReader(currLevel);
		enemy = new Enemy(level, image);
		character = new Character(level, enemy, image);
		music = new Music();
	}
	
	public void init()
	{
		character.saveImages();
		enemy.saveImages();
		//music.playMusic();
		//music.play();
		
		airBlock = image.getImage("air");
		dirtBlock = image.getImage("dirt");
		background = image.getImage("backGround");
	}
	
	public void musicStart() {
		music.playMusic();
		music.play();
	}
	
	public void musicExit() {
		music.stop();
	}
	
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
		
		/*
		 * will restart the world
		 */
		//if(currLevel > 2)
		//{
		//	currLevel = 1;
		//}
	}
	
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
				
				switch(temp)
				{
				case "AA" :
					g.drawImage(dirtBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
					break;
				case "AB" :
					g.drawImage(dirtBlock, level.getBlockX(row, col), level.getBlockY(row, col), null);
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
		character.render(g);
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public LevelReader getLevel() {
		return level;
	}
}
