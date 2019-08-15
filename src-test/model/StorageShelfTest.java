/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class StorageShelfTest {
	private static final String IDENTIFIER = "ss";
	private StorageShelf storageShelfOne;

	@Before
	public void setup() {
		storageShelfOne = new StorageShelf(MockWarehouse.LOCATION_ONE);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(storageShelfOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewStorageShelfIncrementsUID() {
		int currentUID = Integer.parseInt(storageShelfOne.getUID().replaceFirst(IDENTIFIER, ""));
		StorageShelf storageShelfTwo = new StorageShelf(MockWarehouse.LOCATION_TWO);
		assertEquals(IDENTIFIER + ++currentUID, storageShelfTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(MockWarehouse.LOCATION_ONE, storageShelfOne.getPosition());
	}

	@Test
	public void testAct() {
		GridLocation positionBeforeAct = storageShelfOne.getPosition();
		String UIDBeforeAct = storageShelfOne.getUID();

		// Assert act does not fail
		assertTrue(storageShelfOne.act());
		// Assert nothing has changed on the StorageShelf
		assertEquals(positionBeforeAct, storageShelfOne.getPosition());
		assertEquals(UIDBeforeAct, storageShelfOne.getUID());
	}

	@Test
	public void testResetId() {
		StorageShelf.resetIdCount();
		StorageShelf storageShelfTwo = new StorageShelf(MockWarehouse.LOCATION_TWO);
		assertEquals("0", storageShelfTwo.getUID().replaceFirst(IDENTIFIER, ""));
	}
}