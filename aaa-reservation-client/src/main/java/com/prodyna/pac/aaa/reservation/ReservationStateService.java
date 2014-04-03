package com.prodyna.pac.aaa.reservation;

import java.util.List;

/**
 * CRUD service interface for {@link ReservationState}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface ReservationStateService {

	/**
	 * Creates a new reservation state.
	 * 
	 * @param ReservationState
	 *            New reservation state to create.
	 * @return Created reservation state.
	 */
	ReservationState createReservationState(ReservationState ReservationState);

	/**
	 * Retrieves a list of all reservation states.
	 * 
	 * @return List of all available reservation states.
	 */
	List<ReservationState> readReservationStates();

	/**
	 * Updates given reservation state.
	 * 
	 * @param ReservationState
	 *            Reservation state to update.
	 * @return Updated reservation state.
	 */
	ReservationState updateReservationState(ReservationState ReservationState);

	/**
	 * Deletes reservation state with given name (which is the ID).
	 * 
	 * @param name
	 *            Name of the reservation state to delete.
	 */
	void deleteReservationState(String name);
}
