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
		return orders.remove();
	}
	
	public boolean addOrder(Order order) {
		return orders.add(order);
	}
	
	public void empty() {
		orders = new LinkedList<Order>();
		dispatchedOrders = new ArrayList<Order>();
	}
}
