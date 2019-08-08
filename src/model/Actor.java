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
	private Image image;
	
	public Actor(int x, int y) {
		switch (this.getClass().getName()) {
		case "model.ChargingPod":
			this.UID = "CHPID" + countC.incrementAndGet();
			break;
		case "model.StorageShelf":
			this.UID = "SSFID" + countS.incrementAndGet();
			break;
		case "model.Robot":
			this.UID = "RBTID" + countR.incrementAndGet();
			break;
		case "model.PackingStation":
			this.UID = "PCKID" + countP.incrementAndGet();
			break;	
		default:
			this.UID = "???ID" + count.incrementAndGet();
			break;
		}
		
		position = new int[]{x,y};
		
		String location = "";
		String filename = this.getClass().getSimpleName() + ".png";
		System.out.println(location + filename);
		this.image = new Image(location + filename);
	}
	
	public abstract void act();
	
	public int[] getPosition(){
		return position;
	}
	
	public Image getImage() {
		return image;
	}
	
	public String getUID() {
		return UID;
	}
}
