package com.prodyna.pac.aaa.reservation;

import java.util.List;

import com.prodyna.pac.aaa.common.exceptions.EntitiyNotFoundException;

/**
 * CRUD service interface for {@link Reservation}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface ReservationService {

	/**
	 * Creates a new reservation. If id of given reservation is not set/<code>null</code>, a new UUID will be generated
	 * automatically.
	 * 
	 * @param reservation
	 *            New reservation to create.
	 * @return Created reservation.
	 */
	Reservation createReservation(Reservation reservation);

	/**
	 * Retrieves the reservation by given id.
	 * 
	 * @param id
	 *            Id of the reservation to read.
	 * 
	 * @return Reservation represented by given name.
	 * @throws EntitiyNotFoundException
	 *             If no {@link Reservation} could be found for given id.
	 */
	Reservation readReservation(String id) throws EntitiyNotFoundException;

	/**
	 * Retrieves a list of all reservations.
	 * 
	 * @return List of all available reservations.
	 */
	List<Reservation> readReservations();

	/**
	 * Updates given reservation.
	 * 
	 * @param reservation
	 *            Reservation to update.
	 * @return Updated reservation.
	 */
	Reservation updateReservation(Reservation reservation);

	/**
	 * Deletes reservation with given id.
	 * 
	 * @param id
	 *            Id of the reservation to delete.
	 */
	void deleteReservation(String id);
}
