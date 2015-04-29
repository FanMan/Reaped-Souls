package Game;

public class Camera {
	private double x = 0, y = 0;
	//private Character player;
	private double interp;
	
	public Camera() {
		
	}
	
	public void interpolate(double alpha) {
		interp = alpha;
	}
	
	public void update(Character player, int screenWidth, int screenHeight) {
		//x += ((-player.getXPos() + (screenWidth / 2)) - x) * 0.0275;
		x += (-player.getXPos() - x + (screenWidth / 2.5) - 100) * 0.0775 * interp;
		//y += (-player.getYPos() - y + 500) * 0.0255;
		y += (-player.getYPos() - y + (screenHeight / 2.5) - 400) * 0.0775 * interp;
	}
	
	public double getCameraX() {
		return x;
	}
	
	public double getCameraY() {
		return y;
	}
}
