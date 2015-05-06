package Game;

import java.awt.Color;
import java.awt.Graphics2D;

public class World {
	
	private int currLevel;
	
	private LevelReader level;
	private Character character;
	private Enemy enemy;
	
	
	// takes in a level number so it knows which level to load
	public World() {
		currLevel = 1;
		level = new LevelReader(currLevel);
		//createWorld();
		enemy = new Enemy(level);
		character = new Character(level, enemy);
		
		//this.enemy = new Enemy();
	}
	
	public void init()
	{
		
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
		
		for(int row = 0; row < level.getMapHeight(); row++) {
			for(int col = 0; col < level.getMapWidth(); col++) {
				String temp = level.getType(row, col);
				
				if(level.getType(row, col).equals("AA")
						|| level.getType(row, col).equals("AB")) {
					g.setColor(Color.orange);
				}
				else {
					g.setColor(Color.black);
				}
				/*
				switch(temp)
				{
				case "AA" :
					g.setColor(Color.orange);
					break;
				case "AB" :
					g.setColor(Color.orange);
					break;
				default :
					g.setColor(Color.black);
					break;
				}
				*/
				g.fillRect(level.getBlockX(row, col), level.getBlockY(row, col),
					level.getBlockSize(row, col), level.getBlockSize(row, col));
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
