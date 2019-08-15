/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public abstract class Actor {
	private GridLocation position;
	private final String UID;

	/**
	 * Creates a new Actor
	 * 
	 * @param position The position of this Actor
	 * @param UID      The UID of the Actor
	 */
	public Actor(GridLocation position, String UID) {
		this.UID = UID;
		this.position = position;
	}

	/**
	 * The actor will perform some behaviour
	 * 
	 * @return If the act was successful
	 */
	public abstract boolean act();

	/**
	 * The actor will move from it's current position to the provided value
	 * 
	 * @param position The position to move to
	 * @return If the actor moved successfully
	 */
	public boolean move(GridLocation position) {
		if (position.addActor(this)) {
			if (getPosition().removeActor(this)) {
				this.position = position;
				return true;
			} else {
				// Remove from position we previously added to so we don't have two actors
				position.removeActor(this);
			}
		}
		return false;
	}

	/**
	 * Gets the actor's position
	 * 
	 * @return The position of the Actor
	 */
	public GridLocation getPosition() {
		return position;
	}

	/**
	 * Gets the actor's UID
	 * 
	 * @return The UID of the Actor
	 */
	public String getUID() {
		return UID;
	}
}