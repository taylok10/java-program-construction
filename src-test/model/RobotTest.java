/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class RobotTest {
	private static final String IDENTIFIER = "r";
	private Robot robotOne;
	private PathFinder<GridLocation> basicPathFinder;
	private Cell[][] basicGrid;
	private Cell locationOne;
	private Cell locationTwo;
	private Cell locationThree;
	private Cell locationFour;
	private OrderManager orderManager;

	@Before
	public void setup() {
		robotOne = new Robot(MockWarehouse.LOCATION_ONE, 20, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);

		// Locations
		locationOne = new Cell(0, 0);
		locationTwo = new Cell(0, 1);
		locationThree = new Cell(1, 0);
		locationFour = new Cell(1, 1);

		// Basic grid
		basicGrid = new Cell[][] { { locationOne, locationTwo }, { locationThree, locationFour } };

		// Basic path finding
		basicPathFinder = new BFS<GridLocation>(basicGrid);

		// Order manager
		orderManager = new OrderManager();
	}

	@Test
	public void testUIDFormat() {
		assertTrue(robotOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewRobotIncrementsUID() {
		int currentUID = Integer.parseInt(robotOne.getUID().replaceFirst(IDENTIFIER, ""));
		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, 1, MockWarehouse.CHARGING_POD,
				MockWarehouse.PATH_FINDER);
		assertEquals(IDENTIFIER + ++currentUID, robotTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(MockWarehouse.LOCATION_ONE, robotOne.getPosition());
	}

	@Test
	public void testResetId() {
		Robot.resetIdCount();
		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, 1, MockWarehouse.CHARGING_POD,
				MockWarehouse.PATH_FINDER);
		assertEquals("0", robotTwo.getUID().replaceFirst(IDENTIFIER, ""));
	}

	@Test
	public void testChargeBattery() {
		robotOne.setBattery(1);
		robotOne.setRobotState(RobotState.CHARGING);
		robotOne.chargeBattery(1);
		assertEquals(2, robotOne.getBattery());
	}

	@Test
	public void testChargeBatteryMultipleIncrement() {
		robotOne.setBattery(1);
		robotOne.setRobotState(RobotState.CHARGING);
		robotOne.chargeBattery(5);
		assertEquals(6, robotOne.getBattery());
	}

	@Test
	public void testChargeBatteryWhenBatteryFull() {
		robotOne.setRobotState(RobotState.CHARGING);
		robotOne.chargeBattery(5);
		assertEquals(20, robotOne.getBattery());
	}

	@Test
	public void testChargeBatteryWhenNotCharging() {
		robotOne.setBattery(1);
		robotOne.chargeBattery(1);
		assertEquals(1, robotOne.getBattery());
	}

	@Test
	public void testCreateRobot() {
		int battery = 20;

		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, battery, MockWarehouse.CHARGING_POD,
				MockWarehouse.PATH_FINDER);
		assertEquals(battery, robotTwo.getBattery());
		assertEquals(battery, robotTwo.getMaxBattery());
		assertEquals(RobotState.IDLE, robotTwo.getState());
		assertEquals(MockWarehouse.CHARGING_POD, robotTwo.getChargingPod());
		assertEquals(MockWarehouse.PATH_FINDER, robotTwo.getPathFinder());
	}

	@Test
	public void testDeliverItemWhenNoCurrentRequest() {
		assertFalse(robotOne.deliverItemToPackingStation());
	}

	@Test
	public void testDeliverItemToPackingStation() {
		OrderManager orders = new OrderManager();
		Order orderOne = new Order(1, new StorageShelf[] { MockWarehouse.SHELF_ONE });
		orders.addOrder(orderOne);
		ArrayList<Robot> robots = new ArrayList<Robot>();
		robots.add(robotOne);
		PackingStation packingStation = new PackingStation(MockWarehouse.LOCATION_ONE, orders, robots);
		packingStation.act();
		assertTrue(robotOne.deliverItemToPackingStation());
		assertNull(robotOne.getCurrentAssignment());
	}

	@Test
	public void testDeliverItemRejectedByPackingStation() {
		OrderManager orders = new OrderManager();
		Order orderOne = new Order(1, new StorageShelf[] { MockWarehouse.SHELF_ONE });
		orders.addOrder(orderOne);
		ArrayList<Robot> robots = new ArrayList<Robot>();
		robots.add(robotOne);
		PackingStation packingStation = new PackingStation(MockWarehouse.LOCATION_ONE, orders, robots);
		packingStation.act();
		robotOne.setCurrentAssignment(new Assignment(packingStation, MockWarehouse.SHELF_TWO));
		assertFalse(robotOne.deliverItemToPackingStation());
	}

	@Test
	public void testAssignmentRequest() {
		Assignment assignment = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_TWO);
		assertTrue(robotOne.assignmentRequest(assignment));
		assertEquals(assignment, robotOne.getCurrentAssignment());
	}

	@Test
	public void testAssignmentRequestWhileCharging() {
		Assignment assignment = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_TWO);
		robotOne.setRobotState(RobotState.CHARGING);
		assertFalse(robotOne.assignmentRequest(assignment));
		assertNull(robotOne.getCurrentAssignment());
	}

	@Test
	public void testAssignmentRequestWhileHasRequest() {
		Assignment assignmentOne = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_TWO);
		Assignment assignmentTwo = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_THREE);
		robotOne.setCurrentAssignment(assignmentOne);
		assertFalse(robotOne.assignmentRequest(assignmentTwo));
		assertEquals(assignmentOne, robotOne.getCurrentAssignment());
	}

	@Test
	public void testAssignmentRequestWhileCannotHandleRequest() {
		robotOne.setBattery(1);
		Assignment assignment = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_FOUR);
		assertFalse(robotOne.assignmentRequest(assignment));
		assertNull(robotOne.getCurrentAssignment());
	}

	@Test
	public void testActIdle() {
		assertTrue(robotOne.act());
		assertEquals(RobotState.IDLE, robotOne.getState());
	}

	@Test
	public void testActCharging() {
		robotOne.setBattery(1);
		robotOne.setRobotState(RobotState.CHARGING);
		assertTrue(robotOne.act());
		assertEquals(RobotState.CHARGING, robotOne.getState());
	}

	@Test
	public void testActChargingBatteryFull() {
		robotOne.setRobotState(RobotState.CHARGING);
		assertTrue(robotOne.act());
		assertEquals(RobotState.IDLE, robotOne.getState());
	}

	@Test
	public void testActChargingBatteryFullWithEmergencyState() {
		robotOne.setRobotState(RobotState.CHARGING);
		robotOne.setEmergencyState(RobotState.DELIVERING_ITEM);
		assertTrue(robotOne.act());
		assertEquals(RobotState.DELIVERING_ITEM, robotOne.getState());
		assertNull(robotOne.getEmergencyBackupState());
	}

	@Test
	public void testReachedChargingPod() {
		Robot.resetIdCount();
		ChargingPod.resetIdCount();
		ChargingPod chargingPod = new ChargingPod(MockWarehouse.LOCATION_ONE, 1);
		Robot robot = new Robot(MockWarehouse.LOCATION_ONE, 20, chargingPod, MockWarehouse.PATH_FINDER);

		robot.setRobotState(RobotState.RETURNING_TO_POD);
		assertTrue(robot.act());
		assertEquals(RobotState.CHARGING, robot.getState());
	}

	@Test
	public void testMovingTowardsChargingPod() {
		Robot.resetIdCount();
		ChargingPod.resetIdCount();
		ChargingPod chargingPod = new ChargingPod(locationOne, 1);
		Robot robot = new Robot(locationFour, 20, chargingPod, basicPathFinder);
		locationFour.addActor(robot);
		locationOne.addActor(chargingPod);

		robot.setRobotState(RobotState.RETURNING_TO_POD);
		assertTrue(robot.act());
		assertEquals(RobotState.RETURNING_TO_POD, robot.getState());
		assertNotEquals(locationFour, robot.getPosition());
	}

	@Test
	public void testReachedPackingStation() {
		Robot.resetIdCount();
		Robot robot = new Robot(MockWarehouse.LOCATION_ONE, 20, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
		ArrayList<Robot> robots = new ArrayList<Robot>();
		robots.add(robot);

		Order order = new Order(1, new StorageShelf[] { MockWarehouse.SHELF_ONE });
		orderManager.addOrder(order);
		PackingStation packingStation = new PackingStation(MockWarehouse.LOCATION_ONE, orderManager, robots);
		packingStation.act();
		robot.setCurrentAssignment(new Assignment(packingStation, MockWarehouse.SHELF_ONE));

		robot.setRobotState(RobotState.DELIVERING_ITEM);
		assertTrue(robot.act());
		assertEquals(RobotState.RETURNING_TO_POD, robot.getState());
		assertNull(robot.getCurrentAssignment());
		assertFalse(robot.getHasItem());
	}

	@Test
	public void testMovingTowardsPackingStation() {
		Robot.resetIdCount();
		Robot robot = new Robot(locationTwo, 20, MockWarehouse.CHARGING_POD, basicPathFinder);
		locationTwo.addActor(robot);

		ArrayList<Robot> robots = new ArrayList<Robot>();
		robots.add(robot);
		PackingStation packingStation = new PackingStation(locationThree, orderManager, robots);
		locationThree.addActor(packingStation);
		robot.setCurrentAssignment(new Assignment(packingStation, MockWarehouse.SHELF_ONE));

		robot.setRobotState(RobotState.DELIVERING_ITEM);
		assertTrue(robot.act());
		assertEquals(RobotState.DELIVERING_ITEM, robot.getState());
		assertNotEquals(locationTwo, robot.getPosition());
	}

	@Test
	public void testReachedShelf() {
		Robot.resetIdCount();
		Robot robot = new Robot(MockWarehouse.LOCATION_ONE, 20, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
		Assignment assignment = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_ONE);
		robot.setCurrentAssignment(assignment);

		robot.setRobotState(RobotState.COLLECTING_ITEM);
		assertTrue(robot.act());
		assertEquals(RobotState.DELIVERING_ITEM, robot.getState());
		assertEquals(assignment, robot.getCurrentAssignment());
		assertTrue(robot.getHasItem());
	}

	@Test
	public void testMovingTowardsShelf() {
		Robot.resetIdCount();
		Robot robot = new Robot(locationTwo, 20, MockWarehouse.CHARGING_POD, basicPathFinder);
		locationTwo.addActor(robot);

		StorageShelf shelf = new StorageShelf(locationThree);
		Assignment assignment = new Assignment(MockWarehouse.PACKING_STATION, shelf);
		robot.setCurrentAssignment(assignment);
		locationThree.addActor(shelf);

		robot.setRobotState(RobotState.COLLECTING_ITEM);
		assertTrue(robot.act());
		assertEquals(RobotState.COLLECTING_ITEM, robot.getState());
		assertNotEquals(locationTwo, robot.getPosition());
		assertFalse(robot.getHasItem());
	}
}