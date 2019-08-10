package model;

public class ChargingPod extends Actor {
	private static final String identifier = "c";
	private static int id = 0;

	public ChargingPod(int x, int y) {
		super(x,y, identifier + id);
		id++;
	}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
}