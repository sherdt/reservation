package com.prodyna.pac.aaa.auth;

/**
 * 
 * Enumeration with all roles the application requires.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public enum Role {

	/**
	 * Empty role which can be used as default.
	 */
	NONE,

	/**
	 * Role for administrative functions.
	 */
	ADMIN,

	/**
	 * Pilot role to indicate the user may perform operations allowed for the pilot.
	 */
	PILOT
}
