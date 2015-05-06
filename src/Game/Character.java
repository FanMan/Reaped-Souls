package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Character {
	/**
	 * Used to get the image and then display the image
	 */
	ImageLoader image = new ImageLoader();
	BufferedImage walkRight1 = image.getImage("WalkRight1");
	BufferedImage walkLeft1 = image.getImage("WalkLeft1");
	private boolean facingRight, facingLeft;
	
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
	private boolean right, left, jump, run, attack;
	
	private double interp;
	
	/*
	 * position and size of the character
	 */
	private int width, height;
	private double xPos, yPos, velX, velY;
	private double renderX, renderY;
	private double previousX, previousY;
	
	private boolean onGround, falling;
	
	/*
	 * status of the player
	 */
	private int health, lives;
	private boolean dead;
	
	/**
	 * 
	 * @param scytheW is the width of the scythe
	 * @param scytheH is the height of the scythe
	 */
	private int scytheW, scytheH;
	private boolean renderScythe;
	
	/**
	 * @param constructor method of the character. Takes in LevelReader and Enemy
	 */
	public Character(LevelReader l, Enemy e) {
		this.enemy = e;
		this.level = l;
		p = new Physics();
		
		//this.lives = 3;
		
		// gets the size of the block and stores it in blockSize
		// all blocks are of the same width and height
		blockSize = level.getBlockSize(0, 0);
		
		/**
		 * initializes the location of the player
		 */
		spawnPoint(level.setSpawnX(), level.setSpawnY());
		
		width = 90;
		height = 150;
		
		velX = 7.0;
		velY = 0.0;
		
		this.dead = false;
		this.health = 10;
		this.onGround = false; // the character is currently not on the ground
		this.falling = true; // the character is falling
		this.attack = false;
		this.run = false;
		
		facingRight = true;
		facingLeft = false;
		
		this.scytheW = (int) (xPos + width);
		this.scytheH = 40;
		this.renderScythe = false;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	// retrieve the x position of the player
	public double getXPos() {
		return xPos;
	}
	
	// retrieve the y position of the player
	public double getYPos() {
		return yPos;
	}
	
	/////////////////////////////////////////////////////////////////////////
	
	// sets the spawn point of the player
	public void spawnPoint(double x, double y) {
		/**
		 * when this method is called, it takes in the position of the tile
		 * where the character is supposed to spawn. In the text file, it is represented
		 * as "SS". So when the player starts the level or dies, they are set back at that
		 * spawn point with a small displacement of 5 for both x and y to prevent
		 * the character from getting stuck inside any non-passable block
		 */
		this.xPos = x + 5;
		this.yPos = y + 5;
		dead = false;
		health = 10;
	}
	
	// method that checks whether the player is dead or not
	public void death() {
		if(dead == true) {
			//dead = true;
			spawnPoint(level.setSpawnX(), level.setSpawnY());
			lives--;
			//spawnPoint(150, 150);
		}
		
		/*
		 * when the character loses all of their lives, the application game ends
		 * later on, there will be a game over screen that will appear
		 */
		//if(lives < 0) {
			//System.exit(0);
		//	lives = 3;
		//}
	}
	
	///////////////////////////////////////////////////////////////
	
	/*
	 * methods that checks whether the player should perform a certain action
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
	public void run(boolean b) {
		run = b;
	}
	public void attack(boolean b) {
		this.attack = b;
	}
	
	//////////////////////////////////////////////////////////////////
	
	/*
	 * gets the lives of the player
	 */
	public int getLives()
	{
		return lives;
	}
	
	/*
	 * able to set the lives of the player
	 */
	public void setLives(int life)
	{
		lives = life;
	}
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * When it comes to checking for collision, you must use both corners of a rectangle to see whether the player collides
	 * with an object or not using an {@code ||}. Meaning, if you want to see if the player is going to hit a wall on his left, you must check
	 * for collision on his upper left {@code xPos} and bottom left {@code xPos+width} corners and if either one is true, then the 
	 * player cannot pass. If you end up using an {@code &&}, the player will end up pass as one of the corners could be false
	 */
	public void collision() {
		/**
		 * store the position of the player into temporary values. The reason for this is that you
		 * need to predict whether the player is actually going to hit a wall before it actually
		 * happens. The reason why is to prevent any errors where the player could get stuck
		 * inside a wall while checking.
		 * 
		 * nextX is what the x position will be when x velocity is applied
		 * nextY is what the y position will be when 7 is applied. The reason 7 is applied
		 *   instead of velY is that velY is constantly changing and it could be at 0 so it
		 *   would not be able to predict beforehand
		 * tempX and tempY temporarily stores the x and y values without having to actually
		 *   affect the x and y positions
		 */
		double nextX = xPos + velX;
		double nextY = yPos + 7;
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
			if (level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("AA") 
					|| level.getType((int) ((yPos + height - velY) / blockSize), (int) ((xPos - velX) / blockSize)).equals("AA")
					|| level.getType((int) ((yPos + (height / 2)) / blockSize), (int) ((xPos - velX) / blockSize)).equals("AA")
					|| level.getType((int) (yPos / blockSize), (int) ((xPos - velX) / blockSize)).equals("TT") 
					|| level.getType((int) ((yPos + height - velY) / blockSize), (int) ((xPos - velX) / blockSize)).equals("TT")) 
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
			if (level.getType((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)).equals("AA")
					|| level.getType((int) ((yPos + height-velY) / blockSize), (int) ((nextX + width) / blockSize)).equals("AA")
					|| level.getType((int) ((yPos + (height / 2)) / blockSize), (int) ((nextX + width) / blockSize)).equals("AA")
					|| level.getType((int) (yPos / blockSize), (int) ((nextX + width) / blockSize)).equals("TT")
					|| level.getType((int) ((yPos + height-velY) / blockSize), (int) ((nextX + width) / blockSize)).equals("TT"))
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
			if ((nextY + (height / 2)) >= level.getBlockY((int) ((nextY + (height / 2)) / blockSize), (int) (xPos / blockSize)))
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
				velY -= 15;
				onGround = false;
			}
		}
	}
	
	/**
	 * the movement of the player
	 */
	public void movement() {
		/**
		 * movement of the player
		 */
		if(right) {
			xPos += velX;
			facingRight = true;
			facingLeft = false;
		}
		if(left) {
			xPos -= velX;
			facingRight = false;
			facingLeft = true;
		}
		
		velY += p.getGravity();
		yPos += velY;
		
		if(run) {
			velX = 10.5;
		}
	}
	
	public void attackSystem() {
		
	}
	
	//////////////////////////////////////////////////////////////
	
	public void interpolate(double alpha) {
		interp = alpha;
	}
	
	public void update() {
		previousX = xPos;
		previousY = yPos;
		
		movement();
		collision();
		attackSystem();
		death();
		jumpState();
		
		///////////////////////////////////////////////////////////////////////
		// minor collision detection
		if(xPos >= enemy.boundingBox().getX() )//&& (xPos+width) <= (enemy.boundingBox().getX()+enemy.boundingBox().getWidth())) {
		{
			enemy.collided(true);
		} else enemy.collided(false);
		
		renderX = (xPos - previousX) * interp + previousX;
		renderY = (yPos - previousY) * interp + previousY;
	}
	
	public void render(Graphics2D g) {
		//renderX = xPos*interp + previousX*(1.0-interp);
		//renderY = yPos*interp + previousY*(1.0-interp);
		
		//g.setColor(Color.blue);
		//g.drawImage(walkRight1, (int) renderX, (int) renderY, null);
		//g.fillRect((int) renderX, (int) renderY, width, height);
		
		if(facingRight) {
			g.drawImage(walkRight1, (int) renderX, (int) renderY, null);
		}
		else if(facingLeft) {
			g.drawImage(walkLeft1, (int) renderX, (int) renderY, null);
		}
		
		if(renderScythe) {
			g.setColor(Color.cyan);
			g.fillRect((int) (renderX + width), (int) (renderY + (height / 3)), scytheW, scytheH);
		}
	}
	
	
}