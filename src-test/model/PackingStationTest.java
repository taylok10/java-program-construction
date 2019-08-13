/**
 * 
 */
package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class PackingStationTest {
	private static final String IDENTIFIER = "ps";
	private PackingStation packingStationOne;
	private PackingStation packingStationTwo;
	private OrderManager orders;
	private Order orderOne = new Order(1, new StorageShelf[] { new StorageShelf(0, 0), new StorageShelf(0, 1) });
	private Order orderTwo = new Order(2, new StorageShelf[] { new StorageShelf(1, 0), new StorageShelf(1, 1) });
	private ArrayList<Actor> robots;

	@Before
	public void setup() {
		// Setup orders
		orders = new OrderManager();
		orders.addOrder(orderOne);
		orders.addOrder(orderTwo);
		
		// Setup Robots
		robots = new ArrayList<Actor>();
		
		// Setup packing stations
		packingStationOne = new PackingStation(0, 0, orders, robots);
		packingStationTwo = new PackingStation(0, 1, orders, robots);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(packingStationOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewPackingStationIncrementsUID() {
		int currentUID = Integer.parseInt(packingStationTwo.getUID().replaceFirst(IDENTIFIER, ""));
		PackingStation packingStationThree = new PackingStation(0, 2, orders, robots);
		assertEquals(IDENTIFIER + ++currentUID, packingStationThree.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, packingStationOne.getPosition());
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
}