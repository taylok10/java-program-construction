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
	 * @param ticksToPack The number of ticks this Order will take to pack
	 * @param shelves     The location of each item in this Order
	 */
	public Order(int ticksToPack, StorageShelf[] shelves) {
		this.ticksToPack = ticksToPack;
		for (StorageShelf shelf : shelves) {
			addItem(shelf);
		}
	}

	/**
	 * Adds a new item to the order
	 * 
	 * @param shelf The item to add to the order
	 */
	public void addItem(StorageShelf shelf) {
		if (outstandingItems.contains(new OrderItem(shelf, 0))) {
			int index = outstandingItems.indexOf(new OrderItem(shelf, 0));
			outstandingItems.get(index).incrementQty();
		} else {
			outstandingItems.add(new OrderItem(shelf, 1));
		}
	}

	/**
	 * Decrements to ticks to pack by one
	 * 
	 * @return Was this decremented successfully
	 */
	public boolean decrementTicksToPack() {
		if (ticksToPack > 0) {
			ticksToPack--;
			return true;
		}
		return false;
	}

	/**
	 * Gets the next item on this order
	 * 
	 * @return The next item outstanding, will be null if no items
	 */
	public OrderItem getNextItem() {
		if (!outstandingItems.isEmpty()) {
			return outstandingItems.get(0);
		}
		return null;
	}

	/**
	 * Gets the outstanding shelves on this Order
	 * 
	 * @return A list of the outstanding shelves on this order
	 */
	public ArrayList<StorageShelf> getOutstandingShelves() {
		ArrayList<StorageShelf> shelves = new ArrayList<StorageShelf>();
		for (OrderItem orderItem : outstandingItems) {
			shelves.add(orderItem.getLocation());
		}
		return shelves;
	}

	/**
	 * Gets the processed shelves on this Order
	 * 
	 * @return A list of the processed shelves on this order
	 */
	public ArrayList<StorageShelf> getProcessedShelves() {
		ArrayList<StorageShelf> shelves = new ArrayList<StorageShelf>();
		for (OrderItem orderItem : processedItems) {
			shelves.add(orderItem.getLocation());
		}
		return shelves;
	}

	/**
	 * Gets to ticks to pack the order
	 * 
	 * @return the ticks required to pack the order
	 */
	public int getTicksToPack() {
		return ticksToPack;
	}

	/**
	 * Gets the time this order has been processed for
	 * 
	 * @return the ticks spent on this order
	 */
	public int getTimeProcessing() {
		return timeProcessing;
	}

	/**
	 * Increments the tick spent processing this order by one tick
	 */
	public void incrementTimeProcessing() {
		timeProcessing++;
	}

	/**
	 * Gets if there are any outstanding items
	 * 
	 * @return if the order has been completed
	 */
	public boolean isComplete() {
		return outstandingItems.isEmpty();
	}

	/**
	 * Gets if the order has any time remaining for packing
	 * 
	 * @return if the order has been packed
	 */
	public boolean isPacked() {
		return ticksToPack == 0;
	}

	/**
	 * Gets a description of the order containing the time spend processing it and
	 * the shelves
	 * 
	 * @return a short description of the order
	 */
	public String orderDesc() {
		String output = "";
		output += "ORDER:\n  " + timeProcessing + " ticks to process" + "\n  Shelves: ";
		for (StorageShelf storageShelf : getProcessedShelves()) {
			output += storageShelf.getUID() + " ";
		}
		output = output.trim() + "\n";
		return output;
	}

	/**
	 * Gets the number of outstanding unique items on the order
	 * 
	 * @return the number of outstanding items (unique)
	 */
	public int outstandingItemsSize() {
		return outstandingItems.size();
	}

	/**
	 * Processes an item by moving it from outstanding to processed
	 * 
	 * @param item The item to process
	 * @return if the item was successfully processed
	 */
	public boolean processItem(OrderItem item) {
		if (outstandingItems.remove(item)) {
			return processedItems.add(item);
		}
		return false;
	}

	/**
	 * Gets the number of processed unique items on the order
	 * 
	 * @return the number of processed items (unique)
	 */
	public int processedItemsSize() {
		return processedItems.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = "";
		output += "ORDER: " + getTicksToPack() + " tick(s) to pack" + "\n  Shelves: ";
		for (StorageShelf ss : getOutstandingShelves()) {
			output += ss.getUID() + " ";
		}
		output = output.trim() + "\n";
		return output;
	}
}