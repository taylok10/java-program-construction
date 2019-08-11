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
	 * Gets if location is blocked
	 * 
	 * @return If the location is currently blocked
	 */
	boolean isBlocked();
}