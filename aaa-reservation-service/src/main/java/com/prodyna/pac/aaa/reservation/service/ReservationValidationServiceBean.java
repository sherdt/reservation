/**
 * 
 */
package com.prodyna.pac.aaa.reservation.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.service.AircraftValidationService;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResourceInvalidException;
import com.prodyna.pac.aaa.reservation.Reservation;

/**
 * Session Bean implementation class ReservationServiceBean for {@link ReservationValidationService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class ReservationValidationServiceBean implements ReservationValidationService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Aircraft validation service to validate an aircraft. */
	@Inject
	private AircraftValidationService aircraftValidationService;

	/** Reservation service to read the reservation. */
	@Inject
	private ReservationService reservationService;

	@Override
	public void validate(final Reservation reservation) throws ResourceInvalidException {
		final String id = reservation.getId();

		if (id == null || id.isEmpty()) {
			throw new ResourceInvalidException("Reservation id cannot be empty, please provide a generated id.");
		}

		try {
			this.reservationService.readReservation(id);
			throw new ResourceInvalidException("Reservation with id '" + id
					+ "' already exists, please use a differnt id.");
		} catch (final EntityNotFoundException e) {
			this.logger.trace("Everything is fine, no reservation exists with id [{}].", id);
		}

		final Aircraft aircraft = reservation.getAircraft();

		if (aircraft == null) {
			throw new ResourceInvalidException("Given reservation is invalid. The aircraft to loan is not set.");
		}

		this.aircraftValidationService.validate(aircraft);
	}

}
