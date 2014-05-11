/**
 * 
 */
package com.prodyna.pac.aaa.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.aaa.common.annotation.Monitored;

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
	public ReservationState readReservationState(final String name) {
		final TypedQuery<ReservationState> namedQuery = this.entityManager.createNamedQuery(
				ReservationNamedQueries.SELECT_RESERVATION_STATE_BY_NAME, ReservationState.class);
		namedQuery.setParameter("reservationStateName", name);

		return namedQuery.getSingleResult();
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
