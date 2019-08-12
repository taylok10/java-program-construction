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
	private static final String IDENTIFIER = "ss";
	private StorageShelf storageShelfOne;

	@Before
	public void setup() {
		storageShelfOne = new StorageShelf(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(storageShelfOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewStorageShelfIncrementsUID() {
		int currentUID = Integer.parseInt(storageShelfOne.getUID().replaceFirst(IDENTIFIER, ""));
		StorageShelf storageShelfTwo = new StorageShelf(0, 1);
		assertEquals(IDENTIFIER + ++currentUID, storageShelfTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, storageShelfOne.getPosition());
	}
	
	@Test
	public void testAct() {
		int[] positionBeforeAct = storageShelfOne.getPosition();
		String UIDBeforeAct = storageShelfOne.getUID();
		storageShelfOne.act();
		
		//Assert nothing has changed on the StorageShelf
		assertEquals(positionBeforeAct, storageShelfOne.getPosition());
		assertEquals(UIDBeforeAct, storageShelfOne.getUID());
	}
}