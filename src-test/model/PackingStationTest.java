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
	private PackingStation one;

	@Before
	public void setup() {
		one = new PackingStation(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(one.getUID().matches("^P[0-9]+$"));
	}

	@Test
	public void testNewPackingStationIncrementsUID() {
		int currentUID = Integer.parseInt(one.getUID().replaceFirst("P", ""));
		PackingStation two = new PackingStation(0, 1);
		assertEquals("P" + ++currentUID, two.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, one.getPosition());
	}
}