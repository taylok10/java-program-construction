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
	private static final Cell LOCATION_ONE = new Cell(0,0);
	private static final Cell LOCATION_TWO = new Cell(0,1);
	private static final Cell LOCATION_THREE = new Cell(1,0);
	private static final Cell LOCATION_FOUR = new Cell(1,1);
	private PackingStation packingStationOne;
	private PackingStation packingStationTwo;
	private OrderManager orders;
	private Order orderOne = new Order(1, new StorageShelf[] { new StorageShelf(LOCATION_ONE), new StorageShelf(LOCATION_TWO) });
	private Order orderTwo = new Order(2, new StorageShelf[] { new StorageShelf(LOCATION_THREE), new StorageShelf(LOCATION_FOUR) });
	private ArrayList<Robot> robots;

	@Before
	public void setup() {
		// Setup orders
		orders = new OrderManager();
		orders.addOrder(orderOne);
		orders.addOrder(orderTwo);
		
		// Setup Robots
		robots = new ArrayList<Robot>();
		
		// Setup packing stations
		packingStationOne = new PackingStation(LOCATION_ONE, orders, robots);
		packingStationTwo = new PackingStation(LOCATION_TWO, orders, robots);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(packingStationOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewPackingStationIncrementsUID() {
		int currentUID = Integer.parseInt(packingStationTwo.getUID().replaceFirst(IDENTIFIER, ""));
		PackingStation packingStationThree = new PackingStation(LOCATION_THREE, orders, robots);
		assertEquals(IDENTIFIER + ++currentUID, packingStationThree.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(LOCATION_ONE, packingStationOne.getPosition());
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