package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy {
	private Rectangle hitbox;
	private LevelReader level;
	private Physics p;
	
	Random rand = new Random();
	
	private double previousX, previousY;
	private double xPos, yPos, velX, velY;
	private double renderX, renderY;
	private double interp;
	private int width, height;
	
	private boolean onGround, falling;
	
	private int blockSize;
	
	private boolean collide;
	private boolean dead;
	private boolean turnLeft, turnRight;
	
	public Enemy(LevelReader l) {
		this.level = l;
		p = new Physics();
		//this.xPos = 200;
		//this.yPos = 10;
		this.width = 50;
		this.height = 50;
		
		dead = false;
		spawnPoint(level.enemySpawnX(), level.enemySpawnY());
		
		velX = 3.0;
		velY = 0.0;
		
		this.collide = false;
		this.falling = true;
		blockSize = level.getBlockSize(0, 0);
		
		turnRight = true;
		turnLeft = false;
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
	
	public void collision()
	{
		double nextX = xPos + velX;
		double nextY = yPos + 7;
		double tempX = xPos, tempY = yPos;
		
		//if(left)
		//{
			if (level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("AA") 
					|| level.getType((int) ((yPos + height - velY) / blockSize), (int) ((xPos - velX) / blockSize)).equals("AA")
					|| level.getType((int) ((yPos + (height / 2)) / blockSize), (int) ((xPos - velX) / blockSize)).equals("AA")
					|| level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("TT") 
					|| level.getType((int) ((yPos + height - velY) / blockSize), (int) ((xPos - velX) / blockSize)).equals("TT")) 
			{
				if ((xPos - velX) <= level.getBlockX((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)) + blockSize) 
				{
					tempX = level.getBlockX((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)) + (blockSize + 10);
					turnRight = true;
					turnLeft = false;
				}
				
			}else {
				tempX -= velX;
			}
		//}
		
		/*
		 * checks for collision to the right of the player
		 */
		//if(right)
		//{
			if (level.getType((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)).equals("AA")
					|| level.getType((int) ((yPos + height-velY) / blockSize), (int) ((nextX + width) / blockSize)).equals("AA")
					|| level.getType((int) ((yPos + (height / 2)) / blockSize), (int) ((nextX + width) / blockSize)).equals("AA")
					|| level.getType((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)).equals("TT")
					|| level.getType((int) ((yPos + height-velY) / blockSize), (int) ((nextX + width) / blockSize)).equals("TT"))
			{
				if ((nextX + width) >= level.getBlockX((int) (yPos / blockSize), (int) ((nextX + width) / blockSize))) 
				{
					tempX = level.getBlockX((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)) - (width+10);
					turnRight = false;
					turnLeft = true;
				}
				
			}else {
				tempX += velX;
			}
		//}
		
		/*
		 * checks for collision underneath the player
		 */
		if(falling){
			if(level.getType((int) ((nextY + height)/blockSize), (int) ((xPos+2)/blockSize)).equals("AA") 
				|| level.getType((int) ((nextY + height) / blockSize), (int) ((xPos+width-2)/blockSize)).equals("AA")
				|| level.getType((int) ((nextY + height) / blockSize), (int) ((xPos+2)/blockSize)).equals("AB") 
				|| level.getType((int) ((nextY + height) / blockSize), (int) ((xPos+width-2)/blockSize)).equals("AB"))
			{
				if((nextY + height) >= level.getBlockY((int) ((nextY + height) / blockSize), (int) (xPos / blockSize)))
				{
					velY = 0;
					tempY = level.getBlockY((int) ((nextY + height) / blockSize), (int) (xPos / blockSize)) - (height+2);
					onGround = true;
				}
			}
			else {
				onGround = false;
				tempY += velY;
			}
		}
		
		/*
		 * checks for collision above the player
		 */
		if(level.getType((int) ((yPos - 7) / blockSize), (int) (xPos / blockSize)).equals("AA")
				|| level.getType((int) ((yPos - 7) / blockSize), (int) (xPos + width) / blockSize).equals("AA"))
		{
			
			if((yPos - 7) <= level.getBlockY((int) ((yPos - 7) / blockSize), (int) (xPos / blockSize)))
			{
				velY = 0;
				tempY = level.getBlockY((int) ((yPos - 7) / blockSize), (int) (xPos / blockSize)) + (blockSize) - 5;
				System.out.println(true);
			}
		}
		
		
		/*
		 * kills the player if they fall off the map
		 */
		if(level.getType((int) ((nextY+height)/blockSize), (int) ((xPos)/blockSize)).equals("XX"))
		{
			if ((nextY) >= level.getBlockY((int) (yPos / blockSize), (int) (xPos / blockSize)))
			{
				dead = true;
			}
		}
		
		/*
		 * returns the temporary variables back into the actual player positions
		 */
		xPos = tempX;
		yPos = tempY;
	}
	
	public void movement()
	{
		
		
		
		
		velY += p.getGravity();
		yPos += velY;
	}
	
	public void interpolate(double alpha)
	{
		interp = alpha;
	}
	
	public void update() {
		previousX = xPos;
		previousY = yPos;
		
		movement();
		collision();
		
		renderX = (xPos - previousX) * interp + previousX;
		renderY = (yPos - previousY) * interp + previousY;
	}
	
	public void render(Graphics2D g) {
		if(!collide) {
			g.setColor(Color.red);
		} else g.setColor(Color.green);
		g.fillRect((int) renderX, (int) renderY, width, height);
	}
	
	public void collided(boolean b) {
		collide = b;
	}
	
	public Rectangle boundingBox() {
		return hitbox = new Rectangle((int) xPos, (int) yPos, width, height);
	}
}
