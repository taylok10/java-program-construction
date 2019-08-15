package model;

/**
 * @author kelly.taylor
 *
 */
public class StorageShelf extends Actor {
	private static final String IDENTIFIER = "ss";
	private static int id = 0;

	/**
	 * Creates a new StorageShelf
	 * 
	 * @param position The position of this StorageShelf
	 */
	public StorageShelf(GridLocation position) {
		super(position, IDENTIFIER + id);
		id++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Actor#act()
	 */
	@Override
	public boolean act() {
		// Do Nothing. StorageShelf is a passive marker.
		return true;
	}

	/**
	 * Resets the id seed of StorageShelves
	 */
	public static void resetIdCount() {
		id = 0;
	}
}