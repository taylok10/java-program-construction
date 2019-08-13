package model;

public class StorageShelf extends Actor {
	private static final String IDENTIFIER = "ss";
	private static int id = 0;
	
	public StorageShelf(GridLocation position) {
		super(position, IDENTIFIER + id);
		id++;
	}

	@Override
	public void act() {
		//Do Nothing. StorageShelf is a passive marker.
	}

	public static void resetIdCount() {
		id = 0;		
	}
}