/**
 * 
 */
package com.prodyna.pac.aaa.pilot;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Tests for {@link License}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class LicenseTest {

	/**
	 * Test method for {@link com.prodyna.pac.aaa.pilot.License#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		EqualsVerifier.forClass(License.class).verify();
	}

}
