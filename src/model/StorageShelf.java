package model;

public class StorageShelf extends Actor {
	private static final String identifier = "ss";
	private static int id = 0;
	
	public StorageShelf(int x, int y) {
		super(x,y, identifier + id);
		id++;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
}