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
	private static final String identifier = "ss";
	private StorageShelf storageShelfOne;

	@Before
	public void setup() {
		storageShelfOne = new StorageShelf(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(storageShelfOne.getUID().matches("^" + identifier + "[0-9]+$"));
	}

	@Test
	public void testNewStorageShelfIncrementsUID() {
		int currentUID = Integer.parseInt(storageShelfOne.getUID().replaceFirst(identifier, ""));
		StorageShelf storageShelfTwo = new StorageShelf(0, 1);
		assertEquals(identifier + ++currentUID, storageShelfTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, storageShelfOne.getPosition());
	}
}