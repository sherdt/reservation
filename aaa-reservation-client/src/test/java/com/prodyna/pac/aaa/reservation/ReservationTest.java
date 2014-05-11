/**
 * 
 */
package com.prodyna.pac.aaa.reservation;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Tests for {@link Reservation}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class ReservationTest {

	/**
	 * Test method for {@link com.prodyna.pac.aaa.reservation.Reservation#hashCode()}.
	 */
	@Test
	public void testHashCodeAndEqulas() {
		EqualsVerifier.forClass(Reservation.class).verify();
	}

}
