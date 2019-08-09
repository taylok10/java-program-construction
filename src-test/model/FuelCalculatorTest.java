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
		assertEquals(1, FuelCalculator.calculateFuelConsumption(1, false));
		assertEquals(5, FuelCalculator.calculateFuelConsumption(5, false));
	}

	@Test
	public void testCalculateEncumberedFuelConsumption() {
		assertEquals(2, FuelCalculator.calculateFuelConsumption(1, true));
		assertEquals(10, FuelCalculator.calculateFuelConsumption(5, true));
	}
}