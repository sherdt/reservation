package com.prodyna.pac.aaa.reservation;

import java.util.List;

import com.prodyna.pac.aaa.common.exceptions.EntitiyNotFoundException;

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
	 * @param reservationState
	 *            New reservation state to create.
	 * @return Created reservation state.
	 */
	ReservationState createReservationState(ReservationState reservationState);

	/**
	 * Retrieves the reservation state by given name.
	 * 
	 * @param name
	 *            Name of the reservation state to read.
	 * 
	 * @return Reservation state represented by given name.
	 * @throws EntitiyNotFoundException
	 *             If no {@link ReservationState} could be found for given name.
	 */
	ReservationState readReservationState(String name) throws EntitiyNotFoundException;

	/**
	 * Retrieves a list of all reservation states.
	 * 
	 * @return List of all available reservation states.
	 */
	List<ReservationState> readReservationStates();

	/**
	 * Updates given reservation state.
	 * 
	 * @param reservationState
	 *            Reservation state to update.
	 * @return Updated reservation state.
	 */
	ReservationState updateReservationState(ReservationState reservationState);

	/**
	 * Deletes reservation state with given name (which is the ID).
	 * 
	 * @param name
	 *            Name of the reservation state to delete.
	 */
	void deleteReservationState(String name);
}
