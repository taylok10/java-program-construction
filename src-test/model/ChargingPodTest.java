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
	private ChargingPod one;

	@Before
	public void setup() {
		one = new ChargingPod(0, 0);
	}

	@Test
	public void testUIDFormat() {
		assertTrue(one.getUID().matches("^C[0-9]+$"));
	}

	@Test
	public void testNewChargingPodIncrementsUID() {
		int currentUID = Integer.parseInt(one.getUID().replaceFirst("C", ""));
		ChargingPod two = new ChargingPod(0, 1);
		assertEquals("C" + ++currentUID, two.getUID());
	}

	@Test
	public void testGetPosition() {
		assertArrayEquals(new int[] { 0, 0 }, one.getPosition());
	}
}