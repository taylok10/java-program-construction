/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author kelly.taylor
 *
 *         Helper class that provides common fake objects for a warehouse
 */
public final class MockWarehouse {
	static final Cell LOCATION_ONE = new Cell(0, 0);
	static final Cell LOCATION_TWO = new Cell(0, 1);
	static final Cell LOCATION_THREE = new Cell(1, 0);
	static final Cell LOCATION_FOUR = new Cell(1, 1);
	private static final Cell[][] GRID = new Cell[][] { { LOCATION_ONE, LOCATION_TWO },
			{ LOCATION_THREE, LOCATION_FOUR } };

	static final PathFinder<GridLocation> PATH_FINDER = new BFS<GridLocation>(GRID);

	static final StorageShelf SHELF_ONE = new StorageShelf(MockWarehouse.LOCATION_ONE);
	static final StorageShelf SHELF_TWO = new StorageShelf(MockWarehouse.LOCATION_TWO);
	static final StorageShelf SHELF_THREE = new StorageShelf(MockWarehouse.LOCATION_THREE);
	static final StorageShelf SHELF_FOUR = new StorageShelf(MockWarehouse.LOCATION_FOUR);

	static final Order ORDER_ONE = new Order(1, new StorageShelf[] { SHELF_ONE, SHELF_TWO });
	static final Order ORDER_TWO = new Order(2, new StorageShelf[] { SHELF_THREE, SHELF_FOUR });
	
	static final PackingStation PACKING_STATION = new PackingStation(MockWarehouse.LOCATION_ONE, new OrderManager(), new ArrayList<Robot>());
}
