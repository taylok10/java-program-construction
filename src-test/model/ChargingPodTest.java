/**
 * 
 */
package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class ChargingPodTest {
	private static final String IDENTIFIER = "c";
	private static final Cell LOCATION_ONE = new Cell(0,0);
	private static final Cell LOCATION_TWO = new Cell(0,1);
	private ChargingPod chargingPodOne;

	@Before
	public void setup() {
		chargingPodOne = new ChargingPod(LOCATION_ONE, 1);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(chargingPodOne.getUID().matches("^" + IDENTIFIER + "[0-9]+$"));
	}

	@Test
	public void testNewChargingPodIncrementsUID() {
		int currentUID = Integer.parseInt(chargingPodOne.getUID().replaceFirst(IDENTIFIER, ""));
		ChargingPod chargingPodTwo = new ChargingPod(LOCATION_TWO, 1);
		assertEquals(IDENTIFIER + ++currentUID, chargingPodTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertEquals(LOCATION_ONE, chargingPodOne.getPosition());
	}
}