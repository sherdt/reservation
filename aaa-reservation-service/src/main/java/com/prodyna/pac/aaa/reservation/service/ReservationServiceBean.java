/**
 * 
 */
package com.prodyna.pac.aaa.reservation.service;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.reservation.Reservation;
import com.prodyna.pac.aaa.reservation.ReservationNamedQueries;
import com.prodyna.pac.aaa.reservation.service.ReservationService;

/**
 * Session Bean implementation class ReservationServiceBean for {@link ReservationService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class ReservationServiceBean implements ReservationService {

	/** Entity manger for DB access. */
	@Inject
	private EntityManager entityManager;

	@Override
	public Reservation createReservation(final Reservation reservation) {
		if (reservation.getId() == null) {
			reservation.setId(UUID.randomUUID().toString());
		}

		this.entityManager.persist(reservation);
		return reservation;
	}

	@Override
	public Reservation readReservation(final String id) throws EntityNotFoundException {
		final Reservation reservation = this.entityManager.find(Reservation.class, id);
		if (reservation == null) {
			throw new EntityNotFoundException("Reservation could not be found for given is [" + id + "]");
		}

		return reservation;
	}

	@Override
	public List<Reservation> readReservations() {
		return this.entityManager.createNamedQuery(ReservationNamedQueries.SELECT_ALL_RESERVATIONS, Reservation.class)
				.getResultList();
	}

	@Override
	public Reservation updateReservation(final Reservation reservation) {
		return this.entityManager.merge(reservation);
	}

	@Override
	public void deleteReservation(final String id) {
		final Reservation reservation = this.entityManager.find(Reservation.class, id);
		this.entityManager.remove(reservation);
	}

}
