package com.prodyna.pac.aaa.reservation.cleanup;

import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.reservation.Reservation;
import com.prodyna.pac.aaa.reservation.ReservationState;
import com.prodyna.pac.aaa.reservation.service.ReservationService;
import com.prodyna.pac.aaa.reservation.service.ReservationStateService;

/**
 * The job is returning all overdue reservation in {@link ReservationState} <code>LEANT</code> by updating the
 * {@link ReservationState} to <code>RETURNED</code>. Clean up job is executed every minute.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Singleton
@Monitored
public class ReservationCleanupBean {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Reservation service instance. */
	@Inject
	private ReservationService reservationService;

	/** Reservation state service instance. */
	@Inject
	private ReservationStateService reservationStateService;

	/**
	 * Scheduled cleanup is executed every minute. All overdue reservations in state <code>LEANT</code> are returned.
	 */
	@Schedule(second = "0", minute = "*/1", hour = "*", persistent = false)
	public void returnOverdueReservations() {
		this.logger.trace("Reservation cleanup has been triggered.");

		final Date now = new Date();

		final List<Reservation> allReservations = this.reservationService.readReservations();

		ReservationState reservationStateReturned;
		try {
			reservationStateReturned = this.reservationStateService.readReservationState("RETURNED");
		} catch (final EntityNotFoundException e) {
			reservationStateReturned = this.reservationStateService.createReservationState(new ReservationState(
					"RETURNED"));
		}

		for (final Reservation reservation : allReservations) {
			if ("LEANT".equals(reservation.getReservationState().getName()) && now.after(reservation.getEndDate())) {
				reservation.setReservationState(reservationStateReturned);
				this.reservationService.updateReservation(reservation);
			}
		}
	}
}