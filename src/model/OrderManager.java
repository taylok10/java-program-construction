/**
 * 
 */
package model;

import java.util.List;
import java.util.Queue;

/**
 * @author kelly.taylor
 *
 */
public class OrderManager {
	private Queue<Order> orders;
	private List<Order> dispatchedOrders;
	
	public boolean dispatchOrder(Order order) {
		return dispatchedOrders.add(order);
	}
	
	public Order getNextOrder() {
		return orders.remove();
	}
	
	public boolean addOrder(Order order) {
		return orders.add(order);
	}
}
