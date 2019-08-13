/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class Assignment {
	private PackingStation packingStation;
	private StorageShelf shelf;

	/**
	 * Creates a new assignment representing a request from a packing station
	 * 
	 * @param packingStation The PackingStation making the request
	 * @param shelf          The shelf with the assignment contents
	 */
	public Assignment(PackingStation packingStation, StorageShelf shelf) {
		this.packingStation = packingStation;
		this.shelf = shelf;
	}

	/**
	 * Gets the packing station for this assignment
	 * 
	 * @return the packing station for this assignment
	 */
	public PackingStation getPackingStation() {
		return packingStation;
	}

	/**
	 * Gets the shelf of the assignment
	 * 
	 * @return the shelf of the assignment
	 */
	public StorageShelf getShelf() {
		return shelf;
	}
}
