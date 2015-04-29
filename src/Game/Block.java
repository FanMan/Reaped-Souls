package Game;

import java.awt.Rectangle;

public class Block {
	
	private Rectangle block;
	private boolean unpassable;
	private int blockSize = 100;
	private String id;
	
	public Block(int yPos, int xPos, boolean canNotPass, String type) {
		block = new Rectangle(xPos * blockSize, yPos * blockSize, blockSize, blockSize);
		this.unpassable = canNotPass;
		this.id = type;
	}
	
	public Rectangle bounds() {
		return block;
	}
	
	public boolean pass() {
		return unpassable;
	}
	
	public String getId() {
		return id;
	}
}
