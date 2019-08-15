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
public class AssignmentTest {
	@Test
	public void testCreatingNewAssignment() {
		Assignment assignment = new Assignment(MockWarehouse.PACKING_STATION, MockWarehouse.SHELF_ONE);
		assertEquals(MockWarehouse.PACKING_STATION, assignment.getPackingStation());
		assertEquals(MockWarehouse.SHELF_ONE, assignment.getShelf());
	}
}