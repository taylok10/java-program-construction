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
	
	public OrderItem(StorageShelf location, int qty) {
		this.location = location;
		this.qty = qty;
	}
	
	public StorageShelf getLocation() {
		return location;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public void incrementQty() {
		qty++;
	}
	
	public void decrementQty() {
		qty--;
	}

	@Override
    public boolean equals(Object o) { 
		if(o instanceof OrderItem) {
			OrderItem shelf = (OrderItem)o;
			return location.getUID().equals(shelf.getLocation().getUID());
		}
		return false;
    } 
}
