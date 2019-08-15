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
public class CellTest {
	private Cell cell;
	
	@Before
	public void setup() {
		cell = new Cell(0, 0);
	}
	
	@Test
	public void testCreateNewCell() {
		int row = 1;
		int column = 2;
		Cell cell = new Cell(column, row);
		assertEquals(column, cell.getColumn());
		assertEquals(row, cell.getRow());
		assertEquals(0, cell.getActors().size());
	}
	
	@Test
	public void testAddRobot() {
		assertTrue(cell.addActor(MockWarehouse.ROBOT));
		assertTrue(cell.getActors().contains(MockWarehouse.ROBOT));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testAddPackingStation() {
		assertTrue(cell.addActor(MockWarehouse.PACKING_STATION));
		assertTrue(cell.getActors().contains(MockWarehouse.PACKING_STATION));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testAddStorageShelf() {
		assertTrue(cell.addActor(MockWarehouse.SHELF_ONE));
		assertTrue(cell.getActors().contains(MockWarehouse.SHELF_ONE));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testAddChargingPod() {
		assertTrue(cell.addActor(MockWarehouse.CHARGING_POD));
		assertTrue(cell.getActors().contains(MockWarehouse.CHARGING_POD));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testRobotBlocksCell() {
		cell.addActor(MockWarehouse.ROBOT);
		assertTrue(cell.isBlocked());
	}
	
	@Test
	public void testChargingPodDoesNotBlockCell() {
		cell.addActor(MockWarehouse.CHARGING_POD);
		assertFalse(cell.isBlocked());
		cell.removeActor(MockWarehouse.CHARGING_POD);
	}
	
	@Test
	public void testStorageShelfDoesNotBlockCell() {
		cell.addActor(MockWarehouse.SHELF_ONE);
		assertFalse(cell.isBlocked());
		cell.removeActor(MockWarehouse.SHELF_ONE);
	}
	
	@Test
	public void testPackingStationDoesNotBlockCell() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertFalse(cell.isBlocked());
		cell.removeActor(MockWarehouse.PACKING_STATION);
	}
	
	@Test
	public void testPreventAddingDuplicateRobots() {
		Robot robotTwo = new Robot(MockWarehouse.LOCATION_TWO, 1, MockWarehouse.CHARGING_POD, MockWarehouse.PATH_FINDER);
		cell.addActor(MockWarehouse.ROBOT);
		assertFalse(cell.addActor(robotTwo));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testAllowRobotsToCohabitWithPackingStation() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertTrue(cell.addActor(MockWarehouse.ROBOT));
		assertEquals(2, cell.getActors().size());
	}
	
	@Test
	public void testAllowRobotsToCohabitWithShelf() {
		cell.addActor(MockWarehouse.SHELF_ONE);
		assertTrue(cell.addActor(MockWarehouse.ROBOT));
		assertEquals(2, cell.getActors().size());
	}
	
	@Test
	public void testAllowRobotsToCohabitWithChargingPod() {
		cell.addActor(MockWarehouse.CHARGING_POD);
		assertTrue(cell.addActor(MockWarehouse.ROBOT));
		assertEquals(2, cell.getActors().size());
	}
	
	@Test
	public void testEquals() {
		assertTrue(cell.equals(MockWarehouse.LOCATION_ONE));
	}
	
	@Test
	public void testEqualsFalse() {
		assertFalse(cell.equals(MockWarehouse.LOCATION_TWO));
	}
	
	@Test
	public void testEqualsNonCell() {
		assertFalse(cell.equals(MockWarehouse.ROBOT));
	}
	
	@Test
	public void testRemovingActorByType() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertTrue(cell.removeActor("PackingStation"));
		assertEquals(0, cell.getActors().size());
	}
	
	@Test
	public void testRemovingActorOnNonAddedActorByType() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertFalse(cell.removeActor("Robot"));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testRemovingActor() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertTrue(cell.removeActor(MockWarehouse.PACKING_STATION));
		assertEquals(0, cell.getActors().size());
	}
	
	@Test
	public void testRemovingActorOnNonAddedActor() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertFalse(cell.removeActor(MockWarehouse.ROBOT));
		assertEquals(1, cell.getActors().size());
	}
	
	@Test
	public void testGetActorsDescWhenEmpty() {
		assertEquals("", cell.getActorsDesc());
	}
	
	@Test
	public void testGetActorsDesc() {
		cell.addActor(MockWarehouse.ROBOT);
		assertEquals("Actors Present:\nr0\n", cell.getActorsDesc());
	}
	
	@Test
	public void testGetRobot() {
		cell.addActor(MockWarehouse.ROBOT);
		assertEquals(MockWarehouse.ROBOT, cell.getRobot());
	}
	
	@Test
	public void testGetRobotWhenNoRobot() {
		cell.addActor(MockWarehouse.PACKING_STATION);
		assertNull(cell.getRobot());
	}
}