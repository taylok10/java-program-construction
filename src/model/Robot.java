package model;

public class Robot extends Actor {
	private static final String identifier = "r";
	private static int id = 0;

	private ChargingPod chargingPod;
	private StorageShelf storageShelf;
	private PackingStation packingStation;
	private boolean isLoaded;
	private int maxBattery, battery;
	
	public Robot(int x, int y) {
		super(x,y, identifier + id);
		id++;
	}
	
	
	// Getter methods
	public ChargingPod getChargingPod() {
		return chargingPod;
	}
	
	public StorageShelf getStorageShelf() {
		return storageShelf;
	}
	
	public PackingStation getPackingStation() {
		return packingStation;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}


	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
	
}