package com.prodyna.pac.aaa.aircraft;

/**
 * Contains all named query constants.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface AircraftNamedQueries {

	/** Name for the query to retrieve all aircraft types. */
	String SELECT_ALL_AIRCRAFT_TYPES = "select_all_aircraft_types";

	/** Name for the query to retrieve an aircraft type by its name. */
	String SELECT_AIRCRAFT_TYPE_BY_NAME = "select_aircraft_type_by_name";

	/** Name for the query to retrieve all aircrafts. */
	String SELECT_ALL_AIRCRAFTS = "select_all_aircrafts";

	/** Name for the query to retrieve an aircraft by its tail sign. */
	String SELECT_AIRCRAFT_BY_TAIL_SIGN = "select_aircraft_by_tail_sign";

}
