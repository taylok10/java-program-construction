/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class BFSTest {
	private final Cell[][] grid = {
			{ new Cell(0, 0), new Cell(0, 1), new Cell(0, 2), new Cell(0, 3), new Cell(0, 4), new Cell(0, 5) },
			{ new Cell(1, 0), new Cell(1, 1), new Cell(1, 2), new Cell(1, 3), new Cell(1, 4), new Cell(1, 5) },
			{ new Cell(2, 0), new Cell(2, 1), new Cell(2, 2), new Cell(2, 3), new Cell(2, 4), new Cell(2, 5) },
			{ new Cell(3, 0), new Cell(3, 1), new Cell(3, 2), new Cell(3, 3), new Cell(3, 4), new Cell(3, 5) },
			{ new Cell(4, 0), new Cell(4, 1), new Cell(4, 2), new Cell(4, 3), new Cell(4, 4), new Cell(4, 5) },
			{ new Cell(5, 0), new Cell(5, 1), new Cell(5, 2), new Cell(5, 3), new Cell(5, 4), new Cell(5, 5) } };

	@Test
	public void testFindPath() {
		BFS<Cell> BFS = new BFS<Cell>(grid);
		Cell startLocation = grid[5][2];
		Cell endLocation = grid[0][4];

		PathLink<Cell> result = BFS.findPath(startLocation, endLocation);
		// Most efficient path should be 8 nodes
		int expectedSteps = 8;
		assertEquals(expectedSteps, result.size());
		// Check first step is as expected
		assertEquals(startLocation, result.takeStep());
		// Check last step is as expected
		Cell lastStep = null;
		for (int i = 0; i < 7; i++) {
			lastStep = result.takeStep();
		}
		assertEquals(endLocation, lastStep);
	}
}