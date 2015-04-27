package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.regex.*;

public class Character {
	
	/*
	 * create variables of the classes
	 */
	private LevelReader level;
	private Enemy enemy;
	private Physics p;
	
	private int blockSize;
	
	private Rectangle hitbox;
	
	/*
	 * movement of whether the character should perform an action
	 */
	private boolean right, left, jump, attack;
	
	private double interp;
	
	/*
	 * position and size of the character
	 */
	private int width, height;
	private double xPos, yPos, velX, velY;
	private double renderX, renderY;
	private double previousX, previousY;
	
	private boolean onGround, falling;
	private boolean dodge;
	
	/*
	 * status of the player
	 */
	private int health, lives;
	private boolean dead;
	
	/*
	 * constructor method of the character. Takes in LevelReader and Enemy
	 */
	public Character(LevelReader l, Enemy e) {
		this.enemy = e;
		this.level = l;
		p = new Physics();
		this.dead = true;
		lives = 3;
		blockSize = level.getBlockSize(0, 0);
		
		// sets the spawn point of the player using the death function
		death();
		
		width = 50;
		height = 150;
		
		velX = 7.0;
		velY = 0.0;
		//this.velY += p.getGravity();
		
		this.dead = false;
		this.health = 10;
		this.onGround = false;
		this.falling = true;
		this.attack = false;
		
		//this.Max_Speed = 0.05f;
		
		this.hitbox = new Rectangle((int) xPos, (int) yPos, width, height);
		
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	// get the x position of the player
	public double getXPos() {
		return xPos;
	}
	
	// get the y position of the player
	public double getYPos() {
		return yPos;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	// sets the spawn point of the player
	public void spawnPoint(double x, double y) {
		this.xPos = x + 5;
		this.yPos = y + 5;
		dead = false;
		health = 10;
	}
	
	// method that checks whether the player is dead or not
	public void death() {
		int a = 2;
		if(dead == true) {
			//dead = true;
			System.out.println(a + 2);
			spawnPoint(level.setSpawnX(), level.setSpawnY());
			
			//spawnPoint(150, 150);
		}
	}
	
	///////////////////////////////////////////////////////////////
	
	/*
	 * methods that checkes whether the player should perform a certain action
	 */
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
		dodge = b;
	}
	public void attack(boolean b) {
		this.attack = b;
	}
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * When it comes to checking for collision, you must use both corners of a rectangle to see whether the player collides
	 * with an object or not using an {@code ||}. Meaning, if you want to see if the player is going to hit a wall on his left, you must check
	 * for collision on his upper left {@code xPos} and bottom left {@code xPos+width} corners and if either one is true, then the 
	 * player cannot pass. If you end up using an {@code &&}, the player will end up pass as one of the corners could be false
	 */
	public void collision() {
		System.out.println(velY);
		double nextX = xPos + velX;
		double nextY = yPos + velY;
		double tempX = xPos, tempY = yPos;
		
		/**
		 * {@code level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("A") }
		 * Checks for collision on the top left corner of the player while checking ahead of where the player will be
		 *   instead of where the player is now
		 *   
		 * 
		 */
		if(left)
		{
			if (level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("A") 
					|| level.getType((int) ((yPos + height-velY) / blockSize), (int) ((xPos - velX) / blockSize)).equals("A")
					|| level.getType((int) ((yPos + (height / 2)) / blockSize), (int) ((xPos - velX) / blockSize)).equals("A")) 
			{
				if ((xPos - velX) <= level.getBlockX((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)) + blockSize) 
				{
					tempX = level.getBlockX((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)) + (blockSize + 10);
				}
				
			}else {
				tempX -= velX;
			}
		}
		
		/*
		 * checks for collision to the right of the player
		 */
		if(right)
		{
			if (level.getType((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)).equals("A")
					|| level.getType((int) ((yPos + height-velY) / blockSize), (int) ((nextX + width) / blockSize)).equals("A")
					|| level.getType((int) ((yPos + (height / 2)) / blockSize), (int) ((nextX + width) / blockSize)).equals("A"))
			{
				if ((nextX + width) >= level.getBlockX((int) (yPos / blockSize), (int) ((nextX + width) / blockSize))) 
				{
					tempX = level.getBlockX((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)) - (width+10);
				}
				
			}else {
				tempX += velX;
			}
		}
		
		/*
		 * checks for collision underneath the player
		 */
		if(falling){
			if(level.getType((int) ((nextY+height-velY)/blockSize), (int) ((xPos+2)/blockSize)).equals("A") 
				|| level.getType((int) ((nextY+height-velY)/blockSize), (int) ((xPos+width-2)/blockSize)).equals("A")
				|| level.getType((int) ((nextY+height-velY)/blockSize), (int) ((xPos+2)/blockSize)).equals("AB") 
				|| level.getType((int) ((nextY+height-velY)/blockSize), (int) ((xPos+width-2)/blockSize)).equals("AB"))
			{
				if((nextY+height+velY) >= level.getBlockY((int) ((nextY+height)/blockSize), (int) (xPos/blockSize)))
				{
					velY = 0;
					tempY = level.getBlockY((int) ((nextY+height)/blockSize), (int) (xPos/blockSize)) - (height+2);
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
		if(level.getType((int) ((yPos-7)/blockSize), (int) (xPos/blockSize)).equals("A")
				|| level.getType((int) ((yPos-7)/blockSize), (int) (xPos+width)/blockSize).equals("A"))
		{
			if((yPos-7) <= level.getBlockY((int) (yPos/blockSize), (int) (xPos/blockSize)))
			{
				velY = 0;
				tempY = level.getBlockY((int) ((yPos-7)/blockSize), (int) (xPos/blockSize)) + (blockSize);
			}
		}
		
		
		/*
		 * kills the player if they fall off the map
		 */
		if(level.getType((int) ((nextY+height)/blockSize), (int) ((xPos)/blockSize)).equals("X"))
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
	
	//////////////////////////////////////////////////////////////
	
	private void jumpState() {
		
		/*
		 * if onGround == true
		 *   if(jump == true) jump is called
		 *     velY
		 */
		if(onGround)
		{
			if(jump)
			{
				velY -= 32;
			}
		}
	}
	
	//////////////////////////////////////////////////////////////
	
	public void interpolate(double alpha) {
		interp = alpha;
	}
	
	public void update() {
		previousX = xPos;
		previousY = yPos;
		
		/**
		 * movement of the player
		 */
		if(this.right) {
			xPos += velX;
		}
		if(this.left) {
			xPos -= velX;
		}
		
		velY += p.getGravity();
		System.out.println(velY);
		yPos += velY;
		
		collision();
		//death();
		jumpState();
		
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
