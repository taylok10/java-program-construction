/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class Order {
	// Design TODO: we'll need to consider how we manage which items have been
	// packed on an order, as orders have multiple items - PackingStations will only
	// start packing once they have all the items but the time it takes to pack is
	// dependant on how many there are. I'm thinking we just need each item on an
	// order to have some kind of flag, or the PackingStation can have a way of
	// managing this
	// Don't forget the same shelf can appear multiple times on an order but Robots
	// only need to visit the shelf one to fetch both of the items (I believe this
	// should affect packing time however)
	private StorageShelf[] shelves;

	/**
	 * Creates a new Order
	 * 
	 * @param shelves The location of each item in this Order
	 */
	public Order(StorageShelf[] shelves) {
		this.shelves = shelves;
	}

	/**
	 * Gets the item locations for this order
	 * 
	 * @return The item locations for this order
	 */
	public StorageShelf[] getShelves() {
		return shelves;
	}
}