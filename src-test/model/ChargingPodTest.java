/**
 * 
 */
package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kelly.taylor
 *
 */
public class ChargingPodTest {
	private static final String identifier = "c";
	private ChargingPod chargingPodOne;

	@Before
	public void setup() {
		chargingPodOne = new ChargingPod(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(chargingPodOne.getUID().matches("^" + identifier + "[0-9]+$"));
	}

	@Test
	public void testNewChargingPodIncrementsUID() {
		int currentUID = Integer.parseInt(chargingPodOne.getUID().replaceFirst(identifier, ""));
		ChargingPod chargingPodTwo = new ChargingPod(0, 1);
		assertEquals(identifier + ++currentUID, chargingPodTwo.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, chargingPodOne.getPosition());
	}
}