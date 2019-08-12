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
	private static final String identifier = "r";
	private Robot robotOne;

	@Before
	public void setup() {
		robotOne = new Robot(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(robotOne.getUID().matches("^" + identifier + "[0-9]+$"));
	}

	@Test
	public void testNewRobotIncrementsUID() {
		int currentUID = Integer.parseInt(robotOne.getUID().replaceFirst(identifier, ""));
		Robot robotTwo = new Robot(0, 1);
		assertEquals(identifier + ++currentUID, robotTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, robotOne.getPosition());
	}
}
