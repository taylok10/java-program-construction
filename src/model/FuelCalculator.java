/**
 * 
 */
package model;

/**
 * @author kelly.taylor
 *
 */
public class FuelCalculator {
	private static final int FUEL_CONSUMPTION = 1;
	private static final int ENCUMBERED_FUEL_CONSUMPTION = 2;

	/**
	 * Calculates the required fuel for the specified number of moves
	 * 
	 * @param moves        The number of moves required
	 * @param isEncumbered Specifies if the object encumbered and requires more fuel
	 *                     than usual
	 * @return The required fuel
	 */
	public static int calculateFuelConsumption(int moves, boolean isEncumbered) {
		return moves * (isEncumbered ? ENCUMBERED_FUEL_CONSUMPTION : FUEL_CONSUMPTION);
	}
}