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
public class RobotTest {
	private Robot one;

	@Before
	public void setup() {
		one = new Robot(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(one.getUID().matches("^R[0-9]+$"));
	}

	@Test
	public void testNewRobotIncrementsUID() {
		int currentUID = Integer.parseInt(one.getUID().replaceFirst("R", ""));
		Robot two = new Robot(0, 1);
		assertEquals("R" + ++currentUID, two.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, one.getPosition());
	}
}
