package model;

public class PackingStation extends Actor {
	private static final String identifier = "ps";
	private static int id = 0;

	public PackingStation(int x, int y) {
		super(x,y, identifier + id);
		id++;
	}
	
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
}