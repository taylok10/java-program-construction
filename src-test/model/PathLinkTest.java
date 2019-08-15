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
public class PathLinkTest {
	private Cell stepOne;
	private Cell stepTwo;
	private PathLink<Cell> path;

	@Before
	public void setup() {
		stepOne = new Cell(0, 0);
		stepTwo = new Cell(0, 1);
		path = new PathLink<Cell>(stepOne);
	}

	@Test
	public void testNewPathLinkFromLinearNode() {
		LinearNode<Cell> node = new LinearNode<Cell>(stepOne);
		node.setNext(new LinearNode<Cell>(stepTwo));
		path = new PathLink<Cell>(node);

		assertEquals(2, path.size());
		assertEquals(stepOne, path.takeStep());
		assertEquals(stepTwo, path.takeStep());
	}

	@Test
	public void testSize() {
		assertEquals(1, path.size());
	}

	@Test
	public void testAddStep() {
		path.addStep(stepTwo);
		assertEquals(2, path.size());
	}

	@Test
	public void testTakeStep() {
		Cell step = path.takeStep();
		assertEquals(stepOne, step);
		assertEquals(0, path.size());
	}

	@Test
	public void testTakeStepOnPathWithNoMoreSteps() {
		path.takeStep();
		Cell invalidStep = path.takeStep();
		assertNull(invalidStep);
	}

	@Test
	public void testReverseThePath() {
		path.addStep(stepTwo);
		path.reverse();
		Cell step = path.takeStep();
		assertEquals(stepTwo, step);
		step = path.takeStep();
		assertEquals(stepOne, step);
	}
}