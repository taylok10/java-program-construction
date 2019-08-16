/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.WarehouseController;

/**
 * @author Joseph.Sheargold
 *
 */
public class WarehouseSimulation extends Simulation {
	public static OrderManager orders;
	Map<Order, Integer> orderStats;
	private Map<String,StorageShelf> shelves;
	private static WarehouseController wc;
	Floor floor;
	private static boolean runnable;
	private static int ticks;
	
	public WarehouseSimulation() {
		orders = new OrderManager();
		shelves = new HashMap<String,StorageShelf>();
		wc = null;
		report = new ArrayList<String>();
		report.add("Log:");
		isCompleted = true;
		failureReason = null;
		ticks = 0;
	}
	
	/**
	 * Reads and imports the passed .sim file into the application and 
	 *  resets all of the relevant variables for a new run. 
	 * 
	 * @param file a file to be read and imported into the simulation
	 */
	public void readSimulation(File file) {
		ticks = 0;
		resetReport();
		orders.reset();
		shelves.clear();
		wc.resetIds();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    if ((line = br.readLine()).equals("format 1")) {
		    	runnable = false;
		    	boolean widthSet, heightSet, capacitySet, chargeSet, podRobotSet, shelfSet, stationSet, orderSet;
		    	widthSet = false; heightSet = false; capacitySet = false; chargeSet = false; podRobotSet = false; shelfSet = false; stationSet = false; orderSet = false;
			    while ((line = br.readLine()) != null) {
			    	String[] lineArr = line.split(" ");
			    	switch(lineArr[0]) {
			    	case "width":
			    		widthSet = true;
			    		floor.setWidth(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "height":
			    		heightSet = true;
			    		floor.setHeight(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "capacity":
			    		capacitySet = true;
			    		wc.setCapacity(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "chargeSpeed":
			    		chargeSet = true;
			    		wc.setChargeSpeed(Integer.parseInt(lineArr[1]));
			    		break;
			    	case "podRobot":
			    		podRobotSet = true;
			    		int maxBattery = wc.getCapacity();
			    		int chargeSpeed = wc.getChargeSpeed();
			    		ChargingPod chargingPod = new ChargingPod(floor.getCell(Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4])),chargeSpeed);
			    		runnable = floor.addActor(chargingPod,Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4]));
			    		runnable = floor.addActor(new Robot(floor.getCell(Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4])), maxBattery, chargingPod, floor.getPathFinder()),Integer.parseInt(lineArr[3]),Integer.parseInt(lineArr[4]));
			    		break;
			    	case "shelf":
			    		shelfSet = true;
			    		StorageShelf nShelf = new StorageShelf(floor.getCell(Integer.parseInt(lineArr[2]), Integer.parseInt(lineArr[3])));
			    		runnable = floor.addActor(nShelf,Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
						shelves.put(nShelf.getUID(),nShelf);
			    		break;
			    	case "station":
			    		stationSet = true;
			    		runnable = floor.addActor(new PackingStation(floor.getCell(Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3])), orders, floor.getRobots()),Integer.parseInt(lineArr[2]),Integer.parseInt(lineArr[3]));
			    		break;
			    	case "order":
			    		orderSet = true;
			    		StorageShelf[] orderShelves = new StorageShelf[lineArr.length-2];
			    		for (int i = 2; i < lineArr.length; i++) {
			    			orderShelves[i-2] = shelves.get(lineArr[i]);			    			
			    		}
			    		orders.addOrder(new Order(Integer.parseInt(lineArr[1]), orderShelves));
			    		break;
			    	default:
			    		setFailureReason("ERROR - Invalid formatting in .sim file: encountered String '" + lineArr[0]);
			    		finish(false);
			    		break;
			    	}
			    }
			    if (runnable) {
			    	runnable = widthSet && heightSet && capacitySet && chargeSet && podRobotSet && shelfSet && stationSet && orderSet;
			    }
			    if (runnable != wc.getRunnable()) {
			    	wc.toggleRunnable();
			    }
			    wc.setRunnable(runnable);
			    isCompleted = false;
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
		if (!isCompleted) {
			boolean flag = true;
			for (PackingStation actor : floor.getPackingStations()) {
				if (!actor.isFinished()) {
					actor.act();
					flag = false;
				}				
			}				
			for (Actor actor : floor.getChargingPods()) {
				actor.act();
			}
			for (Actor actor : floor.getStorageShelves()) {
				actor.act();
			}
			for (Robot actor : floor.getRobots()) {
				if (!actor.act()) {
					return false;
				}
				if (!(actor.getState().equals(RobotState.IDLE) || actor.getState().equals(RobotState.RETURNING_TO_POD) || actor.getState().equals(RobotState.CHARGING)) && flag) {
					flag = false;
				}
			}
			isCompleted = flag;	
			wc.updateReport();
			floor.refreshGraphics();
			ticks++;
			return true;
		} else {
			finish(true);
			return false;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see model.Simulation#multiTick(int)
	 */
	@Override
	public boolean multiTick(int ticks) {
		for (int i=1; i<=ticks; i++) {
			if (!tick()) {
				break;
			}
		}
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
	
	/**
	 * Finishes the simulation based on a flag given for whether or not
	 *  it was a success and calls a relevant report method.
	 * 
	 * @param type a boolean value which indicates type of completion
	 */
	public static void finish(boolean type) {
		runnable = false;
		wc.setRunnable(runnable);
		if (type) {
			isCompleted = true;
			reportSuccess();
		} else {
			isCompleted = false;
			reportFailure();
		}

	}
	
	/**
	 * Updates the report in the GUI a success message and tick count
	 */
	private static void reportSuccess() {
		addReportEntry("---------------------------------------------------------");
		addReportEntry("Simulation successfully completed after " + ticks + " ticks.");
		addReportEntry("---------------------------------------------------------");
		wc.updateReport();
	}
	
	/**
	 * Updates the report in the GUI with the stored failure message
	 */
	private static void reportFailure() {
		addReportEntry("---------------------------------------------------------");
		addReportEntry(failureReason);
		addReportEntry("---------------------------------------------------------");
		wc.updateReport();
	}
	
	public static int getTicks() {
		return ticks;
	}

}