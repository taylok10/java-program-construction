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
	private GridLocation position;
	
	public Actor(GridLocation position, String UID) {	
		this.UID = UID;
		this.position = position;
	}
	
	public abstract void act();
	
	public GridLocation getPosition(){
		return position;
	}
	
	public String getUID() {
		return UID;
	}
	
	public boolean move(GridLocation location) {
		if(location.addActor(this)) {
			if(getPosition().removeActor(this)) {
				return true;
			} else {
				// Remove from location we previously added to so we don't have two actors
				location.removeActor(this);
			}
		}
		return false;
	}
}