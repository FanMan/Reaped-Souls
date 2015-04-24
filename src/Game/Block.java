package Game;

import java.awt.Rectangle;

public class Block {
	
	private Rectangle block;
	private boolean passable;
	private int blockSize = 100;
	private String id;
	
	public Block(int yPos, int xPos, boolean canPass, String type) {
		block = new Rectangle(xPos * blockSize, yPos * blockSize, blockSize, blockSize);
		this.passable = canPass;
		this.id = type;
	}
	
	public Rectangle bounds() {
		return block;
	}
	
	public boolean pass() {
		return passable;
	}
	
	public String getId() {
		return id;
	}
}
