/**
 * 
 */
package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class StorageShelfTest {
	private StorageShelf one;

	@Before
	public void setup() {
		one = new StorageShelf(0, 0);
	}

	@Test
	public void testNewStorageShelfIncrementsUID() {
		assertEquals("ss0", one.getUID());
		StorageShelf two = new StorageShelf(0, 1);
		assertEquals("ss1", two.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, one.getPosition());
	}
}