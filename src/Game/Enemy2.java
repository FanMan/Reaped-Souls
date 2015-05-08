package Game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy2 {
	ImageLoader image;// = new ImageLoader();
	private BufferedImage skeleLeft;// = image.getImage("SkeleLeft");
	private BufferedImage skeleRight;// = image.getImage("SkeleRight");
	
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
	
	private boolean facingRight, facingLeft;
	
	private int blockSize;
	
	private boolean collide;
	private boolean dead;
	private boolean turnLeft, turnRight;
	
	public Enemy2(LevelReader l, ImageLoader i) {
		this.level = l;
		this.image = i;
		p = new Physics();
		//this.xPos = 200;
		//this.yPos = 10;
		this.width = 120;
		this.height = 190;
		
		dead = false;
		spawnPoint(level.enemy2SpawnX(), level.enemy2SpawnY());
		
		velX = 3.0;
		velY = 0.0;
		
		this.collide = false;
		this.falling = true;
		blockSize = level.getBlockSize(0, 0);
		
		facingRight = true;
		facingLeft = false;
	}
	
	public void saveImages() {
		skeleLeft = image.getImage("SkeleLeft");
		skeleRight = image.getImage("SkeleRight");
	}
	
	public void spawnPoint(double x, double y) {
		this.xPos = x + 2;
		this.yPos = y + 5;
		dead = false;
	}
	
	public void death() {
		if(dead) {
			spawnPoint(level.enemy2SpawnX(), level.enemy2SpawnY());
		}
	}
	
	public void collided(boolean b) {
		collide = b;
	}
	
	public Rectangle boundingBox() {
		return hitbox = new Rectangle((int) xPos, (int) yPos, width, height);
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
					facingLeft = false;
					facingRight = true;
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
					facingRight = false;
					facingLeft = true;
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
		if(facingRight) {
			xPos += velX;
		}
		else if(facingLeft) {
			xPos -= velX;
		}
		
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
		if(facingRight) {
			g.drawImage(skeleRight, (int) renderX, (int) renderY + 10, null);
		}
		else if(facingLeft) {
			g.drawImage(skeleLeft, (int) renderX, (int) renderY + 10, null);
		}
	}
	
	
}
