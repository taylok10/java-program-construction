package model;

import java.util.Queue;

public class PackingStation extends Actor {
	private static final String IDENTIFIER = "ps";
	private static int id = 0;
	private Order currentOrder;
	private Queue<Order> orders;

	public PackingStation(int x, int y, Queue<Order> orders) {
		super(x, y, IDENTIFIER + id);
		id++;
		this.orders = orders;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	private boolean hasOrder() {
		return currentOrder != null;
	}

	private void takeOrder() {
		currentOrder = orders.remove();
	}

	@Override
	public void act() {
		if (!hasOrder()) {
			// If we don't currently have an order, take next one from the list
			takeOrder();
		} else {
			// Ask Robot to bring items from the list
			// Once has all items, takes x ticks packing (dependant on items)
			// Dispatch for delivery
		}
	}
}