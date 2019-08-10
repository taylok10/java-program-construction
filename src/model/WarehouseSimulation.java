/**
 * 
 */
package model;

import java.util.Map;
import java.util.Queue;

/**
 * @author kelly.taylor
 *
 */
public class WarehouseSimulation extends Simulation {
	Queue<Order> orders;
	Map<Order, Integer> orderStats;
	Floor floor;
	
	public void readSimulation(String filePath) {
		//	TODO
	}
	
	/* (non-Javadoc)
	 * @see model.Simulation#tick()
	 */
	@Override
	public boolean tick() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see model.Simulation#multiTick(int)
	 */
	@Override
	public boolean multiTick(int ticks) {
		// TODO Auto-generated method stub
		return false;
	}
}