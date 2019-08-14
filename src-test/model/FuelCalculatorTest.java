/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class FuelCalculatorTest {
	@Test
	public void testCalculateFuelConsumption() {
		FuelCalculator fuelCalculator = new FuelCalculator(1 , 2);
		assertEquals(1, fuelCalculator.calculateFuelConsumption(1, false));
		assertEquals(5, fuelCalculator.calculateFuelConsumption(5, false));
	}

	@Test
	public void testCalculateEncumberedFuelConsumption() {
		FuelCalculator fuelCalculator = new FuelCalculator(1 , 2);
		assertEquals(2, fuelCalculator.calculateFuelConsumption(1, true));
		assertEquals(10, fuelCalculator.calculateFuelConsumption(5, true));
	}
}