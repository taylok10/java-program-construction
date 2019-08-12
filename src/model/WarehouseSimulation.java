/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controller.WarehouseController;

/**
 * @author kelly.taylor
 *
 */
public class WarehouseSimulation extends Simulation {
	OrderManager orders;
	Map<Order, Integer> orderStats;
	private Map<String,StorageShelf> shelves;
	private WarehouseController wc;
	Floor floor;
	
	public WarehouseSimulation() {
		orders = new OrderManager();
		shelves = new HashMap<String,StorageShelf>();
		wc = null;
	}
	
	public void readSimulation(File file) {
		orders.empty();
		shelves.clear();
		wc.resetIds();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    if ((line = br.readLine()).equals("format 1")) {
		    	boolean runnable = false;
			    while ((line = br.readLine()) != null) {
			    	String[] lineArr = line.split(" ");
			    	switch(lineArr[0]) {
			    	case "width":
			    		floor.setWidth(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "height":
			    		floor.setHeight(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "capacity":
			    		wc.setCapacity(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "chargeSpeed":
			    		wc.setChargeSpeed(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "podRobot":
			    		runnable = floor.addActor(new ChargingPod(Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4])),Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4]));
			    		runnable = floor.addActor(new Robot(Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4])),Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4]));
			    		break;
			    	case "shelf":
			    		StorageShelf nShelf = new StorageShelf(Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
			    		runnable = floor.addActor(nShelf,Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
						shelves.put(nShelf.getUID(),nShelf);
			    		break;
			    	case "station":
			    		runnable = floor.addActor(new PackingStation(Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]), orders, floor.getRobots()),Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
			    		break;
			    	case "order":
			    		StorageShelf[] orderShelves = new StorageShelf[lineArr.length-2];
			    		for (int i = 2; i < lineArr.length; i++) {
			    			orderShelves[i-2] = shelves.get(lineArr[i]);			    			
			    		}
			    		orders.addOrder(new Order(Integer.parseInt(lineArr[1]), orderShelves));
			    		break;
			    	default:
			    		System.out.println("Invalid format: " + lineArr[0]);
			    		break;
			    	}
			    }
			    System.out.println(runnable);
			    wc.setRunnable(runnable);
		    } else {
		    	System.out.println("WARNING - Invalid simulation format");
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see model.Simulation#tick()
	 */
	@Override
	public boolean tick() {
		System.out.println("Tick");
		return false;
	}

	/* (non-Javadoc)
	 * @see model.Simulation#multiTick(int)
	 */
	@Override
	public boolean multiTick(int ticks) {
		System.out.println("Multiple Ticks, Amount = " + ticks);
		return false;
	}
	
	public OrderManager getOrderManager(){
		return orders;
	}
	
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	public void setController(WarehouseController wc) {
		this.wc = wc;
	}
}