/**
 * 
 */
package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	private Queue<Order> orders = new LinkedList<Order>();
	private Order orderOne = new Order(new StorageShelf[] { new StorageShelf(0, 0), new StorageShelf(0, 1) });
	private Order orderTwo = new Order(new StorageShelf[] { new StorageShelf(1, 0), new StorageShelf(1, 1) });

	@Before
	public void setup() {
		// Setup orders
		orders.clear();
		orders.add(orderOne);
		orders.add(orderTwo);

		// Setup packing stations
		packingStationOne = new PackingStation(0, 0, orders);
		packingStationTwo = new PackingStation(0, 1, orders);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(packingStationOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewPackingStationIncrementsUID() {
		int currentUID = Integer.parseInt(packingStationTwo.getUID().replaceFirst(IDENTIFIER, ""));
		PackingStation packingStationThree = new PackingStation(0, 2, orders);
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