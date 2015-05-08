package Game;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LevelReader {
	
	private String text = "";
	
	private int MapWidth, MapHeight;
	
	private Block[][] block;
	
	private int characterSpawnX = 0, characterSpawnY = 0;
	private int enemySpawnX = 0, enemySpawnY = 0;
	private int enemy2SpawnX = 0, enemy2SpawnY = 0;
	private int nextLevelX = 0, nextLevelY = 0;
	
	public LevelReader(int currLevel) {
		/**
		 * In order to read from a file, needed to make the resource folder a source folder and then include the levels folder
		 * by right clicking on the levels folder and then click on include in build path
		 * 
		 * Link below showed me:
		 * "http://stackoverflow.com/questions/18386613/java-getting-file-as-resource-when-its-in-the-project-folder"
		 */
		InputStream is = this.getClass().getResourceAsStream("/levels/level " + currLevel + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		// test to see if it reads the lines
		try {
			/**
			 * allows me to remove any blank spaces from the strings without creating extra indexes
			 */
			
			//String temp = reader.readLine();
			
			MapWidth = Integer.parseInt(reader.readLine());
			MapHeight = Integer.parseInt(reader.readLine());
			
			// temporarly stores the first line of the level after the two numbers
			String temp = reader.readLine();
			
			while(temp != null) {
				text += temp;
				temp = reader.readLine();
			}
			
			// sets the dimension of the height and width of two-dimensional array
			block = new Block[MapHeight][MapWidth];
			
			String[] tempArray = text.split(" ");
			
			int i = 0;
			// mapHeight goes first because you need to fill each row first before moving onto
			//   the next one
			for(int row = 0; row < MapHeight; row++) {
				for(int col = 0; col < MapWidth; col++) {
					// all non-transparent blocks
					if(tempArray[i].equals("AA") || tempArray[i].equals("TT") || tempArray[i].equals("AB")) {
						block[row][col] = new Block(row, col, true, tempArray[i]);
						//i++;
					}
					// for all blocks that are passable
					else {
						block[row][col] = new Block(row, col, false, tempArray[i]);
						//i++;
					}
					i++;
					
					/*
					 * identifies the location for the player to spawn in
					 */
					if(block[row][col].getId().equals("SS")) {
						characterSpawnX = col;
						characterSpawnY = row;
					}
					
					/*
					 * identifies the location for the first enemy to spawn in
					 */
					if(block[row][col].getId().equals("E1")) {
						enemySpawnX = col;
						enemySpawnY = row;
					}
					
					/*
					 * identifies the location for the second enemy to spawn in
					 */
					if(block[row][col].getId().equals("E2")) {
						enemy2SpawnX = col;
						enemy2SpawnY = row;
					}
					
					/*
					 * identifies the location of the finish line
					 */
					if(block[row][col].getId().equals("FL"))
					{
						nextLevelX = col;
						nextLevelY = row;
					}
					
					if(i >= tempArray.length) i--;
				}
			}
			
			/*
			 * Tests to see if Buffered Reader actually work
			System.out.println(block[4][1].getId());
			System.out.println(block[0][1].bounds().x);
			System.out.println(block[2][3].getId());
			System.out.println(block[2][7].getId());
			*/
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	// gets the height of the level
	public int getMapHeight() {
		return MapHeight;
	}
	
	// gets the width of the level
	public int getMapWidth() {
		return MapWidth;
	}
	
	// gets the string type of the level aka the ID
	public String getType(int row, int col) {
		return block[row][col].getId();
	}
	
	// gets the x-coordinate of the block
	public int getBlockX(int row, int col) {
		return block[row][col].bounds().x;
	}
	
	// gets the y-coordinate of the block
	public int getBlockY(int row, int col) {
		return block[row][col].bounds().y;
	}
	
	// gets the size of the block
	public int getBlockSize(int row, int col) {
		return block[row][col].bounds().width;
	}
	
	// tells whether the block is passable or not
	public boolean isPassable(int row, int col) {
		return block[row][col].pass();
	}
	
	// gets the bounds of the rectangle since the blocks are static
	public Rectangle bounding(int row, int col)
	{
		return block[row][col].bounds();
	}
	
	// sets the x-coordinate spawnpoint of the character
	public int setSpawnX() {
		return characterSpawnX * 100;
	}
	
	// sets the y-coordinate spawnpoint of the character
	public int setSpawnY() {
		return characterSpawnY * 100;
	}
	
	// sets the x-coordinate spawnpoint of the first enemy
	public int enemySpawnX() {
		return enemySpawnX * 100;
	}
	
	// sets the y-coordinate spawnpoint of the first enemy
	public int enemySpawnY() {
		return enemySpawnY * 100;
	}
	
	// sets the x-coordinate spawnpoint of the second enemy
	public int enemy2SpawnX() {
		return enemy2SpawnX * 100;
	}
	
	// sets the y-coordinate spawnpoint of the second enemy
	public int enemy2SpawnY() {
		return enemy2SpawnY * 100;
	}
	
	// the two methods below were to try to allow the character to switch 
	// to the next level once they finish the previous level
	public int levelLocX() {
		return nextLevelX * 100;
	}
	
	public int levelLocY() {
		return nextLevelY * 100;
	}
}
