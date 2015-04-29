package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy {
	private Rectangle hitbox;
	private LevelReader level;
	private Physics p;
	
	private double previousX, previousY;
	private double xPos, yPos, velX, velY;
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
		
		dead = false;
		spawnPoint(level.enemySpawnX(), level.enemySpawnY());
		
		this.collide = false;
	}
	
	public void spawnPoint(double x, double y) {
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
		previousX = xPos;
		previousY = yPos;
		
		
		
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
