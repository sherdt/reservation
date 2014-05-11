/**
 * 
 */
package com.prodyna.pac.aaa.aircraft;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Tests for {@link AircraftType}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class AircraftTypeTest {

	/**
	 * Test method for {@link com.prodyna.pac.aaa.aircraft.AircraftType#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(AircraftType.class).verify();
	}

}
