/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		Robot robot = new Robot(0, 0);
		assertTrue(cell.addActor(robot));
		assertTrue(cell.getActors().contains(robot));
	}
	
	@Test
	public void testAddPackingStation() {
		PackingStation packingStation = new PackingStation(0, 0);
		assertTrue(cell.addActor(packingStation));
		assertTrue(cell.getActors().contains(packingStation));
	}
	
	@Test
	public void testAddStorageShelf() {
		StorageShelf storageShelf = new StorageShelf(0, 0);
		assertTrue(cell.addActor(storageShelf));
		assertTrue(cell.getActors().contains(storageShelf));
	}
	
	@Test
	public void testAddChargingPod() {
		ChargingPod chargingPod = new ChargingPod(0, 0);
		assertTrue(cell.addActor(chargingPod));
		assertTrue(cell.getActors().contains(chargingPod));
	}
	
	@Test
	public void testIsBlockedTrue() {
		Robot robot = new Robot(0, 0);
		cell.addActor(robot);
		assertTrue(cell.isBlocked());
	}
	
	@Test
	public void testIsBlockedFalse() {
		ChargingPod chargingPod = new ChargingPod(0, 0);
		cell.addActor(chargingPod);
		assertFalse(cell.isBlocked());
		
		StorageShelf storageShelf = new StorageShelf(0, 0);
		cell.addActor(storageShelf);
		assertFalse(cell.isBlocked());
		
		PackingStation packingStation = new PackingStation(0, 0);
		cell.addActor(packingStation);
		assertFalse(cell.isBlocked());
	}
	
	@Test
	public void testPreventAddingDuplicateRobots() {
		Robot robotOne = new Robot(0, 0);
		Robot robotTwo = new Robot(0, 0);
		cell.addActor(robotOne);
		assertFalse(cell.addActor(robotTwo));
	}
}