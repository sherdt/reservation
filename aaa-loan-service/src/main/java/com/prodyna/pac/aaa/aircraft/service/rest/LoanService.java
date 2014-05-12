package com.prodyna.pac.aaa.aircraft.service.rest;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.common.exceptions.EntitiyNotFoundException;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;
import com.prodyna.pac.aaa.reservation.Reservation;

/**
 * REST Service to manage {@link Reservation}s. One can use this service to reserve {@link Aircraft}s for a
 * {@link Pilot} or return the {@link Aircraft} or cancel the {@link Reservation}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("loan")
public class LoanService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Pilot service to use for REST calls. */
	@Inject
	private PilotService pilotService;

	/**
	 * Retrieves the loans for the {@link Pilot} represented by given user name.
	 * 
	 * @param username
	 *            User name of the {@link Pilot} to retrieve the loans for.
	 * @return JSON representation of the loans for the {@link Pilot} represented by given user name.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{username}/")
	public Set<Reservation> listLoansForPilot(@PathParam("username") final String username) {

		Set<Reservation> reservations = null;
		try {
			final Pilot pilot = this.pilotService.readPilot(username);
			reservations = pilot.getReservations();
		} catch (final EntitiyNotFoundException e) {
			this.logger.debug("No pilot could be found for given username [{}]", username, e);
		}

		if (reservations == null) {
			reservations = new HashSet<Reservation>();
		}

		return reservations;
	}
}
