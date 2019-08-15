/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class ChargingPodTest {
	private static final String IDENTIFIER = "c";
	private ChargingPod chargingPodOne;

	@Before
	public void setup() {
		chargingPodOne = new ChargingPod(MockWarehouse.LOCATION_ONE, 1);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(chargingPodOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewChargingPodIncrementsUID() {
		int currentUID = Integer.parseInt(chargingPodOne.getUID().replaceFirst(IDENTIFIER, ""));
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		assertEquals(IDENTIFIER + ++currentUID, chargingPodTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(MockWarehouse.LOCATION_ONE, chargingPodOne.getPosition());
	}

	@Test
	public void testResetId() {
		ChargingPod.resetIdCount();
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		assertEquals("0", chargingPodTwo.getUID().replaceFirst(IDENTIFIER, ""));
	}

	@Test
	public void testDockWithAssignedRobot() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();

		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 1, chargingPodTwo, MockWarehouse.PATH_FINDER);

		// Should dock successfully
		assertTrue(chargingPodTwo.dockRobot(robot));
		assertEquals(robot, chargingPodTwo.getDockedRobot());
	}

	@Test
	public void testDockWithNonAssignedRobot() {
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot.resetIdCount();
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 1, chargingPodTwo, MockWarehouse.PATH_FINDER);

		// Should not allow
		assertFalse(chargingPodTwo.dockRobot(robot));
		assertNull(chargingPodTwo.getDockedRobot());
	}

	@Test
	public void testDockWithAssignedRobotInDifferentLocation() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();

		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_ONE, 1, chargingPodTwo, MockWarehouse.PATH_FINDER);

		// Should not allow
		assertFalse(chargingPodTwo.dockRobot(robot));
		assertNull(chargingPodTwo.getDockedRobot());
	}

	@Test
	public void testDockWithRobotAlreadyDocked() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();

		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 1, chargingPodTwo, MockWarehouse.PATH_FINDER);

		// Should not cause an issue
		assertTrue(chargingPodTwo.dockRobot(robot));
		assertEquals(robot, chargingPodTwo.getDockedRobot());
		assertFalse(chargingPodTwo.dockRobot(robot));
		assertEquals(robot, chargingPodTwo.getDockedRobot());
	}

	@Test
	public void testUndockWithDockedRobot() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 1, chargingPodTwo, MockWarehouse.PATH_FINDER);
		chargingPodTwo.dockRobot(robot);

		chargingPodTwo.undockRobot();
		assertNull(chargingPodTwo.getDockedRobot());
	}

	@Test
	public void testUndockWithNoRobot() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 1, chargingPodTwo, MockWarehouse.PATH_FINDER);
		chargingPodTwo.dockRobot(robot);

		// Should cause any issues
		chargingPodTwo.undockRobot();
		assertNull(chargingPodTwo.getDockedRobot());
	}

	@Test
	public void testActWithNoRobot() {
		// Should not cause an issue
		assertTrue(chargingPodOne.act());
	}

	@Test
	public void testActWithRobot() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 5, chargingPodTwo, MockWarehouse.PATH_FINDER);
		chargingPodTwo.dockRobot(robot);
		robot.setBattery(1);
		robot.setRobotState(RobotState.CHARGING);

		assertTrue(chargingPodTwo.act());
		assertEquals(2, robot.getBattery());
	}

	@Test
	public void testActWithRobotMultipleChargesPerTick() {
		ChargingPod.resetIdCount();
		Robot.resetIdCount();
		ChargingPod chargingPodTwo = new ChargingPod(MockWarehouse.LOCATION_TWO, 2);
		Robot robot = new Robot(MockWarehouse.LOCATION_TWO, 5, chargingPodTwo, MockWarehouse.PATH_FINDER);
		chargingPodTwo.dockRobot(robot);
		robot.setBattery(1);
		robot.setRobotState(RobotState.CHARGING);

		assertTrue(chargingPodTwo.act());
		assertEquals(3, robot.getBattery());
	}
}