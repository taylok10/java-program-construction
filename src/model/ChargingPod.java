package model;

/**
 * @author kelly.taylor
 *
 */
public class ChargingPod extends Actor {
	private static final String IDENTIFIER = "c";
	private static int id = 0;
	private Robot dockedRobot;
	private int chargeSpeed;

	/**
	 * Creates a new ChargingPod
	 * 
	 * @param position    The position of this ChargingPod
	 * @param chargeSpeed The charging speed of this pod
	 */
	public ChargingPod(GridLocation position, int chargeSpeed) {
		super(position, IDENTIFIER + id);
		id++;
		this.chargeSpeed = chargeSpeed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.Actor#act()
	 */
	@Override
	public boolean act() {
		if (dockedRobot != null) {
			dockedRobot.chargeBattery(chargeSpeed);
		}
		return true;
	}

	/**
	 * Will attempt to dock the specified robot to the charging pod. If the robot is
	 * not assigned to this charging pod it will fail.
	 * 
	 * @param robot The robot to attempt to dock
	 * @return if the robot docked successfully
	 */
	public boolean dockRobot(Robot robot) {
		if (this.dockedRobot == null && robot.getUID().replace('r', ' ').equals(getUID().replace('c', ' '))) {
			this.dockedRobot = robot;
			return true;
		}
		return false;
	}

	/**
	 * Will undock the robot from the charging pod
	 */
	public void undockRobot() {
		dockedRobot = null;
	}

	/**
	 * Resets the id seed of StorageShelves
	 */
	public static void resetIdCount() {
		id = 0;
	}
}