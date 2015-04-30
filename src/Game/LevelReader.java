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
			
			String temp = reader.readLine();
			
			while(temp != null) {
				text += temp;
				temp = reader.readLine();
			}
			
			block = new Block[MapHeight][MapWidth];
			
			String[] tempArray = text.split(" ");
			
			int i = 0;
			// mapHeight goes first because you need to fill each row first before moving onto
			//   the next one
			for(int row = 0; row < MapHeight; row++) {
				for(int col = 0; col < MapWidth; col++) {
					// all transparent blocks
					if(tempArray[i].equals("--") || tempArray[i].equals("SS") || tempArray[i].equals("E1")
							|| tempArray[i].equals("FL")) {
						block[row][col] = new Block(row, col, false, tempArray[i]);
						//i++;
					}
					// for all blocks that are not passable
					else {
						block[row][col] = new Block(row, col, true, tempArray[i]);
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
					 * identifies the location for the enemy to spawn in
					 */
					if(block[row][col].getId().equals("E1")) {
						enemySpawnX = col;
						enemySpawnY = row;
					}
					
					if(block[row][col].getId().equals("FL"))
					{
						nextLevelX = col;
						nextLevelY = row;
					}
					
					if(i >= tempArray.length) i--;
				}
			}
			
			/*
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
	
	public int getMapHeight() {
		return MapHeight;
	}
	
	public int getMapWidth() {
		return MapWidth;
	}
	
	public String getType(int row, int col) {
		return block[row][col].getId();
	}
	
	public int getBlockX(int row, int col) {
		return block[row][col].bounds().x;
	}
	
	public int getBlockY(int row, int col) {
		return block[row][col].bounds().y;
	}
	
	public int getBlockSize(int row, int col) {
		return block[row][col].bounds().width;
	}
	
	public Rectangle bounding(int row, int col)
	{
		return block[row][col].bounds();
	}
	
	public int setSpawnX() {
		return characterSpawnX * 100;
	}
	
	public int setSpawnY() {
		return characterSpawnY * 100;
	}
	
	public int enemySpawnX() {
		return enemySpawnX * 100;
	}
	
	public int enemySpawnY() {
		return enemySpawnY * 100;
	}
	
	public int levelLocX() {
		return nextLevelX * 100;
	}
	
	public int levelLocY() {
		return nextLevelY * 100;
	}
}
