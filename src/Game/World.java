package Game;

import java.awt.Color;
import java.awt.Graphics2D;

public class World {
	
	//private int currLevel;
	
	private LevelReader level;
	//private Character character;
	//private Enemy enemy;
	
	// takes in a level number so it knows which level to load
	public World(LevelReader l) {
		this.level = l;
		
		//this.enemy = new Enemy();
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		
		for(int row = 0; row < level.getMapHeight(); row++) {
			for(int col = 0; col < level.getMapWidth(); col++) {
				if(level.getType(row, col).equals("A")) {
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
	}
}
