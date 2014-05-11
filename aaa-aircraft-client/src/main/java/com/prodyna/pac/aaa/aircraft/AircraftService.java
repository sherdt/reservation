package com.prodyna.pac.aaa.aircraft;

import java.util.List;

import com.prodyna.pac.aaa.common.exceptions.DeleteException;

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
	 */
	Aircraft readAircraft(String tailSign);

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
	 * @throws DeleteException
	 *             If the aircraft is used an cannot be deleted.
	 */
	void deleteAircraft(String tailSign) throws DeleteException;
}
