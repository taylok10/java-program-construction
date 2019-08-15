/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public interface GridLocation {
	/**
	 * Gets the column location in the grid
	 * 
	 * @return Column position
	 */
	int getColumn();

	/**
	 * Gets the row location in the grid
	 * 
	 * @return Row position
	 */
	int getRow();

	/**
	 * Attempts to add the actor to this location
	 * 
	 * @param actor The actor to add
	 * @return If the actor was added successfully
	 */
	boolean addActor(Actor actor);

	/**
	 * Attempts to remove the actor from this location
	 * 
	 * @param actor The actor to remove
	 * @return If the actor was removed successfully
	 */
	boolean removeActor(Actor actor);

	/**
	 * Gets if location is blocked
	 * 
	 * @return If the location is currently blocked
	 */
	boolean isBlocked();
}