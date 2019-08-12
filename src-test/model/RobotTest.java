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
	private static final String IDENTIFIER = "r";
	private Robot robotOne;

	@Before
	public void setup() {
		robotOne = new Robot(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(robotOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewRobotIncrementsUID() {
		int currentUID = Integer.parseInt(robotOne.getUID().replaceFirst(IDENTIFIER, ""));
		Robot robotTwo = new Robot(0, 1);
		assertEquals(IDENTIFIER + ++currentUID, robotTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, robotOne.getPosition());
	}
}
