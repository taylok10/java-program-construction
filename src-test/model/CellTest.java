/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class CellTest {
	private static final Cell LOCATION_ONE = new Cell(0,0);
	private static final Cell LOCATION_TWO = new Cell(0,1);
	private ChargingPod chargingPod = new ChargingPod(LOCATION_ONE, 1);
	private PathFinder pathFinder = new BFS(new Cell[4][4]);
	private Cell cell;
	private OrderManager orderManager = new OrderManager();
	private ArrayList<Robot> robots = new ArrayList<Robot>();
	
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
		Robot robot = new Robot(LOCATION_ONE, 1, chargingPod, pathFinder);
		assertTrue(cell.addActor(robot));
		assertTrue(cell.getActors().contains(robot));
	}
	
	@Test
	public void testAddPackingStation() {
		PackingStation packingStation = new PackingStation(LOCATION_ONE, orderManager, robots);
		assertTrue(cell.addActor(packingStation));
		assertTrue(cell.getActors().contains(packingStation));
	}
	
	@Test
	public void testAddStorageShelf() {
		StorageShelf storageShelf = new StorageShelf(LOCATION_ONE);
		assertTrue(cell.addActor(storageShelf));
		assertTrue(cell.getActors().contains(storageShelf));
	}
	
	@Test
	public void testAddChargingPod() {
		ChargingPod chargingPod = new ChargingPod(LOCATION_ONE, 1);
		assertTrue(cell.addActor(chargingPod));
		assertTrue(cell.getActors().contains(chargingPod));
	}
	
	@Test
	public void testIsBlockedTrue() {
		Robot robot = new Robot(LOCATION_ONE, 1, chargingPod, pathFinder);
		cell.addActor(robot);
		assertTrue(cell.isBlocked());
	}
	
	@Test
	public void testIsBlockedFalse() {
		ChargingPod chargingPod = new ChargingPod(LOCATION_ONE, 1);
		cell.addActor(chargingPod);
		assertFalse(cell.isBlocked());
		
		StorageShelf storageShelf = new StorageShelf(LOCATION_ONE);
		cell.addActor(storageShelf);
		assertFalse(cell.isBlocked());
		
		PackingStation packingStation = new PackingStation(LOCATION_ONE, orderManager, robots);
		cell.addActor(packingStation);
		assertFalse(cell.isBlocked());
	}
	
	@Test
	public void testPreventAddingDuplicateRobots() {
		Robot robotOne = new Robot(LOCATION_ONE, 1, chargingPod, pathFinder);
		Robot robotTwo = new Robot(LOCATION_ONE, 1, chargingPod, pathFinder);
		cell.addActor(robotOne);
		assertFalse(cell.addActor(robotTwo));
	}
}