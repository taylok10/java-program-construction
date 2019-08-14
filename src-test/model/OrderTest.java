/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class OrderTest {
	private static final Cell LOCATION_ONE = new Cell(0,0);
	private static final Cell LOCATION_TWO = new Cell(0,1);
	
	@Test
	public void testAddItemToEmptyList() {
		Order emptyOrder = new Order(1, new StorageShelf[] {});
		StorageShelf shelf = new StorageShelf(LOCATION_ONE);
		emptyOrder.addItem(shelf);
		assertEquals(1, emptyOrder.outstandingItemsSize());
		assertEquals(shelf, emptyOrder.getNextItem().getLocation());
	}

	@Test
	public void testAddDuplicateItem() {
		StorageShelf shelf = new StorageShelf(LOCATION_ONE);
		Order order = new Order(1, new StorageShelf[] { shelf });
		order.addItem(shelf);
		assertEquals(1, order.outstandingItemsSize());
		assertEquals(2, order.getNextItem().getQty());
	}

	@Test
	public void testGetTicksToPack() {
		Order order = new Order(3, new StorageShelf[] {});
		assertEquals(3, order.getTicksToPack());
	}

	@Test
	public void testDecrementTicksToPack() {
		Order order = new Order(3, new StorageShelf[] {});
		assertTrue(order.decrementTicksToPack());
		assertEquals(2, order.getTicksToPack());
		assertTrue(order.decrementTicksToPack());
		assertEquals(1, order.getTicksToPack());
		assertTrue(order.decrementTicksToPack());
		assertEquals(0, order.getTicksToPack());
	}

	@Test
	public void testDecrementTicksToPackBelowZero() {
		Order order = new Order(0, new StorageShelf[] {});
		assertFalse(order.decrementTicksToPack());
		assertEquals(0, order.getTicksToPack());
	}

	@Test
	public void testGetTimeProcessing() {
		Order order = new Order(0, new StorageShelf[] {});
		assertEquals(0, order.getTimeProcessing());
	}

	@Test
	public void testIncrementTimeProcessing() {
		Order order = new Order(0, new StorageShelf[] {});
		order.incrementTimeProcessing();
		assertEquals(1, order.getTimeProcessing());
		order.incrementTimeProcessing();
		assertEquals(2, order.getTimeProcessing());
	}

	@Test
	public void testGetNextItem() {
		StorageShelf shelf = new StorageShelf(LOCATION_ONE);
		Order order = new Order(0, new StorageShelf[] { shelf });
		assertEquals(shelf, order.getNextItem().getLocation());
	}

	@Test
	public void testGetNextItemWhenNoMoreItems() {
		Order order = new Order(0, new StorageShelf[] {});
		assertNull(order.getNextItem());
	}

	@Test
	public void testIsComplete() {
		Order order = new Order(0, new StorageShelf[] {});
		assertTrue(order.isComplete());
		order.addItem(new StorageShelf(LOCATION_ONE));
		assertFalse(order.isComplete());
	}

	@Test
	public void testIsPacked() {
		Order order = new Order(1, new StorageShelf[] {});
		assertFalse(order.isPacked());
		order.decrementTicksToPack();
		assertTrue(order.isPacked());
	}

	@Test
	public void testProcessOrder() {
		StorageShelf shelf = new StorageShelf(LOCATION_ONE);
		Order order = new Order(1, new StorageShelf[] { shelf });
		assertTrue(order.processItem(order.getNextItem()));
		assertEquals(0, order.outstandingItemsSize());
		assertEquals(1, order.processedItemsSize());
	}

	@Test
	public void testProcessOrderInvalidOrder() {
		StorageShelf shelf = new StorageShelf(LOCATION_ONE);
		StorageShelf shelfTwo = new StorageShelf(LOCATION_TWO);
		Order order = new Order(1, new StorageShelf[] { shelf });
		assertFalse(order.processItem(new OrderItem(shelfTwo, 1)));
		assertEquals(1, order.outstandingItemsSize());
		assertEquals(0, order.processedItemsSize());
	}
}