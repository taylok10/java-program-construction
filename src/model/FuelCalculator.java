/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class FuelCalculator {
	private int normalFuelUse;
	private int fuelWhileEncumbered;

	public FuelCalculator(int normalFuelUse, int fuelWhileEncumbered) {
		this.normalFuelUse = normalFuelUse;
		this.fuelWhileEncumbered =fuelWhileEncumbered;
	}
	
	/**
	 * Calculates the required fuel for the specified number of moves
	 * 
	 * @param moves        The number of moves required
	 * @param isEncumbered Specifies if the object encumbered and requires more fuel
	 *                     than usual
	 * @return The required fuel
	 */
	public int calculateFuelConsumption(int moves, boolean isEncumbered) {
		return moves * (isEncumbered ? fuelWhileEncumbered : normalFuelUse);
	}
}