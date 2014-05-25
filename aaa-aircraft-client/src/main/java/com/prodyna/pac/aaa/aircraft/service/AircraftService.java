package com.prodyna.pac.aaa.aircraft.service;

import java.util.List;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;

/**
 * CRUD service interface for the {@link Aircraft}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface AircraftService {

	/**
	 * Creates a new aircraft.
	 * 
	 * @param Aircraft
	 *            New aircraft to create.
	 * @return Created aircraft.
	 */
	Aircraft createAircraft(Aircraft Aircraft);

	/**
	 * Retrieves an aircraft represented by its tail sign.
	 * 
	 * @param tailSign
	 *            Tail sign to retrieve the aircraft for.
	 * 
	 * @return The aircraft represented by given tail sign.
	 * @throws EntityNotFoundException
	 *             If no {@link Aircraft} could be found for given tail sign.
	 */
	Aircraft readAircraft(String tailSign) throws EntityNotFoundException;

	/**
	 * Retrieves a list of all aircrafts.
	 * 
	 * @return List of all available aircrafts.
	 */
	List<Aircraft> readAircrafts();

	/**
	 * Updates given aircraft.
	 * 
	 * @param Aircraft
	 *            Aircraft to update.
	 * @return Updated aircraft.
	 */
	Aircraft updateAircraft(Aircraft Aircraft);

	/**
	 * Deletes the aircraft with given tail sign (which is the ID).
	 * 
	 * @param tailSign
	 *            Tail sign of the aircraft to delete.
	 */
	void deleteAircraft(String tailSign);
}
