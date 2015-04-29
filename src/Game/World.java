package Game;

import java.awt.Color;
import java.awt.Graphics2D;

public class World {
	
	//private int currLevel;
	
	private LevelReader level;
	private Character character;
	private Enemy enemy;
	
	// takes in a level number so it knows which level to load
	public World() {
		level = new LevelReader(1);
		enemy = new Enemy(level);
		character = new Character(level, enemy);
		
		//this.enemy = new Enemy();
	}
	
	public void init() {
		
	}
	
	public void update() {
		character.update();
		enemy.update();
	}
	
	public void render(Graphics2D g) {
		
		for(int row = 0; row < level.getMapHeight(); row++) {
			for(int col = 0; col < level.getMapWidth(); col++) {
				if(level.getType(row, col).equals("AA")
						|| level.getType(row, col).equals("AB")) {
					g.setColor(Color.orange);
				}
				
				else {
					g.setColor(Color.black);
				}
				
				g.fillRect(level.getBlockX(row, col),
					level.getBlockY(row, col),
					level.getBlockSize(row, col), 
					level.getBlockSize(row, col));
			}
		}
		

		enemy.render(g);
		character.render(g);
	}
	
	public Character getCharacter() {
		return character;
	}
}
