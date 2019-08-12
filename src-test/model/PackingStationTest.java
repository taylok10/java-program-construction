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
public class PackingStationTest {
	private static final String identifier = "ps";
	private PackingStation packingStationOne;

	@Before
	public void setup() {
		packingStationOne = new PackingStation(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(packingStationOne.getUID().matches("^" + identifier + "[0-9]+$"));
	}

	@Test
	public void testNewPackingStationIncrementsUID() {
		int currentUID = Integer.parseInt(packingStationOne.getUID().replaceFirst(identifier, ""));
		PackingStation packingStationTwo = new PackingStation(0, 1);
		assertEquals(identifier + ++currentUID, packingStationTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, packingStationOne.getPosition());
	}
}