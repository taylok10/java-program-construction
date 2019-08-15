/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class OrderItem {
	private int qty;
	private StorageShelf location;

	/**
	 * Creates a new OrderItem
	 * 
	 * @param location The location of this OrderItem
	 * @param qty      The qty of these items
	 */
	public OrderItem(StorageShelf location, int qty) {
		this.location = location;
		this.qty = qty;
	}

	/**
	 * Decrements the qty of this item by one
	 */
	public void decrementQty() {
		qty--;
	}

	/**
	 * Gets the location of this Item
	 * 
	 * @return The StorageShelf
	 */
	public StorageShelf getLocation() {
		return location;
	}

	/**
	 * Gets the Qty of this item
	 * 
	 * @return The qty of this item
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * Increments the qty of this item by one
	 */
	public void incrementQty() {
		qty++;
	}

	/**
	 * Sets the Qty of this item
	 * 
	 * @param qty The new qty of this item
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/*
	 * Compares this OrderItem to the provided object.
	 * 
	 * Two OrderItem objects are considered equal when they have the same UID
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof OrderItem) {
			OrderItem shelf = (OrderItem) o;
			return location.getUID().equals(shelf.getLocation().getUID());
		}
		return false;
	}
}
