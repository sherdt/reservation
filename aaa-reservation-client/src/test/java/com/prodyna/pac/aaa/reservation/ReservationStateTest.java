/**
 * 
 */
package com.prodyna.pac.aaa.reservation;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Tests for {@link ReservationState}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class ReservationStateTest {

	/**
	 * Test method for {@link com.prodyna.pac.aaa.reservation.ReservationState#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(ReservationState.class).verify();
	}

}
