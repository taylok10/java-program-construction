package model;

public class ChargingPod extends Actor {
	private static final String IDENTIFIER = "c";
	private static int id = 0;
	private Robot robot;
	private int chargeSpeed;

	public ChargingPod(GridLocation position, int chargeSpeed) {
		super(position, IDENTIFIER + id);
		id++;
		this.chargeSpeed = chargeSpeed;
	}
	
	@Override
	public void act() {
		if(robot != null) {
			robot.chargeBattery(chargeSpeed);
		}
	}
	
	public void undockRobot() {
		robot = null;
	}
	
	public boolean dockRobot(Robot robot) {
		if(this.robot == null && robot.getUID().replace('r', ' ').equals(getUID().replace('c', ' '))) {
			this.robot = robot;
			return true;
		}
		return false;
	}

	public static void resetIdCount() {
		id = 0;		
	}
}