/**
 * 
 */
package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	public void testUIDFormat() {
		assertTrue(one.getUID().matches("^S[0-9]+$"));
	}

	@Test
	public void testNewStorageShelfIncrementsUID() {
		int currentUID = Integer.parseInt(one.getUID().replaceFirst("S", ""));
		StorageShelf two = new StorageShelf(0, 1);
		assertEquals("S" + ++currentUID, two.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, one.getPosition());
	}
}