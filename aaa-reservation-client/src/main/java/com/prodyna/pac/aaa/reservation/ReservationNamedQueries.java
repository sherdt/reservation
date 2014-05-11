package com.prodyna.pac.aaa.reservation;

/**
 * Contains all named query constants for reservation entities.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface ReservationNamedQueries {

	/** Name for the query to retrieve all reservations. */
	String SELECT_ALL_RESERVATIONS = "select_all_reservations";

	/** Name for the query to retrieve the reservation by its id. */
	String SELECT_RESERVATION_BY_ID = "select_reservation_by_id";

	/** Name for the query to retrieve all reservation states. */
	String SELECT_ALL_RESERVATION_STATES = "select_all_reservation_states";

	/** Name for the query to retrieve the reservation state by its name. */
	String SELECT_RESERVATION_STATE_BY_NAME = "select_reservation_state_by_name";

}
