/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class BFSTest {
	private final Cell[][] emptyGrid = {
			{ new Cell(0, 0), new Cell(1, 0), new Cell(2, 0), new Cell(3, 0), new Cell(4, 0), new Cell(5, 0) },
			{ new Cell(0, 1), new Cell(1, 1), new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1) },
			{ new Cell(0, 2), new Cell(1, 2), new Cell(2, 2), new Cell(3, 2), new Cell(4, 2), new Cell(5, 2) },
			{ new Cell(0, 3), new Cell(1, 3), new Cell(2, 3), new Cell(3, 3), new Cell(4, 3), new Cell(5, 3) },
			{ new Cell(0, 4), new Cell(1, 4), new Cell(2, 4), new Cell(3, 4), new Cell(4, 4), new Cell(5, 4) },
			{ new Cell(0, 5), new Cell(1, 5), new Cell(2, 5), new Cell(3, 5), new Cell(4, 5), new Cell(5, 5) } };
	private Cell[][] trickyGrid;

	@Before
	public void setup() {
		trickyGrid = new Cell[][] {
			{ new Cell(0, 0), new Cell(1, 0), new Cell(2, 0), new Cell(3, 0), new Cell(4, 0), new Cell(5, 0) },
			{ new Cell(0, 1), new Cell(1, 1), new Cell(2, 1), new Cell(3, 1), new Cell(4, 1), new Cell(5, 1) },
			{ new Cell(0, 2), new Cell(1, 2), new Cell(2, 2), new Cell(3, 2), new Cell(4, 2), new Cell(5, 2) },
			{ new Cell(0, 3), new Cell(1, 3), new Cell(2, 3), new Cell(3, 3), new Cell(4, 3), new Cell(5, 3) },
			{ new Cell(0, 4), new Cell(1, 4), new Cell(2, 4), new Cell(3, 4), new Cell(4, 4), new Cell(5, 4) },
			{ new Cell(0, 5), new Cell(1, 5), new Cell(2, 5), new Cell(3, 5), new Cell(4, 5), new Cell(5, 5) } };
		// Trap Cell[5][0]
		trickyGrid[4][0].addActor(new Robot(0, 4));
		trickyGrid[4][1].addActor(new Robot(1, 4));
		trickyGrid[5][1].addActor(new Robot(1, 5));

		//Create complex path from [5][2] to [0][4]
		trickyGrid[4][2].addActor(new Robot(2, 4));
		trickyGrid[4][3].addActor(new Robot(3, 4));
		trickyGrid[4][4].addActor(new Robot(4, 4));
		trickyGrid[2][5].addActor(new Robot(5, 2));
		trickyGrid[2][4].addActor(new Robot(4, 2));
		trickyGrid[0][3].addActor(new Robot(3, 0));
	}

	@Test
	public void testFindPathOnEmptyGrid() {
		BFS<Cell> BFS = new BFS<Cell>(emptyGrid);
		Cell startLocation = emptyGrid[5][2];
		Cell endLocation = emptyGrid[0][4];

		PathLink<Cell> result = BFS.findPath(startLocation, endLocation);
		// Most efficient path should be 7 nodes
		int expectedSteps = 7;
		assertEquals(expectedSteps, result.size());
		// Check last step is as expected
		Cell lastStep = null;
		for (int i = 0; i < expectedSteps; i++) {
			lastStep = result.takeStep();
		}
		assertEquals(endLocation, lastStep);
	}

	@Test
	public void testNoPathPossible() {
		BFS<Cell> BFS = new BFS<Cell>(trickyGrid);
		Cell startLocation = trickyGrid[5][0];
		Cell endLocation = trickyGrid[0][0];

		PathLink<Cell> result = BFS.findPath(startLocation, endLocation);
		assertNull(result);
	}
	
	@Test
	public void testFindPathWithCollisions() {
		BFS<Cell> BFS = new BFS<Cell>(trickyGrid);
		Cell startLocation = trickyGrid[5][2];
		Cell endLocation = trickyGrid[0][4];

		PathLink<Cell> result = BFS.findPath(startLocation, endLocation);
		// Most efficient path should be 11 nodes
		int expectedSteps = 11;
		assertEquals(expectedSteps, result.size());
		// Check last step is as expected
		Cell lastStep = null;
		for (int i = 0; i < expectedSteps; i++) {
			lastStep = result.takeStep();
		}
		assertEquals(endLocation, lastStep);
	}
}