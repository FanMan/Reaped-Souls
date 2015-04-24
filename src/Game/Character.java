package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Character {
	
	private LevelReader level;
	private Enemy enemy;
	private Physics p;
	private int blockSize;
	
	private boolean right, left, falling;
	
	private double interp;
	
	private int width, height;
	private double xPos, yPos, velX, velY;
	private double renderX, renderY;
	private double previousX, previousY;
	
	private boolean onGround, jump;
	private boolean dodge;
	
	
	private Rectangle hitbox, hitbox2;
	
	private int health;
	private boolean dead;
	
	public Character(LevelReader l, Enemy e) {
		this.enemy = e;
		this.level = l;
		p = new Physics();
		this.dead = true;
		
		blockSize = level.getBlockSize(0, 0);
		
		// sets the spawn point of the player using the death function
		death();
		
		this.width = 50;
		this.height = 150;
		
		this.velX = 7.0;
		this.velY = 3.0;
		//this.velY += p.getGravity();
		
		this.dead = false;
		this.health = 10;
		
		
		//this.Max_Speed = 0.05f;
		
		this.hitbox = new Rectangle((int) xPos, (int) yPos, width, height);
		
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	public void spawnPoint(double x, double y) {
		this.xPos = x + 5;
		this.yPos = y + 5;
		dead = false;
		health = 10;
	}
	
	public void death() {
		if(dead) {
			dead = true;
			spawnPoint(level.setSpawnX(), level.setSpawnY());
			//spawnPoint(150, 150);
		}
	}
	
	///////////////////////////////////////////////////////////////
	
	public void moveRight(boolean b) {
		this.right = b;
	}
	public void moveLeft(boolean b) {
		this.left = b;
	}
	public void jump(boolean b) {
		jump = b;
	}
	public void dodge(boolean b) {
		this.dodge = b;
	}
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * When it comes to checking for collision, you must use both corners of a rectangle to see whether the player collides
	 * with an object or not using an {@code ||}. Meaning, if you want to see if the player is going to hit a wall on his left, you must check
	 * for collision on his upper left {@code xPos} and bottom left {@code xPos+width} corners and if either one is true, then the 
	 * player cannot pass. If you end up using an {@code &&}, the player will end up pass as one of the corners could be false
	 */
	public void collision() {
		double nextX = xPos + velX;
		double nextY = yPos + velY;
		double tempX = xPos, tempY = yPos;
		
		/**
		 * checks for collision to the left of the player
		 */
		
		//System.out.println((level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("A") 
		//		|| level.getType((int) ((yPos + height) / blockSize), (int) ((xPos - velX) / blockSize)).equals("A")));
		if(left)
		{
			if (level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("A") 
					|| level.getType((int) ((yPos + height) / blockSize), (int) ((xPos - velX) / blockSize)).equals("A")) 
			{
				if ((xPos - velX) <= level.getBlockX((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)) + blockSize) 
				{
					tempX = level.getBlockX((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)) + (blockSize + 10);
				}
				
			}else {
				tempX -= velX;
			}
		}
		
		/**
		 * checks for collision to the right of the player
		 */
		if(right)
		{
			if (level.getType((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)).equals("A")
					|| level.getType((int) ((yPos + height) / blockSize), (int) ((nextX + width) / blockSize)).equals("A")) {
				if ((nextX + width) >= level.getBlockX((int) (yPos / blockSize), (int) ((nextX + width) / blockSize))) 
				{
					tempX = level.getBlockX((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)) - (width+10);
				}
				
			}else {
				tempX += velX;
			}
		}
		
		/**
		 * checks for collision underneath the player
		 */
		if(!onGround){
			if(level.getType((int) ((nextY+height)/blockSize), (int) ((xPos+2)/blockSize)).equals("A") 
				|| level.getType((int) ((nextY+height)/blockSize), (int) ((xPos+width-2)/blockSize)).equals("A"))
			{
				if((nextY+height) >= level.getBlockY((int) ((nextY+height)/blockSize), (int) (xPos/blockSize)))
				{
					velY = 0;
					tempY = level.getBlockY((int) ((nextY+height)/blockSize), (int) (xPos/blockSize)) - (height+2);
				}
			}
			else {
				//onGround = false;
				tempY += velY;
			}
		}
		
		/**
		 * checks for collision above the player
		 */
		if(level.getType((int) ((yPos-7)/blockSize), (int) (xPos/blockSize)).equals("A")
				|| level.getType((int) ((yPos-7)/blockSize), (int) (xPos+width)/blockSize).equals("A"))
		{
			if((yPos-7) <= level.getBlockY((int) (yPos/blockSize), (int) (xPos/blockSize)))
			{
				velY = 0;
				tempY = level.getBlockY((int) ((yPos-7)/blockSize), (int) (xPos/blockSize)) + (blockSize);
			}
		}
		
		
		/**
		 * kills the player if they fall off the map
		 */
		if(level.getType((int) ((nextY+height)/blockSize), (int) ((xPos)/blockSize)).equals("X"))
		{
			if ((nextY) >= level.getBlockY((int) (yPos / blockSize), (int) (xPos / blockSize)))
			{
				dead = true;
			}
		}
		
		xPos = tempX;
		yPos = tempY;
	}
	
	//////////////////////////////////////////////////////////////
	
	private void jumpState() {
		
	}
	
	//////////////////////////////////////////////////////////////
	
	public void interpolate(double alpha) {
		interp = alpha;
	}
	
	public void update() {
		previousX = xPos;
		previousY = yPos;
		
		collision();
		death();
		jumpState();
		
		if(dodge) {
			this.height = 50;
		}
		
		/**
		 * movement of the player
		 */
		if(this.right) {
			xPos += velX;
		}
		if(this.left) {
			xPos -= velX;
		}
		
		///////////////////////////////////////////////////////////////////////
		// minor collision detection
		if(xPos >= enemy.boundingBox().getX() )//&& (xPos+width) <= (enemy.boundingBox().getX()+enemy.boundingBox().getWidth())) {
		{
			enemy.collided(true);
		} else enemy.collided(false);
		//System.out.println(xPos >= enemy.boundingBox().getX());
	}
	
	public void render(Graphics2D g) {
		renderX = xPos;//*interp + previousX*(1.0-interp);
		renderY = yPos;//*interp + previousY*(1.0-interp);
		
		g.setColor(Color.blue);
		g.fillRect((int) renderX, (int) renderY, width, height);
	}
}
