/**
 * 
 */
package model;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class OrderTest {
	@Test
	public void testGetShelves() {
		StorageShelf[] shelves = { new StorageShelf(0,0), new StorageShelf(0,0), new StorageShelf(0,0) };
		Order order = new Order(shelves);
		assertArrayEquals(shelves, order.getShelves());
	}
}