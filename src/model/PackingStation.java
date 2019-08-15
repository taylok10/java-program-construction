package model;

import java.util.List;

/**
 * @author kelly.taylor
 *
 */
public class PackingStation extends Actor {
	private static final String IDENTIFIER = "ps";
	private static int id = 0;
	private Order currentOrder;
	private OrderItem itemInProgress;
	private OrderManager orders;
	private List<Robot> robots;
	private boolean finished;

	/**
	 * Creates a new PackingStation
	 * 
	 * @param position     The position of this PackingStation
	 * @param orderManager The OrderManager of this PackingStation
	 * @param robots       The robots this PackingStation has access to
	 */
	public PackingStation(GridLocation position, OrderManager orderManager, List<Robot> robots) {
		super(position, IDENTIFIER + id);
		id++;
		orders = orderManager;
		this.robots = robots;
		finished = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Actor#act()
	 */
	@Override
	public boolean act() {
		if (!hasOrder()) {
			// If we don't currently have an order, take next one from the list
			if (!orders.isOrdersEmpty()) {
				takeOrder();
			} else {
				finished = true;
			}
		}
		if (hasOrder()) {
			// Update time spent processing for reporting
			currentOrder.incrementTimeProcessing();

			if (!currentOrder.isComplete()) {
				if (itemInProgress == null) {
					requestRobot(currentOrder.getNextItem());
				}
				// Robot is collecting item wait
			} else if (!currentOrder.isPacked()) {
				// Once has all items, takes x ticks packing
				packOrder();
			} else {
				WarehouseSimulation.addReportEntry("Completed " + currentOrder.orderDesc());
				// Dispatch for delivery
				dispatchForDelivery();
			}
		}
		return true;
	}

	/**
	 * Accepts an incoming collection from a Robot
	 * 
	 * @param shelf The item being delivered
	 * @return was the item accepted successfully. This will fail if the item is not
	 *         for this PackingStation.
	 */
	public boolean acceptItemDelivery(StorageShelf shelf) {
		if (shelf == itemInProgress.getLocation()) {
			currentOrder.processItem(itemInProgress);
			itemInProgress = null;
			return true;
		}
		return false;
	}

	/**
	 * Dispatch the current order for delivery
	 */
	private void dispatchForDelivery() {
		orders.dispatchOrder(currentOrder);
		currentOrder = null;
	}

	/**
	 * Gets if this PackingStation currently has an order
	 * 
	 * @return if we have an order in progress
	 */
	private boolean hasOrder() {
		return currentOrder != null;
	}

	/**
	 * Gets the current order of this PackingStation
	 * 
	 * @return the Order that is currently being handled by this PackingStation
	 */
	public Order getCurrentOrder() {
		return currentOrder;
	}

	/**
	 * Gets the item in progress
	 * 
	 * @return The item currently being processed by this PackingStation
	 */
	public OrderItem getItemInProgress() {
		return itemInProgress;
	}

	/**
	 * Gets if this PackingStation has finished processing orders
	 * 
	 * @return If this PackingStation has finished processing orders
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Packs the current order
	 */
	private void packOrder() {
		currentOrder.decrementTicksToPack();
	}

	/**
	 * Requests a robot for the provided item
	 * 
	 * @param item The item to be assigned
	 * @return If the item has been accepted by a Robot
	 */
	private void requestRobot(OrderItem item) {
		for (Robot robot : robots) {
			boolean accepted = robot.assignmentRequest(new Assignment(this, item.getLocation()));
			if (accepted) {
				// We have a robot, we can stop asking
				itemInProgress = item;
				return;
			}
		}
	}

	/**
	 * Resets the id seed of PackingStations
	 */
	public static void resetIdCount() {
		id = 0;
	}

	/**
	 * Takes a new order to process
	 */
	private void takeOrder() {
		currentOrder = orders.getNextOrder();
	}
}