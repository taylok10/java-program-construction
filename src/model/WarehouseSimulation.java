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
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author kelly.taylor
 *
 */
public class WarehouseSimulation extends Simulation {
	OrderManager orders;
	Map<Order, Integer> orderStats;
	private Map<String,StorageShelf> shelves;
	Floor floor;
	
	public WarehouseSimulation() {
		orders = new OrderManager();
		shelves = new HashMap<String,StorageShelf>();
	}
	
	public void readSimulation(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    if ((line = br.readLine()).equals("format 1")) {
			    while ((line = br.readLine()) != null) {
			    	String[] lineArr = line.split(" ");
			    	switch(lineArr[0]) {
			    	case "width":
			    		System.out.println("Width = " + lineArr[1]);
			    		floor.setWidth(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "height":
			    		System.out.println("Height = " + lineArr[1]);
			    		floor.setHeight(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "capacity":
			    		System.out.println("Charge Capacity = " + lineArr[1]);
			    		break;
			    	case "chargeSpeed":
			    		System.out.println("Charge Speed = " + lineArr[1]);
			    		break;
			    	case "podRobot":
			    		System.out.println("Charging Pod ID = " + lineArr[1] + " Robot ID = " + lineArr[2] + " X-Coordinate " + lineArr[3] + " Y-Coordinate " + lineArr[4]);
						floor.addActor(new ChargingPod(Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4])),Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4]));
						floor.addActor(new Robot(Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4])),Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4]));
			    		break;
			    	case "shelf":
			    		System.out.println("Storage Shelf ID = " + lineArr[1] + " X-Coordinate " + lineArr[2] + " Y-Coordinate " + lineArr[3]);
			    		StorageShelf nShelf = new StorageShelf(Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
						floor.addActor(nShelf,Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
						shelves.put(nShelf.getUID(),nShelf);
			    		break;
			    	case "station":
			    		System.out.println("Packing Station ID = " + lineArr[1] + " X-Coordinate " + lineArr[2] + " Y-Coordinate " + lineArr[3]);
						floor.addActor(new PackingStation(Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]), orders, floor.getRobots()),Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
			    		break;
			    	case "order":
			    		System.out.println("Packing Ticks = " + lineArr[1]);
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
		    } else {
		    	System.out.println("Invalid simulation format");
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(floor.getColumns() + " " + floor.getRows());
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
	
	public OrderManager getOrderManager(){
		return orders;
	}
	
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
}