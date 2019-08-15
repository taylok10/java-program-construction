/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author kelly.taylor
 *
 */
public class OrderManager {
	private LinkedList<Order> orders;
	private ArrayList<Order> dispatchedOrders;

	/**
	 * Creates a new OrderManager oversee the orders in a Warehouse Floor
	 */
	public OrderManager() {
		reset();
	}

	/**
	 * Adds a new order to be processed
	 * 
	 * @param order The order to be added
	 * @return If the order was added to the outstanding orders successfully
	 */
	public boolean addOrder(Order order) {
		return orders.add(order);
	}

	/**
	 * Adds an order to the dispatched list
	 * 
	 * @param order The order to be dispatched
	 * @return If the order was added to the dispatched orders successfully
	 */
	public boolean dispatchOrder(Order order) {
		return dispatchedOrders.add(order);
	}

	/**
	 * Removes and returns the next order from the list to be processed. Note: Will
	 * return null if there are no more orders.
	 * 
	 * @return The next order to be processed
	 */
	public Order getNextOrder() {
		if (!orders.isEmpty()) {
			return orders.remove();
		}
		return null;
	}

	/**
	 * Gets if the dispatched orders are empty
	 * 
	 * @return If there are no dispatched orders
	 */
	public boolean isDispatchedOrdersEmpty() {
		return dispatchedOrders.isEmpty();
	}

	/**
	 * Gets if the outstanding orders are empty
	 * 
	 * @return If there are no more orders to be processed
	 */
	public boolean isOrdersEmpty() {
		return orders.isEmpty();
	}

	/**
	 * Resets the OrderManager to it's default state
	 */
	public void reset() {
		orders = new LinkedList<Order>();
		dispatchedOrders = new ArrayList<Order>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = "";
		int count = 1;
		for (Order order : orders) {
			output += "ORDER - " + count + ": " + order.getTicksToPack() + " tick(s) to pack" + "\n  Shelves: ";
			for (StorageShelf ss : order.getOutstandingShelves()) {
				output += ss.getUID() + " ";
			}
			output = output.trim() + "\n";
			count++;
		}
		return output;
	}
}
