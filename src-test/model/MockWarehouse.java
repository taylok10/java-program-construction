/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 * Helper class that provides common fake objects for a warehouse
 */
public final class MockWarehouse {
	static final Cell LOCATION_ONE = new Cell(0,0);
	static final Cell LOCATION_TWO = new Cell(0,1);
	static final Cell LOCATION_THREE = new Cell(1,0);
	static final Cell LOCATION_FOUR = new Cell(1,1);
	
	static final PathFinder<GridLocation> PATH_FINDER = new BFS(new Cell[4][4]);
}
