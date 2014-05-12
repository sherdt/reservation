/**
 * 
 */
package com.prodyna.pac.aaa.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exceptions.EntitiyNotFoundException;

/**
 * Session Bean implementation class ReservationStateServiceBean for {@link ReservationStateService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class ReservationStateServiceBean implements ReservationStateService {

	/** Entity manger for DB access. */
	@Inject
	private EntityManager entityManager;

	@Override
	public ReservationState createReservationState(final ReservationState reservationState) {
		this.entityManager.persist(reservationState);
		return reservationState;
	}

	@Override
	public ReservationState readReservationState(final String name) throws EntitiyNotFoundException {
		final ReservationState reservationState = this.entityManager.find(ReservationState.class, name);
		if (reservationState == null) {
			throw new EntitiyNotFoundException("Reservation state could not be found for given username [" + name + "]");
		}

		return reservationState;
	}

	@Override
	public List<ReservationState> readReservationStates() {
		return this.entityManager.createNamedQuery(ReservationNamedQueries.SELECT_ALL_RESERVATION_STATES,
				ReservationState.class).getResultList();
	}

	@Override
	public ReservationState updateReservationState(final ReservationState reservationState) {
		return this.entityManager.merge(reservationState);
	}

	@Override
	public void deleteReservationState(final String name) {
		final ReservationState reservationState = this.entityManager.find(ReservationState.class, name);
		this.entityManager.remove(reservationState);
	}

}
