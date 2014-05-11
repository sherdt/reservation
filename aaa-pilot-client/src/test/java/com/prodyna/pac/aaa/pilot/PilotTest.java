/**
 * 
 */
package com.prodyna.pac.aaa.pilot;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Tests for {@link Pilot}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class PilotTest {

	/**
	 * Test method for {@link com.prodyna.pac.aaa.pilot.Pilot#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(Pilot.class).verify();
	}

}
