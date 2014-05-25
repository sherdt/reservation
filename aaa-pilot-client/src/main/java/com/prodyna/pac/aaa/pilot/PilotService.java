package com.prodyna.pac.aaa.pilot;

import java.util.List;

import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;

/**
 * CRUD service interface for the {@link Pilot}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface PilotService {

	/**
	 * Creates a new pilot.
	 * 
	 * @param pilot
	 *            New pilot to create.
	 * @return Created pilot.
	 */
	Pilot createPilot(Pilot pilot);

	/**
	 * Retrieves a pilot represented by its user name.
	 * 
	 * @param username
	 *            User name to retrieve the pilot for.
	 * 
	 * @return The pilot represented by the given user name.
	 * @throws EntityNotFoundException
	 *             If no {@link Pilot} could be found for given user name.
	 */
	Pilot readPilot(String username) throws EntityNotFoundException;

	/**
	 * Retrieves a list of all pilots.
	 * 
	 * @return List of all available pilots.
	 */
	List<Pilot> readPilots();

	/**
	 * Updates given pilot.
	 * 
	 * @param pilot
	 *            Pilot to update.
	 * @return Updated pilot.
	 */
	Pilot updatePilot(Pilot pilot);

	/**
	 * Deletes the pilot with given user name.
	 * 
	 * @param username
	 *            User name of the pilot to delete.
	 */
	void deletePilot(String username);
}
