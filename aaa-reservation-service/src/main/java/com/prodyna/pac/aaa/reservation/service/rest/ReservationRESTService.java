package com.prodyna.pac.aaa.reservation.service.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.auth.AuthenticationSecured;
import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResourceInvalidException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;
import com.prodyna.pac.aaa.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.aaa.reservation.Reservation;
import com.prodyna.pac.aaa.reservation.ReservationState;
import com.prodyna.pac.aaa.reservation.exception.ReservationInvalidException;
import com.prodyna.pac.aaa.reservation.exception.ReservationNotCreatedException;
import com.prodyna.pac.aaa.reservation.service.ReservationService;
import com.prodyna.pac.aaa.reservation.service.ReservationStateService;
import com.prodyna.pac.aaa.reservation.service.ReservationValidationService;

/**
 * REST Service to manage {@link Reservation}s. One can use this service to reserve {@link Aircraft}s for a
 * {@link Pilot} or return the {@link Aircraft} or cancel the {@link Reservation}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("/reservation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Monitored
public class ReservationRESTService {

	/** Constant for error message if an pilot could not be found in DB for given user name. */
	private static final String PILOT_NOT_FOUND_ERROR_MESSAGE = "Could not find pilot in DB for given user name [{}].";

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Pilot service to retrieve the data from. */
	@Inject
	private PilotService pilotService;

	/** Reservation service to retrieve the data from. */
	@Inject
	private ReservationService reservationService;

	/** Reservation state service to retrieve the data from. */
	@Inject
	private ReservationStateService reservationStateService;

	/** Reservation validation service to check the validity of a reservation. */
	@Inject
	private ReservationValidationService reservationValidationService;

	/**
	 * Retrieves all reservations.
	 * 
	 * @return JSON representation of all the reservations.
	 */
	@GET
	@Path("/list-reservations")
	public List<Reservation> listReservations() {

		final List<Reservation> allReservations = this.reservationService.readReservations();

		return allReservations;
	}

	/**
	 * Retrieves the reservations for the {@link Pilot} represented by given user name.
	 * 
	 * @param username
	 *            User name of the {@link Pilot} to retrieve the reservations for.
	 * @return JSON representation of the reservations for the {@link Pilot} represented by given user name.
	 */
	@GET
	@Path("/{username}")
	@AuthenticationSecured(role = Role.PILOT)
	public Set<Reservation> listReservationForPilot(@PathParam("username") final String username) {

		Set<Reservation> reservations = null;
		try {
			final Pilot pilot = this.pilotService.readPilot(username);
			reservations = pilot.getReservations();
		} catch (final EntityNotFoundException e) {
			throw new PilotNotFoundException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		if (reservations == null) {
			reservations = new HashSet<Reservation>();
		}

		return reservations;
	}

	/**
	 * Retrieves the reservations for the pilot represented by given user name. Only reservations in date range will be
	 * returned. If given start date is equals to the start date of an reservation, the reservation is not found.
	 * 
	 * If no reservation could be found, an empty list is returned.
	 * 
	 * @param username
	 *            User name for the {@link Pilot} to retrieve the {@link Reservation}s for.
	 * @param startDate
	 *            Start date of the range to retrieve the {@link Reservation}s for.
	 * @param endDate
	 *            End date of the range to retrieve the {@link Reservation}s for.
	 * @return List of {@link Reservation}s for given {@link Pilot} in given date range. An empty list is returned if
	 *         either the {@link Pilot} could not be found or if no {@link Reservation}s could be found for the
	 *         {@link Pilot} represented by given user name.
	 */
	@GET
	@Path("/{username}/{startDate}/{endDate}")
	@AuthenticationSecured(role = Role.PILOT)
	public Set<Reservation> listReservationForPilotAndDateRange(@PathParam("username") final String username,
			@PathParam("startDate") final long startDate, @PathParam("endDate") final long endDate) {

		final Set<Reservation> resultSet = new HashSet<Reservation>();

		try {
			final Pilot pilot = this.pilotService.readPilot(username);
			final Set<Reservation> reservations = pilot.getReservations();
			for (final Reservation reservation : reservations) {
				if (reservation.getStartDate().getTime() >= startDate && reservation.getEndDate().getTime() <= endDate) {
					resultSet.add(reservation);
				}
			}
		} catch (final EntityNotFoundException e) {
			this.logger.debug(PILOT_NOT_FOUND_ERROR_MESSAGE, username, e);

			throw new PilotNotFoundException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		return resultSet;
	}

	/**
	 * Retrieves a list of {@link Reservation}s for all the other {@link Pilot}s but not the one represented by given
	 * user name.
	 * 
	 * @param username
	 *            User name for the {@link Pilot} to skip the {@link Reservation}s for.
	 * @return List of {@link Reservation}s of other pilots. The list might be empty, but the result is never null.
	 */
	@GET
	@Path("/other-than/{username}")
	@AuthenticationSecured(role = Role.PILOT)
	public Set<Reservation> listOtherReservationsForDateRange(@PathParam("username") final String username) {
		return this.listOtherReservationsForDateRange(username, 0, Long.MAX_VALUE);
	}

	/**
	 * Retrieves a list of {@link Reservation}s for all the other {@link Pilot}s but not the one represented by given
	 * user name.
	 * 
	 * @param username
	 *            User name for the {@link Pilot} to skip the {@link Reservation}s for.
	 * @param startDate
	 *            Start date of the range to retrieve the reservations for.
	 * @param endDate
	 *            End date of the range to retrieve the reservations for.
	 * @return List of {@link Reservation}s of other pilots. The list might be empty, but the result is never null.
	 */
	@GET
	@Path("/other-than/{username}/{startDate}/{endDate}")
	@AuthenticationSecured(role = Role.PILOT)
	public Set<Reservation> listOtherReservationsForDateRange(@PathParam("username") final String username,
			@PathParam("startDate") final long startDate, @PathParam("endDate") final long endDate) {

		final Set<Reservation> resultSet = new HashSet<Reservation>();

		Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			this.logger.debug(PILOT_NOT_FOUND_ERROR_MESSAGE, username, e);

			throw new PilotNotFoundException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		final Set<Reservation> pilotsReservations = pilot.getReservations();

		final List<Reservation> allReservations = this.reservationService.readReservations();
		for (final Reservation reservation : allReservations) {

			// only reservations for other pilots are requested
			if (pilotsReservations.contains(reservation)) {
				continue;
			}

			// skip reservations outside of the date range
			if (reservation.getStartDate().getTime() > endDate || reservation.getEndDate().getTime() < startDate) {
				continue;
			}

			resultSet.add(reservation);
		}

		return resultSet;
	}

	/**
	 * Creates given reservation for the pilot represented by the given user name.
	 * 
	 * @param username
	 *            User name for the {@link Pilot} to create a new {@link Reservation} for.
	 * @param reservation
	 *            {@link Reservation} to create for the {@link Pilot} represented by given user name.
	 * @return Created reservation.
	 */
	@POST
	@Path("/{username}")
	@AuthenticationSecured(role = Role.PILOT)
	public Reservation createReservationForPilot(@PathParam("username") final String username,
			final Reservation reservation) {
		this.logger.debug("Request to create a new reservation [{}] for user [{}] has been received.", reservation,
				username);

		// id is required but we don't want to force the client to generate one, so we are doing it here
		if (reservation.getId() == null || "".equals(reservation.getId())) {
			reservation.setId(UUID.randomUUID().toString());
		}

		Reservation createdReservation = null;
		try {

			// set correct reservation state for the new reservation
			reservation.setReservationState(this.readOrCreateReservationState("RESERVED"));

			// check if reservation is valid
			try {
				this.reservationValidationService.validate(reservation);
			} catch (final ResourceInvalidException e) {
				throw new ReservationInvalidException(e.getMessage(), e, ResponseStatusConstants.RESOURCE_INVALID);
			}

			// get all reservations for given pilot
			final Pilot pilot = this.pilotService.readPilot(username);
			final Set<Reservation> reservations = pilot.getReservations();

			final List<Reservation> allReservations = this.reservationService.readReservations();

			// check if user already has a reservation at that specific time
			for (final Reservation pilotsReservation : reservations) {
				this.checkTimeRangeOverlapping(reservation, pilotsReservation,
						"You are not allowed to reserve more than one aircraft at the same time.");
			}

			// checks if the aircraft can be reserved at that specific time
			for (final Reservation aReservation : allReservations) {

				// check only reservations for same aircraft
				if (aReservation.getAircraft().equals(reservation.getAircraft())) {
					this.checkTimeRangeOverlapping(reservation, aReservation,
							"This aircraft is already reserved. Please choose a different aircraft or another time slot.");
				}
			}

			// check if user is allowed to reserve this aircraft
			final AircraftType aircraftType = reservation.getAircraft().getAircraftType();
			final Set<License> licenses = pilot.getLicenses();
			boolean hasValidLicense = false;
			final Date now = new Date();
			for (final License license : licenses) {
				// ignore all expired licenses
				if (now.after(license.getExpirationDate())) {
					continue;
				}

				// check if aircraft type matches the one in the license
				if (aircraftType.equals(license.getAircraftType())) {
					hasValidLicense = true;
					break;
				}
			}
			if (!hasValidLicense) {
				throw new ReservationInvalidException("The pilot '" + username
						+ "' has no valid license for the aircraft type '" + aircraftType + "'",
						ResponseStatusConstants.RESOURCE_INVALID);
			}

			// create new reservation
			createdReservation = this.reservationService.createReservation(reservation);

			// update pilots reservations
			reservations.add(createdReservation);
			this.pilotService.updatePilot(pilot);
		} catch (final EntityNotFoundException e) {
			this.logger.debug(PILOT_NOT_FOUND_ERROR_MESSAGE, username, e);

			throw new PilotNotFoundException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		if (createdReservation == null) {
			throw new ReservationNotCreatedException("The reservation could not be created.",
					ResponseStatusConstants.RESOURCE_NOT_CREATED);
		}

		return createdReservation;
	}

	/**
	 * Checks if the date ranges for given reservations are overlapping each other. The check is only performed if the
	 * second reservation is already in status RESERVED or LEANT.
	 * 
	 * <pre>
	 * Case 1:
	 *        |----d1-----|
	 * |------d2------|
	 * 
	 * Case 2:
	 * |------d1------|
	 *       |------d2------|
	 * 	 
	 * Case 3:
	 *    |----d1---|
	 * |-------d2--------|
	 * 
	 * All tests are covered by the following two if conditions:
	 * start1 <= end2 and start2 <= end1
	 * 
	 * </pre>
	 * 
	 * @param firstReservation
	 *            First reservation to get the start and end date from.
	 * @param secondReservation
	 *            Second reservation to get the start and end date from.
	 * @param errorMessage
	 *            The message to use for throwing the ReservationInvalidException.
	 * 
	 * @throws ReservationInvalidException
	 *             If the ranges of given reservations are overlapping each other.
	 */
	private void checkTimeRangeOverlapping(final Reservation firstReservation, final Reservation secondReservation,
			final String errorMessage) {

		final String reservationState = secondReservation.getReservationState().getName();
		if (("RESERVED".equals(reservationState) || "LEANT".equals(reservationState))
				&& secondReservation.getStartDate().getTime() < firstReservation.getEndDate().getTime()
				&& firstReservation.getStartDate().getTime() < secondReservation.getEndDate().getTime()) {

			throw new ReservationInvalidException(errorMessage, ResponseStatusConstants.RESOURCE_INVALID);
		}
	}

	/**
	 * Deletes the reservation represented by given id.
	 * 
	 * @param id
	 *            Id of the reservation to delete.
	 * 
	 */
	@DELETE
	@Path("/{id}")
	@AuthenticationSecured(role = Role.ADMIN)
	public void deleteReservation(@PathParam("id") final String id) {
		Reservation storedReservation;
		try {
			storedReservation = this.reservationService.readReservation(id);
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("Reservation could not been deleted, it doesn't exist.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		final List<Pilot> allPilots = this.pilotService.readPilots();
		for (final Pilot pilot : allPilots) {
			final Set<Reservation> reservations = pilot.getReservations();
			if (reservations.contains(storedReservation)) {
				reservations.remove(storedReservation);
				break;
			}
		}

		this.reservationService.deleteReservation(id);
	}

	/**
	 * Cancels given reservation.
	 * 
	 * @param username
	 *            User name to cancel the reservation for.
	 * 
	 * @param reservation
	 *            {@link Reservation} to cancel.
	 * @return Created reservation.
	 */
	@PUT
	@Path("/{username}/cancel")
	@AuthenticationSecured(role = Role.PILOT)
	public Reservation cancelReservation(@PathParam("username") final String username, final Reservation reservation) {
		Reservation storedReservation;
		try {
			storedReservation = this.reservationService.readReservation(reservation.getId());
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("You tried to cancel an reservation which doesn't exist.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		final Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the user is trying to cancel foreign reservation, this is not allowed
		if (!pilot.getReservations().contains(reservation)) {
			throw new ReservationInvalidException("You are not allowed to cancel foreign reservations.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		Reservation updatedReservation;
		final ReservationState reservationState = storedReservation.getReservationState();
		if ("RESERVED".equals(reservationState.getName())) {
			storedReservation.setReservationState(this.readOrCreateReservationState("CANCELLED"));
			updatedReservation = this.reservationService.updateReservation(storedReservation);
		} else {
			throw new ReservationInvalidException("You are not allowed to cancel this reservation, it's not reserved.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		return updatedReservation;
	}

	/**
	 * Return the aircraft.
	 * 
	 * @param username
	 *            User name to update the reservation for.
	 * 
	 * @param reservation
	 *            {@link Reservation} to update to <code>RETURNED</code>.
	 * @return Created reservation.
	 */
	@PUT
	@Path("/{username}/return")
	@AuthenticationSecured(role = Role.PILOT)
	public Reservation returnAircraft(@PathParam("username") final String username, final Reservation reservation) {
		Reservation storedReservation;
		try {
			storedReservation = this.reservationService.readReservation(reservation.getId());
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("You tried to return an reservation which doesn't exist.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		final Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the user is trying to return foreign reservation, this is not allowed
		if (!pilot.getReservations().contains(reservation)) {
			throw new ReservationInvalidException("You are not allowed to return foreign reservations.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		Reservation updatedReservation;
		final ReservationState reservationState = storedReservation.getReservationState();
		if ("LEANT".equals(reservationState.getName())) {
			storedReservation.setReservationState(this.readOrCreateReservationState("RETURNED"));
			updatedReservation = this.reservationService.updateReservation(storedReservation);
		} else {
			throw new ReservationInvalidException("You are not allowed to return this reservation, it's not leant.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		return updatedReservation;
	}

	/**
	 * Lending the reserved aircraft.
	 * 
	 * @param username
	 *            User name to update the reservation for.
	 * 
	 * @param reservation
	 *            {@link Reservation} to update to <code>LENT</code>.
	 * @return Created reservation.
	 */
	@PUT
	@Path("/{username}/lean")
	@AuthenticationSecured(role = Role.PILOT)
	public Reservation leanAircraft(@PathParam("username") final String username, final Reservation reservation) {
		Reservation storedReservation;
		try {
			storedReservation = this.reservationService.readReservation(reservation.getId());
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("You tried to lean an reservation which doesn't exist.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		final Date now = new Date();
		if (storedReservation.getStartDate().after(now)) {
			throw new ReservationInvalidException("You cannot lean the aircraft '"
					+ reservation.getAircraft().getTailSign() + "' yet, please try again later.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		if (now.after(storedReservation.getEndDate())) {
			throw new ReservationInvalidException("The reservation end date is exceeded, sorry you are too late.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		final Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new ReservationInvalidException("No pilot could be found for given user name '" + username + "'.", e,
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the user is trying to lean foreign reservation, this is not allowed
		if (!pilot.getReservations().contains(reservation)) {
			throw new ReservationInvalidException("You are not allowed to lean foreign reservations.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		Reservation updatedReservation;
		final ReservationState reservationState = storedReservation.getReservationState();
		if ("RESERVED".equals(reservationState.getName())) {
			storedReservation.setReservationState(this.readOrCreateReservationState("LEANT"));
			updatedReservation = this.reservationService.updateReservation(storedReservation);
		} else {
			throw new ReservationInvalidException("You are not allowed to lean this reservation, it's not reserved.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		return updatedReservation;
	}

	/**
	 * Creates a new reservation state if it doesn't exist yet.
	 * 
	 * @param stateName
	 *            Reservation state e.g. CANCELLED, RESERVED, ...
	 * @return Created or loaded reservation state with given stateName.
	 */
	private ReservationState readOrCreateReservationState(final String stateName) {
		ReservationState reservationState;
		try {
			reservationState = this.reservationStateService.readReservationState(stateName);
		} catch (final EntityNotFoundException e) {
			reservationState = this.reservationStateService.createReservationState(new ReservationState(stateName));
		}
		return reservationState;
	}
}
