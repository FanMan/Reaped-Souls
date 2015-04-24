package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy {
	private Rectangle hitbox;
	private LevelReader level;
	private Physics p;
	
	private double xPos, yPos;
	private int width, height;
	private boolean collide;
	private boolean dead;
	
	public Enemy(LevelReader l) {
		this.level = l;
		p = new Physics();
		//this.xPos = 200;
		//this.yPos = 10;
		this.width = 50;
		this.height = 50;
		
		dead = true;
		death();
		
		this.collide = false;
	}
	
	public void spawnPoint(int x, int y) {
		this.xPos = x + 2;
		this.yPos = y + 5;
		dead = false;
	}
	
	public void death() {
		if(dead) {
			spawnPoint(level.enemySpawnX(), level.enemySpawnY());
		}
	}
	
	public void update() {
		//xPos += 2;
		
		yPos += p.getGravity();
		
		if(yPos+height >= 500) {
			yPos = 500-height;
		}
	}
	
	public void render(Graphics2D g) {
		if(!collide) {
			g.setColor(Color.red);
		} else g.setColor(Color.green);
		g.fillRect((int) xPos, (int) yPos, width, height);
	}
	
	public void collided(boolean b) {
		collide = b;
	}
	
	public Rectangle boundingBox() {
		return hitbox = new Rectangle((int) xPos, (int) yPos, width, height);
	}
}
