package Game;

public class Physics {
	private double gravity;
	private double water;
	private World w;
	
	public Physics() {
		this.gravity = 1.5;
		this.water = 3.2;
		
	}
	
	public double getGravity() {
		return gravity;
	}
	
	public double getWater() {
		return water;
	}
}
