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
	private static final Cell LOCATION_ONE = new Cell(0,0);
	private static final Cell LOCATION_TWO = new Cell(0,1);
	private Robot robotOne;
	private ChargingPod chargingPod = new ChargingPod(LOCATION_ONE, 1);
	private ChargingPod chargingPodTwo = new ChargingPod(LOCATION_TWO, 1);
	private PathFinder pathFinder = new BFS(new Cell[4][4]);

	@Before
	public void setup() {
		robotOne = new Robot(LOCATION_ONE, 1, chargingPod, pathFinder);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(robotOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewRobotIncrementsUID() {
		int currentUID = Integer.parseInt(robotOne.getUID().replaceFirst(IDENTIFIER, ""));
		Robot robotTwo = new Robot(LOCATION_TWO, 1, chargingPodTwo, pathFinder);
		assertEquals(IDENTIFIER + ++currentUID, robotTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(LOCATION_ONE, robotOne.getPosition());
	}
}
