/**
 * 
 */
package model;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.image.Image;

/**
 * @author kelly.taylor
 *
 */
public abstract class Actor {
	private static final AtomicInteger countC = new AtomicInteger(0);
	private static final AtomicInteger countS = new AtomicInteger(0); 
	private static final AtomicInteger countR = new AtomicInteger(0);
	private static final AtomicInteger countP = new AtomicInteger(0); 
	private static final AtomicInteger count = new AtomicInteger(0); 
	private final String UID;
	private int[] position;
	
	public Actor(int x, int y) {
		switch (this.getClass().getName()) {
		case "model.ChargingPod":
			this.UID = "c" + countC.incrementAndGet();
			break;
		case "model.StorageShelf":
			this.UID = "ss" + countS.incrementAndGet();
			break;
		case "model.Robot":
			this.UID = "r" + countR.incrementAndGet();
			break;
		case "model.PackingStation":
			this.UID = "ps" + countP.incrementAndGet();
			break;	
		default:
			this.UID = "???ID" + count.incrementAndGet();
			break;
		}
		
		position = new int[]{x,y};
	}
	
	public abstract void act();
	
	public int[] getPosition(){
		return position;
	}
	
	public String getUID() {
		return UID;
	}
}
