package com.prodyna.pac.aaa.pilot;

/**
 * Contains all named query constants for pilot related entities.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface PilotNamedQueries {

	/** Name for the query to retrieve all pilots. */
	String SELECT_ALL_PILOTS = "select_all_pilots";

	/** Name for the query to retrieve an pilot by its user name. */
	String SELECT_PILOT_BY_USERNAME = "select_pilot_by_username";

	/** Name for the query to retrieve all license. */
	String SELECT_ALL_LICENSES = "select_all_licenses";

	/** Name for the query to retrieve an license by its id. */
	String SELECT_LICENSE_BY_ID = "select_license_by_id";
}
