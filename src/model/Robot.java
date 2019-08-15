package model;

/**
 * @author kelly.taylor
 *
 */
public class Robot extends Actor {
	private static final String IDENTIFIER = "r";
	private static final FuelCalculator FUEL_CALCULATOR = new FuelCalculator(1, 2);
	private static int id = 0;

	private int battery;
	private int maxBattery;
	private ChargingPod chargingPod;
	private Assignment currentAssignment;
	private RobotState state = RobotState.IDLE;
	private PathFinder<GridLocation> pathFinder;
	private boolean hasItem;
	private RobotState emergencyBackupState = null;
	private int deadlockCounter = 0;

	/**
	 * Creates a new Robot
	 * 
	 * @param position    The position of this PackingStation
	 * @param maxBattery  The maximum battery of the Robot
	 * @param chargingPod The chargingPod assigned to this Robot
	 * @param pathFinder  The pathFinder used for navigating the warehouse
	 */
	public Robot(GridLocation position, int maxBattery, ChargingPod chargingPod, PathFinder<GridLocation> pathFinder) {
		super(position, IDENTIFIER + id);
		id++;
		this.maxBattery = maxBattery;
		this.battery = maxBattery; // Robot starts fully charged
		this.chargingPod = chargingPod; // Every Robot has a designated ChargingPod
		this.pathFinder = pathFinder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Actor#act()
	 */
	@Override
	public boolean act() {
		boolean flag = true;
		switch (state) {
		case COLLECTING_ITEM:
			flag = moveTowards(currentAssignment.getShelf().getPosition());
			if (getPosition().equals(currentAssignment.getShelf().getPosition())) {
				// Collected item, deliver it to packing station
				hasItem = true;
				state = RobotState.DELIVERING_ITEM;
			}
			break;
		case DELIVERING_ITEM:
			flag = moveTowards(currentAssignment.getPackingStation().getPosition());
			if (getPosition().equals(currentAssignment.getPackingStation().getPosition())) {
				// Delivered item return to pod
				if (deliverItemToPackingStation()) {
					state = RobotState.RETURNING_TO_POD;
					currentAssignment = null;
					hasItem = false;
				}
			}
			break;
		case RETURNING_TO_POD:
			flag = moveTowards(chargingPod.getPosition());
			if (getPosition().equals(chargingPod.getPosition())) {
				// Reached the charging pod, now we can charge
				if (chargingPod.dockRobot(this)) {
					state = RobotState.CHARGING;
				}
			}
			break;
		case CHARGING:
			if (battery == maxBattery) { // Stop charging when full
				chargingPod.undockRobot(); // Detach from pod

				if (emergencyBackupState != null) {
					// The Robot had to make an emergency re-route, pick up where we were
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

	/**
	 * Asks the robot if they can accept the provided assignment
	 * 
	 * @param assignment The assignment for the Robot
	 * @return If the robot has accepted
	 */
	public boolean assignmentRequest(Assignment assignment) {
		// If Robot is not doing anything/not on an order
		if (currentAssignment == null && state != RobotState.CHARGING && canHandleAssignment(assignment)) {
			// Update state if accepted & assignment
			state = RobotState.COLLECTING_ITEM;
			currentAssignment = assignment;
			return true;
		}
		return false;
	}

	/**
	 * Attempts to move the robot to the specified location
	 * 
	 * @param location The location to move to
	 * @return If the robot can continue
	 */
	private boolean attemptToMove(GridLocation location) {
		if (battery == 0) {
			WarehouseSimulation.setFailureReason("ERROR - Robot: " + getUID() + " has run out of battery at "
					+ WarehouseSimulation.getTicks() + " ticks");
			WarehouseSimulation.finish(false);
			return false;
		}

		if (location != null) {
			if (move(location)) {
				// Reduce battery
				battery -= FUEL_CALCULATOR.calculateFuelConsumption(1, hasItem);
			}
		}
		return true;
	}

	/**
	 * Confirms if the Robot can complete it's current journey
	 * 
	 * @return If the Robot can complete it's current journey
	 */
	private boolean canFinishJourney() {
		int cost = 0;
		if (state == RobotState.COLLECTING_ITEM) {
			PathLink<GridLocation> pathToShelf = pathFinder.findPath(getPosition(),
					currentAssignment.getShelf().getPosition(), false);
			if (pathToShelf == null) {
				// No available path currently - assume they can still finish the journey as no
				// battery change
				deadlockCounter++;
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToShelf.size(), false);

			PathLink<GridLocation> pathToPackingStation = pathFinder.findPath(
					currentAssignment.getShelf().getPosition(), currentAssignment.getPackingStation().getPosition(),
					true);
			if (pathToPackingStation == null) {
				// No available path currently - assume they can still finish the journey as no
				// battery change
				deadlockCounter++;
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPackingStation.size(), true);

			PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), true);
			if (pathToPod == null) {
				// No available path currently - assume they can still finish the journey as no
				// battery change
				deadlockCounter++;
				return true;
			}

			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		}
		if (state == RobotState.DELIVERING_ITEM) {
			PathLink<GridLocation> pathToPackingStation = pathFinder.findPath(getPosition(),
					currentAssignment.getPackingStation().getPosition(), false);
			if (pathToPackingStation == null) {
				// No available path currently - assume they can still finish the journey as no
				// battery change
				deadlockCounter++;
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPackingStation.size(), true);

			PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), true);
			if (pathToPod == null) {
				// No available path currently - assume they can still finish the journey as no
				// battery change
				deadlockCounter++;
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		}
		if (state == RobotState.RETURNING_TO_POD) {
			PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), false);
			if (pathToPod == null) {
				// No available path currently - assume they can still finish the journey as no
				// battery change
				deadlockCounter++;
				return true;
			}
			cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);
		}
		return battery >= cost;
	}

	/**
	 * Advises if the Robot can handle the provided assignment by reviewing the cost
	 * of the journey to the current battery
	 * 
	 * @param assignment The assignment to attempt
	 * @return If the Robot can handle the assignment
	 */
	private boolean canHandleAssignment(Assignment assignment) {
		int cost = 0;

		// Calculate journey from location to shelf to collect item
		PathLink<GridLocation> pathToShelf = pathFinder.findPath(getPosition(), assignment.getShelf().getPosition(),
				false);
		if (pathToShelf == null) {
			return false;
		}
		cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToShelf.size(), false);

		// Calculate journey from shelf to PackingStation
		PathLink<GridLocation> pathToPackingStation = pathFinder.findPath(assignment.getShelf().getPosition(),
				assignment.getPackingStation().getPosition(), true);
		if (pathToPackingStation == null) {
			return false;
		}
		cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPackingStation.size(), true);

		// Need to know the charging pod location for returning to
		PathLink<GridLocation> pathToPod = pathFinder.findPath(assignment.getPackingStation().getPosition(),
				chargingPod.getPosition(), true);
		if (pathToPod == null) {
			return false;
		}
		cost += FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), false);

		// If we have the battery we can accept
		return battery > cost;
	}

	/**
	 * Advises if the Robot can return to the pod in their current condition
	 * 
	 * @return If Robot can return to the ChargingPod
	 */
	private boolean canReturnToPod() {
		PathLink<GridLocation> pathToPod = pathFinder.findPath(getPosition(), chargingPod.getPosition(), false);
		if (pathToPod != null) {
			return battery > FUEL_CALCULATOR.calculateFuelConsumption(pathToPod.size(), hasItem);
		}
		return false;
	}

	/**
	 * Charges the battery of the robot. Required the Robot to be in Charging state.
	 * 
	 * @param charge The amount to charge the battery by
	 */
	public void chargeBattery(int charge) {
		if (state == RobotState.CHARGING) {
			battery += charge;
			if (battery > maxBattery) {
				battery = maxBattery;
			}
		}
	}

	/**
	 * Hands over the current assignment to the packing station
	 * 
	 * @return If the assignment was delivered successfully
	 */
	public boolean deliverItemToPackingStation() {
		if (currentAssignment != null
				&& currentAssignment.getPackingStation().acceptItemDelivery(currentAssignment.getShelf())) {
			currentAssignment = null;
			return true;
		}
		return false;
	}

	/**
	 * Gets the current battery level of the robot
	 * 
	 * @return Battery level
	 */
	public int getBattery() {
		return battery;
	}

	/**
	 * Gets the assigned ChargingPod of the robot
	 * 
	 * @return Assigned ChargingPod
	 */
	public ChargingPod getChargingPod() {
		return chargingPod;
	}

	/**
	 * Gets the current assignment of the robot
	 * 
	 * @return Current assignment
	 */
	public Assignment getCurrentAssignment() {
		return currentAssignment;
	}

	/**
	 * Gets the backed up state of the robot before it entered emergency mode
	 * 
	 * @return Backed up state
	 */
	public RobotState getEmergencyBackupState() {
		return emergencyBackupState;
	}

	/**
	 * Gets the maximum battery level of the robot
	 * 
	 * @return Max battery level
	 */
	public int getMaxBattery() {
		return maxBattery;
	}

	/**
	 * Gets the path finder of the robot
	 * 
	 * @return The path finder being used for navigation
	 */
	public PathFinder<GridLocation> getPathFinder() {
		return pathFinder;
	}

	/**
	 * Gets the current state of the robot
	 * 
	 * @return Robot state
	 */
	public RobotState getState() {
		return state;
	}

	/**
	 * Will try to move the Robot towards the specified target. If there is not
	 * enough battery they may enter an emergency state and return to their pod.
	 * 
	 * @param location The location to move towards
	 * @return If the robot is still active
	 */
	private boolean moveTowards(GridLocation location) {
		// Check if the Robot has ended up in a deadlock state with another Robot
		// This happens if they want each other's spaces
		boolean inDeadlockState = deadlockCounter >= 5;
		if (emergencyBackupState != null && !canFinishJourney()) {
			if (canReturnToPod()) {
				emergencyBackupState = state;
				state = RobotState.RETURNING_TO_POD;
				location = chargingPod.getPosition();
			}
		} else if (canFinishJourney() || inDeadlockState) {
			PathLink<GridLocation> path = pathFinder.findPath(getPosition(), location, inDeadlockState);
			if (path != null) {
				return attemptToMove(path.takeStep());
			}
		}
		return true;
	}

	/**
	 * Resets the id seed of Robots
	 */
	public static void resetIdCount() {
		id = 0;
	}

	/**
	 * Sets the current battery level of the robot
	 * 
	 * @param battery New battery value
	 */
	public void setBattery(int battery) {
		this.battery = battery;
	}

	/**
	 * Sets the current assignment of the robot
	 * 
	 * @param assignment New assignment
	 */
	public void setCurrentAssignment(Assignment assignment) {
		this.currentAssignment = assignment;
	}

	/**
	 * Sets the state of the robot
	 * 
	 * @param state The new state of the Robot
	 */
	public void setEmergencyState(RobotState state) {
		this.emergencyBackupState = state;
	}

	/**
	 * Sets the state of the robot
	 * 
	 * @param state New state
	 */
	public void setRobotState(RobotState state) {
		this.state = state;
	}
}