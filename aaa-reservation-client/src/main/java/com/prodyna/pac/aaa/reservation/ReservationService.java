package com.prodyna.pac.aaa.reservation;

import java.util.List;

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
	 */
	Reservation readReservation(String id);

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
