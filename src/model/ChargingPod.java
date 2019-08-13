package model;

public class ChargingPod extends Actor {
	private static final String IDENTIFIER = "c";
	private static int id = 0;

	public ChargingPod(int x, int y) {
		super(x,y, IDENTIFIER + id);
		id++;
	}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	public static void resetIdCount() {
		id = 0;		
	}
}