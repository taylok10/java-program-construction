/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class PackingStationTest {
	private static final String IDENTIFIER = "ps";
	private static final OrderManager EMPTY_ORDERS = new OrderManager();

	private PackingStation packingStationOne;
	private PackingStation packingStationTwo;
	private OrderManager orders;
	private Order orderOne;
	private Order orderTwo;
	private ArrayList<Robot> robots;
	private ChargingPod chargingPodOne;
	private ChargingPod chargingPodTwo;
	private Robot robotOne;
	private Robot robotTwo;

	@Before
	public void setup() {
		// Setup orders
		orders = new OrderManager();
		orderOne = new Order(1, new StorageShelf[] { MockWarehouse.SHELF_ONE, MockWarehouse.SHELF_TWO });
		orderTwo = new Order(2, new StorageShelf[] { MockWarehouse.SHELF_THREE });
		orders.addOrder(orderOne);
		orders.addOrder(orderTwo);

		// Setup Robots
		robots = new ArrayList<Robot>();
		chargingPodOne = new ChargingPod(MockWarehouse.LOCATION_ONE, 1);
		chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		robotOne = new Robot(MockWarehouse.LOCATION_ONE, 99, chargingPodOne, MockWarehouse.PATH_FINDER);
		robotTwo = new Robot(MockWarehouse.LOCATION_TWO, 99, chargingPodTwo, MockWarehouse.PATH_FINDER);
		robots.add(robotOne);
		robots.add(robotTwo);

		// Setup packing stations
		packingStationOne = new PackingStation(MockWarehouse.LOCATION_ONE, orders, robots);
		packingStationTwo = new PackingStation(MockWarehouse.LOCATION_TWO, orders, robots);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(packingStationOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewPackingStationIncrementsUID() {
		int currentUID = Integer.parseInt(packingStationTwo.getUID().replaceFirst(IDENTIFIER, ""));
		PackingStation packingStationThree = new PackingStation(MockWarehouse.LOCATION_THREE, orders, robots);
		assertEquals(IDENTIFIER + ++currentUID, packingStationThree.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(MockWarehouse.LOCATION_ONE, packingStationOne.getPosition());
	}

	@Test
	public void testTakeOrder() {
		assertEquals(null, packingStationOne.getCurrentOrder());
		packingStationOne.act();
		assertEquals(orderOne, packingStationOne.getCurrentOrder());
	}

	@Test
	public void testOrdersIsSharedByPackingStations() {
		packingStationOne.act();
		packingStationTwo.act();
		assertEquals(orderOne, packingStationOne.getCurrentOrder());
		assertEquals(orderTwo, packingStationTwo.getCurrentOrder());
	}

	@Test
	public void testResetId() {
		PackingStation.resetIdCount();
		PackingStation packingStationThree = new PackingStation(MockWarehouse.LOCATION_THREE, orders, robots);
		assertEquals("0", packingStationThree.getUID().replaceFirst(IDENTIFIER, ""));
	}

	@Test
	public void testIsFinishedWhenComplete() {
		PackingStation packingStationThree = new PackingStation(MockWarehouse.LOCATION_THREE, EMPTY_ORDERS, robots);
		assertTrue(packingStationThree.act());
		assertTrue(packingStationThree.isFinished());
	}

	@Test
	public void testIsFinishedWhenOutstandingOrders() {
		PackingStation packingStationThree = new PackingStation(MockWarehouse.LOCATION_THREE, orders, robots);
		assertTrue(packingStationThree.act());
		assertFalse(packingStationThree.isFinished());
	}

	@Test
	public void testIncrementsOrderProcessingTime() {
		assertTrue(packingStationOne.act());
		assertEquals(1, packingStationOne.getCurrentOrder().getTimeProcessing());
		assertTrue(packingStationOne.act());
		assertEquals(2, packingStationOne.getCurrentOrder().getTimeProcessing());
	}

	@Test
	public void testGetsNextItemCorrectly() {
		assertTrue(packingStationOne.act());
		assertEquals(MockWarehouse.SHELF_ONE, packingStationOne.getItemInProgress().getLocation());
	}

	@Test
	public void testNoAvilableRobotsForNextItem() {
		PackingStation packingStationThree = new PackingStation(MockWarehouse.LOCATION_THREE, orders,
				new ArrayList<Robot>());
		assertTrue(packingStationThree.act());
		assertNull(packingStationThree.getItemInProgress());
	}

	@Test
	public void testWaitsOnRobotCorrectly() {
		assertTrue(packingStationOne.act());
		assertEquals(MockWarehouse.SHELF_ONE, packingStationOne.getItemInProgress().getLocation());
		assertTrue(packingStationOne.act());
		assertEquals(MockWarehouse.SHELF_ONE, packingStationOne.getItemInProgress().getLocation());
	}

	@Test
	public void testRejectDeliveryForIncorrectItem() {
		packingStationOne.act();
		assertFalse(packingStationOne.acceptItemDelivery(MockWarehouse.SHELF_TWO));
	}

	@Test
	public void testAcceptDeliveryForCorrectItem() {
		packingStationOne.act();
		assertTrue(packingStationOne.acceptItemDelivery(MockWarehouse.SHELF_ONE));
		assertNull(packingStationOne.getItemInProgress());
	}

	@Test
	public void testPicksUpAllItemsBeforePacking() {
		packingStationOne.act();
		packingStationOne.acceptItemDelivery(packingStationOne.getItemInProgress().getLocation());

		assertTrue(packingStationOne.act());
		assertEquals(MockWarehouse.SHELF_TWO, packingStationOne.getItemInProgress().getLocation());
	}

	@Test
	public void testPackItemUntilPacked() {
		packingStationOne.act();
		packingStationTwo.act();
		packingStationTwo.acceptItemDelivery(packingStationTwo.getItemInProgress().getLocation());

		assertEquals(2, packingStationTwo.getCurrentOrder().getTicksToPack());
		packingStationTwo.act();
		assertEquals(1, packingStationTwo.getCurrentOrder().getTicksToPack());
		assertFalse(packingStationTwo.getCurrentOrder().isPacked());
		packingStationTwo.act();
		assertEquals(0, packingStationTwo.getCurrentOrder().getTicksToPack());
		assertTrue(packingStationTwo.getCurrentOrder().isPacked());
	}

	@Test
	public void testDispatchesForDeliveryWhenPacked() {
		packingStationOne.act();
		packingStationOne.acceptItemDelivery(packingStationOne.getItemInProgress().getLocation());
		packingStationOne.act();
		packingStationOne.acceptItemDelivery(packingStationOne.getItemInProgress().getLocation());
		packingStationOne.act();

		assertTrue(packingStationOne.act());
		assertNull(packingStationOne.getCurrentOrder());
	}
}