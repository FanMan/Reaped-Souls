package Game;

public class Physics {
	private double gravity;
	private double water;
	private World w;
	
	public Physics() {
		this.gravity = 1.5;
		this.water = 3.2;
		
	}
	
	/**
	 * returns the gravity of the world. It is consistent for all entities
	 * @return
	 */
	public double getGravity() {
		return gravity;
	}
	
	/**
	 * returns the speed of water if the character or enemy were in the water.
	 * Was never used currently
	 * @return
	 */
	public double getWater() {
		return water;
	}
}
