/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author kelly.taylor
 *
 */
public class Order {
	private ArrayList<OrderItem> outstandingItems = new ArrayList<OrderItem>();
	private ArrayList<OrderItem> processedItems = new ArrayList<OrderItem>();
	private int ticksToPack;
	private int timeProcessing = 0;

	/**
	 * Creates a new Order
	 * 
	 * @param shelves The location of each item in this Order
	 */
	public Order(int ticksToPack, StorageShelf[] shelves) {
		this.ticksToPack = ticksToPack;
		for(StorageShelf shelf : shelves) {
			addItem(shelf);
		}
	}

	public void addItem(StorageShelf shelf) {
		if(outstandingItems.contains(new OrderItem(shelf, 0))) {
			outstandingItems.get(outstandingItems.indexOf(new OrderItem(shelf, 0))).incrementQty();
		} else {
			outstandingItems.add(new OrderItem(shelf, 1));
		}
	}
	
	public void decrementTicksToPack() {
		ticksToPack--;
	}
	
	public void incrementTimeProcessing() {
		timeProcessing++;
	}
	
	public boolean isPacked() {
		return ticksToPack ==  0;
	}

	/**
	 * Gets the items on this order
	 * 
	 * @return The item locations for this order
	 */
	public OrderItem getNextItem() {
		if(!outstandingItems.isEmpty()) {
			return outstandingItems.get(0);
		}
		return null;
	}
	
	public int getTimeProcessing() {
		return timeProcessing;
	}
	
	public void processItem(OrderItem item) {
		outstandingItems.remove(item);
		processedItems.add(item);
	}
	
	public boolean isComplete() {
		return outstandingItems.isEmpty();
	}
}