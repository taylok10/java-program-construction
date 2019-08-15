package model;

public class Robot extends Actor {
	private static final String IDENTIFIER = "r";
	private static final FuelCalculator FUEL_CALCULATOR = new FuelCalculator(1,2);
	private static int id = 0;

	private int battery;
	private int maxBattery;
	private ChargingPod chargingPod;
	private Assignment currentAssignment;
	private RobotState state = RobotState.IDLE;
	private PathFinder<GridLocation> pathFinder;
	private boolean hasItem;
	private RobotState emergencyBackupState = null;

	public Robot(GridLocation position, int maxBattery, ChargingPod chargingPod, PathFinder<GridLocation> pathFinder) {
		super(position, IDENTIFIER + id);
		id++;
		this.maxBattery = maxBattery;
		this.battery = maxBattery; // Robot starts fully charged
		this.chargingPod = chargingPod; // Every Robot has a designated ChargingPod
		this.pathFinder = pathFinder;
	}

	// Getter methods
	public ChargingPod getChargingPod() {
		return chargingPod;
	}
	
	public void chargeBattery(int charge) {
		if(state == RobotState.CHARGING) {
			battery += charge;
			if(battery > maxBattery) {
				battery = maxBattery;
			}
		}
	}

	private boolean canHandleAssignment(Assignment assignment) {
		int cost = 0;
		
		// Calculate journey from location to shelf to collect item
		PathLink<GridLocation> pathToShelf = pathFinder.findPath(getPosition(), assignment.getShelf().getPosition(), true);
		if(pathToShelf == null) {
			return false;
		}
		cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToShelf.size(), false);
		
		// Calculate journey from shelf to PackingStation
		PathLink<GridLocation> pathToPackingStation = pathFinder.findPath(assignment.getShelf().getPosition(), assignment.getPackingStation().getPosition(), true);
		if(pathToPackingStation == null) {
			return false;
		}
		cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPackingStation.size(), true);
		
		// Need to know the charging pod location for returning to
		PathLink<GridLocation> pathToPod = pathFinder.findPath(assignment.getPackingStation().getPosition(), chargingPod.getPosition(), true);
		if(pathToPod == null) {
			return false;
		}
		cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		
		// If the cost is less than our battery we can accept
		return cost < battery;
	}
	
	public boolean assignmentRequest(Assignment assignment) {
		if(currentAssignment == null && state != RobotState.CHARGING && canHandleAssignment(assignment)) { // If Robot is not doing anything/not on an order
			//Update state if accepted & assignment
			state = RobotState.COLLECTING_ITEM;
			currentAssignment = assignment;
			return true;
		}
		return false;
	}

	private boolean attemptToMove(GridLocation location) {
		if(battery == 0) {
			WarehouseSimulation.setFailureReason("ERROR - Robot: " + getUID() + " has run out of battery at " + WarehouseSimulation.getTicks() + " ticks");
			WarehouseSimulation.finish(false);
			return false;
		}
			
		if(location != null) {
			if(move(location)) {
				// Reduce battery
				battery -= FUEL_CALCULATOR.calculateFuelConsumption(1, hasItem);
			}
		}
		return true;
	}

	public boolean deliverItemToPackingStation() {
		if(currentAssignment.getPackingStation().acceptItemDelivery(currentAssignment.getShelf())) {
			currentAssignment = null;
			return true;
		}
		return false;
	}

	public static void resetIdCount() {
        id = 0;
    }

	private boolean moveTowards(GridLocation location) {
		if(!canFinishJourney() && emergencyBackupState == null) {
			emergencyBackupState = state;
			state = RobotState.RETURNING_TO_POD;
			location = chargingPod.getPosition();
		}
		
		PathLink<GridLocation> path = pathFinder.findPath(getPosition(), location, false);
		if (path != null) {
			return attemptToMove(path.takeStep());
		}
		return true;
	}
	
	private boolean canFinishJourney() {
		int cost = 0;
		if(state == RobotState.COLLECTING_ITEM) {
			PathLink<GridLocation> pathToShelf = pathFinder.findPath(getPosition(), currentAssignment.getShelf().getPosition(), false);
			if(pathToShelf == null) {
				// No available path currently - assume they can still finish the journey as no battery change
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToShelf.size(), false);
			
			PathLink<GridLocation> pathToPackingStation = pathFinder.findPath(currentAssignment.getShelf().getPosition(), currentAssignment.getPackingStation().getPosition(), true);
			if(pathToPackingStation == null) {
				// No available path currently - assume they can still finish the journey as no battery change
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPackingStation.size(), true);
			
			PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), true);
			if(pathToPod == null) {
				// No available path currently - assume they can still finish the journey as no battery change
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		}
		if (state == RobotState.DELIVERING_ITEM) {
			PathLink<GridLocation> pathToPackingStation = pathFinder.findPath(getPosition(), currentAssignment.getPackingStation().getPosition(), false);
			if(pathToPackingStation == null) {
				// No available path currently - assume they can still finish the journey as no battery change
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPackingStation.size(), true);
			
			PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), true);
			if(pathToPod == null) {
				// No available path currently - assume they can still finish the journey as no battery change
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		}
		if (state == RobotState.RETURNING_TO_POD) {
			PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), false);
			if(pathToPod == null) {
				// No available path currently - assume they can still finish the journey as no battery change
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		}
		return battery > cost;
	}

	@Override
	public boolean act() {
//		WarehouseSimulation.addReportEntry("ROBOT - " + getUID() + ": " + state + " | " + battery + "Units | " + getPosition().getRow() + "," + getPosition().getColumn());
		boolean flag = true;
		switch(state) {
			case COLLECTING_ITEM:
				flag = moveTowards(currentAssignment.getShelf().getPosition());
				if(getPosition().equals(currentAssignment.getShelf().getPosition())) {
					// Collected item, deliver it to packing station
					hasItem = true;
					state = RobotState.DELIVERING_ITEM;
				}
				break;
			case DELIVERING_ITEM:
				flag = moveTowards(currentAssignment.getPackingStation().getPosition());
				if(getPosition().equals(currentAssignment.getPackingStation().getPosition())) {
					// Delivered item return to pod
					if(deliverItemToPackingStation()) {
						state = RobotState.RETURNING_TO_POD;
						currentAssignment = null;
						hasItem = false;
					}
				}
				break;
			case RETURNING_TO_POD:
				flag = moveTowards(chargingPod.getPosition());
				if(getPosition().equals(chargingPod.getPosition())) {
					// Reached the charging pod, now we can charge
					if(chargingPod.dockRobot(this)) {
						state = RobotState.CHARGING;
					}
				}
				break;
			case CHARGING:
				if(battery == maxBattery) { // Stop charging when full
					chargingPod.undockRobot(); // Detach from pod
					
					if(emergencyBackupState != null) { // The Robot had to make an emergency re-route, pick up where we were
						state = emergencyBackupState;
						emergencyBackupState = null;
					} else {
						state = RobotState.IDLE;
					}
				}
				break;
			case IDLE:
			default:
				// Do nothing
				break;
		}
		return flag;
	}
	
	public RobotState getState() {
		return state;
	}
	
	public int getBattery() {
		return battery;
	}
	
	public void setBattery(int battery) {
		this.battery = battery;
	}
	
	public void setRobotState(RobotState state) {
		this.state = state;
	}
}