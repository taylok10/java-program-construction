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
	
	public OrderManager() {
		orders = new LinkedList<Order>();
		dispatchedOrders = new ArrayList<Order>();
	}
	
	public boolean dispatchOrder(Order order) {
		return dispatchedOrders.add(order);
	}
	
	public Order getNextOrder() {
		if (!orders.isEmpty()) {
			return orders.remove();
		}
		return null;
	}
	
	public boolean addOrder(Order order) {
		return orders.add(order);
	}
	
	public void empty() {
		orders = new LinkedList<Order>();
		dispatchedOrders = new ArrayList<Order>();
	}
	
	public boolean isOrdersEmpty() {
		return orders.isEmpty();
	}
	
	public boolean isDispatchedOrdersEmpty() {
		return dispatchedOrders.isEmpty();
	}
	
	@Override
	public String toString() {
		String output = "";
		int count = 1;
		for (Order order : orders) {
			output += "ORDER - " + count + ": " + order.getTicksToPack() + " tick(s) to pack" + "\n  Shelves: ";
			for (StorageShelf ss :order.getOutstandingShelves()) {
				output += ss.getUID() + " ";
			}
		output = output.trim() + "\n";
		count++;
		}
		return output;
	}
}
