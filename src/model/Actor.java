/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public abstract class Actor {
	private final String UID;
	private int[] position;
	
	public Actor(int x, int y, String UID) {	
		this.UID = UID;
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