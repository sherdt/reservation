package com.prodyna.pac.aaa.aircraft.service.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.exception.AircraftInvalidException;
import com.prodyna.pac.aaa.aircraft.exception.AircraftTypeNotFoundException;
import com.prodyna.pac.aaa.aircraft.service.AircraftService;
import com.prodyna.pac.aaa.aircraft.service.AircraftTypeService;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;
import com.prodyna.pac.aaa.pilot.exception.PilotNotFoundException;
import com.prodyna.pac.aaa.reservation.Reservation;
import com.prodyna.pac.aaa.reservation.service.ReservationService;

/**
 * REST Service to manage {@link Aircraft}s. One can use this service to create, update and delete {@link Aircraft}s.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("aircraft")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AircraftRESTService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Aircraft service. */
	@Inject
	private AircraftService aircraftService;

	/** Aircraft type service. */
	@Inject
	private AircraftTypeService aircraftTypeService;

	/** Pilot service. */
	@Inject
	private PilotService pilotService;

	/** Reservation service. */
	@Inject
	private ReservationService reservationService;

	/**
	 * @return List with all available aircrafts.
	 */
	@GET
	@Path("list-aircrafts")
	public List<Aircraft> listAircrafts() {
		return this.aircraftService.readAircrafts();
	}

	/**
	 * Retrieves the aircrafts for the pilot represented by given user name. All aircrafts the pilot has no license to
	 * fly with will be omitted.
	 * 
	 * @param username
	 *            User name of the pilot to retrieve the aircrafts for.
	 * @return JSON representation of the aircraft for the pilot represented by given user name.
	 * 
	 * @throws PilotNotFoundException
	 *             If the {@link Pilot} with given user name doesn't exist.
	 */
	@GET
	@Path("{username}")
	public Set<Aircraft> listAircraftsForPilot(@PathParam("username") final String username) {

		final Set<Aircraft> resultSet = new HashSet<Aircraft>();

		Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new PilotNotFoundException("Given pilot name '" + username + "' is unknown.", e,
					ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		// calculates the list of aircraft types the pilot is allowed to fly
		final List<AircraftType> aircraftTypesPilotAllowedToLoan = new ArrayList<AircraftType>();
		final Set<License> licenses = pilot.getLicenses();
		for (final License license : licenses) {
			final Date expirationDate = license.getExpirationDate();

			// skip all aircraft types the pilot is not allowed to use, because of license expiration
			if (new Date().after(expirationDate)) {
				continue;
			}

			aircraftTypesPilotAllowedToLoan.add(license.getAircraftType());
		}

		// filter out aircrafts the pilot is not allowed to fly
		final List<Aircraft> aircrafts = this.aircraftService.readAircrafts();
		for (final Aircraft aircraft : aircrafts) {
			final AircraftType aircraftType = aircraft.getAircraftType();
			if (aircraftTypesPilotAllowedToLoan.contains(aircraftType)) {
				resultSet.add(aircraft);
			}
		}

		return resultSet;
	}

	/**
	 * Creates an aircraft.
	 * 
	 * @param aircraft
	 *            The aircraft to create.
	 * 
	 * @return JSON representation of successfully created aircraft.
	 * 
	 * @throws AircraftInvalidException
	 *             If given aircraft to save is invalid. Or an aircraft already exists with the same tail sign.
	 */
	@POST
	@Path("/")
	public Aircraft createAircraft(final Aircraft aircraft) {
		final AircraftType aircraftType = aircraft.getAircraftType();

		// check if aircraft type is set
		final String aircraftTypeName = aircraftType.getName();
		if (aircraftTypeName == null) {
			throw new AircraftInvalidException("Could not create the aircraft, you have to set the aircraft type.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the aircraft type already exists
		try {
			this.aircraftTypeService.readAircraftType(aircraftTypeName);
		} catch (final EntityNotFoundException e) {
			throw new AircraftTypeNotFoundException("Could not create the aircraft, used aircraft type '"
					+ aircraftTypeName + "' is unknown.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		// check if tail sign is set
		final String tailSign = aircraft.getTailSign();
		if (tailSign == null) {
			throw new AircraftInvalidException("Could not create the aircraft, you have to set the tail sign.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if tail sign is unique
		try {
			this.aircraftService.readAircraft(tailSign);
			throw new AircraftInvalidException("Could not create the aircraft, the tail sign '" + tailSign
					+ "' is already in use for another aircraft.", ResponseStatusConstants.RESOURCE_INVALID);
		} catch (final EntityNotFoundException e) {
			this.logger.trace("Everything is fine, the aircraft with the tail sign [{}] doesn't exist yet.", tailSign);
		}

		// create the aircraft
		final Aircraft createdAircraft = this.aircraftService.createAircraft(aircraft);

		return createdAircraft;
	}

	/**
	 * Deletes an aircraft.
	 * 
	 * @param aircraft
	 *            The aircraft to delete.
	 * @return Given aircraft.
	 * 
	 * @throws AircraftInvalidException
	 *             If the aircraft doesn't exist.
	 */
	@DELETE
	@Path("/")
	public Aircraft deleteAircraft(final Aircraft aircraft) {

		// check if tail sign is set
		final String tailSign = aircraft.getTailSign();
		if (tailSign == null || tailSign.equals("")) {
			throw new AircraftInvalidException("Could not delete the aircraft without a tail sign.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the aircraft really exists
		try {
			this.aircraftService.readAircraft(tailSign);
		} catch (final EntityNotFoundException e) {
			throw new AircraftInvalidException("Could not delete the aircraft, the aircraft with the tail sign '"
					+ tailSign + "' doesn't exist.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		// check if aircraft is used for a reservation
		final List<Reservation> reservations = this.reservationService.readReservations();
		for (final Reservation reservation : reservations) {
			if (reservation.getAircraft().equals(aircraft)) {
				throw new AircraftInvalidException("Could not delete the aircraft, the aircraft with the tail sign '"
						+ tailSign + "' is referenced by a reservation.", ResponseStatusConstants.RESOURCE_INVALID);
			}
		}

		this.aircraftService.deleteAircraft(tailSign);

		return aircraft;
	}
}
