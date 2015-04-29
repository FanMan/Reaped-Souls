package Game;

public class Camera {
	private double x = 0, y = 0;
	//private Character player;
	
	public Camera() {
		
	}
	
	public void update(Character player, int screenWidth) {
		//x += ((-player.getXPos() + (screenWidth / 2)) - x) * 0.0275;
		x += (-player.getXPos() - x + (screenWidth / 2.5) - 100) * 0.0775;
		y += (-player.getYPos() - y + 500) * 0.0255;
	}
	
	public double getCameraX() {
		return x;
	}
	
	public double getCameraY() {
		return y;
	}
}
