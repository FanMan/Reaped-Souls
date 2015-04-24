package Game;

public class Physics {
	private double gravity;
	private double water;
	
	public Physics() {
		this.gravity = 7.0;
		this.water = 3.2;
	}
	
	public double getGravity() {
		return gravity;
	}
	
	public double getWater() {
		return water;
	}
}
