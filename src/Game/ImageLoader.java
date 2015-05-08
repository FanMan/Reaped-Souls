package Game;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	/**
	 * all variables that save images for the background that is not in the game
	 */
	private BufferedImage MenuScreenImage, GameOverImage, CreditsImage, icon;
	
	private BufferedImage startButton, creditsButton, exitButton, menuButton;
	
	private BufferedImage airBlock, grassBlock, dirtBlock, finishLine;
	
	private BufferedImage walkright1, walkleft1, jumpRight, jumpLeft;
	
	private BufferedImage skeleLeft, skeleRight, skeleAttackLeft;
	
	private BufferedImage background;
	HashMap<String, BufferedImage> map = new HashMap<String, BufferedImage>();
	
	public ImageLoader() {
		try{
			// tells the program to read in the image
			//MenuScreenImage = ImageIO.read(new File("/images/Menu Screen Pic.png"));
			InputStream is = this.getClass().getResourceAsStream("/images/Menu Screen Pic.png");
			MenuScreenImage = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/images/Game Over Pic.png");
			GameOverImage = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/images/Credits.png");
			CreditsImage = ImageIO.read(is);
			
			
			is = this.getClass().getResourceAsStream("/backgrounds/Background.png");
			background = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/images/icon.png");
			icon = ImageIO.read(is);
			
			/**
			 * Images for the main character animation
			 */
			is = this.getClass().getResourceAsStream("/sprites/Walking Right 1.png");
			walkright1 = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/sprites/Walking Left 1.png");
			walkleft1 = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/sprites/Jumping Right.png");
			jumpRight = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/sprites/Jumping Left.png");
			jumpLeft = ImageIO.read(is);
			
			
			/**
			 * Images for the skeleton
			 */
			is = this.getClass().getResourceAsStream("/sprites/Skele Right.png");
			skeleRight = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/sprites/Skele Left.png");
			skeleLeft = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/sprites/Skele Attack Left.png");
			skeleAttackLeft = ImageIO.read(is);
			
			
			/**
			 * Images for the blocks
			 */
			is = this.getClass().getResourceAsStream("/blocks/Air Block.png");
			airBlock = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/blocks/Dirt Block.png");
			dirtBlock = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/blocks/Grass Block.png");
			grassBlock = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/blocks/Finish Line.png");
			finishLine = ImageIO.read(is);
			
			
			/**
			 * Images for the buttons
			 */
			is = this.getClass().getResourceAsStream("/images/Start Button.png");
			startButton = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/images/Credits Button.png");
			creditsButton = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/images/Menu Button.png");
			menuButton = ImageIO.read(is);
			
			is = this.getClass().getResourceAsStream("/images/Exit Button.png");
			exitButton = ImageIO.read(is);
		}//catch(IOException ex) {
		catch(Exception e) {
			System.out.println("Image could not be found");
		}
		
		map.put("menuScreenPic", MenuScreenImage);
		map.put("gameOverPic", GameOverImage);
		map.put("creditsPic", CreditsImage);
		map.put("icon", icon);
		
		map.put("backGround", background);
		
		map.put("WalkRight1", walkright1);
		map.put("WalkLeft1", walkleft1);
		map.put("JumpRight", jumpRight);
		map.put("JumpLeft", jumpLeft);
		
		map.put("SkeleRight", skeleRight);
		map.put("SkeleLeft", skeleLeft);
		map.put("SkeleAttackLeft", skeleAttackLeft);
		
		map.put("air", airBlock);
		map.put("dirt", dirtBlock);
		map.put("grass", grassBlock);
		map.put("finishLine", finishLine);
		
		map.put("startBut", startButton);
		map.put("creditsBut", creditsButton);
		map.put("menuBut", menuButton);
		map.put("exitBut", exitButton);
	}
	
	// allows for the image to be retrieved
	public BufferedImage getImage(String name) {
		return map.get(name);
	}
}
