/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	//private PathFinder pathFinder = new BFS(new Cell[4][4]);

	@Before
	public void setup() {
		robotOne = new Robot(MockWarehouse.LOCATION_ONE, 20, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(robotOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewRobotIncrementsUID() {
		int currentUID = Integer.parseInt(robotOne.getUID().replaceFirst(IDENTIFIER, ""));
		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, 1, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
		assertEquals(IDENTIFIER + ++currentUID, robotTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(MockWarehouse.LOCATION_ONE, robotOne.getPosition());
	}
	
	@Test
	public void testResetId() {
		Robot.resetIdCount();
		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, 1, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
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
		
		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, battery, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
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
}