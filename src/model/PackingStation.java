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

	/**
	 * Creates a new PackingStation
	 * 
	 * @param x            The column coordiniate of this PackingStation
	 * @param y            The row coordiniate of this PackingStation
	 * @param orderManager The OrderManager of this PackingStation
	 * @param robots       The robots this PackingStation has access to
	 */
	public PackingStation(GridLocation position, OrderManager orderManager, List<Robot> robots) {
		super(position, IDENTIFIER + id);
		id++;
		orders = orderManager;
		this.robots = robots;
	}

	/**
	 * Accepts an incoming collection from a Robot
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Actor#act()
	 */
	@Override
	public void act() {
		if (!hasOrder()) {
			WarehouseSimulation.addReportEntry("PACKINGSTATION - "+ getUID() + ": Getting Order");
			// If we don't currently have an order, take next one from the list
			takeOrder();
		} 
		if(hasOrder()) {
			// Update time spent processing for reporting
			currentOrder.incrementTimeProcessing();

			if (!currentOrder.isComplete()) {
				if (itemInProgress == null) {
					requestRobot(currentOrder.getNextItem());
					WarehouseSimulation.addReportEntry("PACKINGSTATION - "+ getUID() + ": Requesting Robot");
				}
				// Robot is collecting item wait
			} else if (!currentOrder.isPacked()) {
				WarehouseSimulation.addReportEntry("PACKINGSTATION - "+ getUID() + ": Packing Order");
				// Once has all items, takes x ticks packing
				packOrder();
			} else {
				WarehouseSimulation.addReportEntry("PACKINGSTATION - "+ getUID() + ": Dispatching Order");
				// Dispatch for delivery
				dispatchForDelivery();
			}
		}
	}

	/**
	 * Dispatch the current order for delivery
	 */
	private void dispatchForDelivery() {
		orders.dispatchOrder(currentOrder);
		currentOrder = null;
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
	 * Gets if this PackingStation currently has an order
	 * 
	 * @return if we have an order in progress
	 */
	private boolean hasOrder() {
		return currentOrder != null;
	}

	/**
	 * Takes a new order to process
	 */
	private void takeOrder() {
		currentOrder = orders.getNextOrder();
	}

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
	 * Packs the current order
	 */
	private void packOrder() {
		currentOrder.decrementTicksToPack();
	}

	public static void resetIdCount() {
		id = 0;		
	}
	
}