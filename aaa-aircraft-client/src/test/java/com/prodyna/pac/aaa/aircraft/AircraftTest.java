/**
 * 
 */
package com.prodyna.pac.aaa.aircraft;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link Aircraft}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class AircraftTest {

	/**
	 * Test method for {@link com.prodyna.pac.aaa.aircraft.Aircraft#hashCode()}.
	 */
	@Test
	public void testHashCodeAndEquals() {
		EqualsVerifier.forClass(Aircraft.class).verify();
	}

	/**
	 * Test method for {@link com.prodyna.pac.aaa.aircraft.Aircraft#toString()}.
	 */
	@Test
	public void testToString() {
		final Aircraft aircraft = new Aircraft("ABC");
		final AircraftType aircraftType = new AircraftType("737");
		aircraft.setAircraftType(aircraftType);
		final String toStringResult = aircraft.toString();
		Assert.assertTrue(toStringResult.contains("ABC"));
		Assert.assertTrue(toStringResult.contains("737"));
		Assert.assertEquals("ABC", aircraft.getTailSign());
		Assert.assertEquals(aircraftType, aircraft.getAircraftType());
	}

}
