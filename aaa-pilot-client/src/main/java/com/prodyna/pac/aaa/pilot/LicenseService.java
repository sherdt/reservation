package com.prodyna.pac.aaa.pilot;

import java.util.List;

import com.prodyna.pac.aaa.common.exceptions.EntitiyNotFoundException;

/**
 * CRUD service interface for the {@link License}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface LicenseService {

	/**
	 * Creates a new license.
	 * 
	 * @param license
	 *            New license to create.
	 * @return Created license.
	 */
	License createLicense(License license);

	/**
	 * Retrieves a license represented by its id.
	 * 
	 * @param id
	 *            Id to retrieve the license for.
	 * 
	 * @return The license represented by given id.
	 * @throws EntitiyNotFoundException
	 *             If no {@link License} could be found for given id.
	 */
	License readLicense(String id) throws EntitiyNotFoundException;

	/**
	 * Retrieves a list of all licenses.
	 * 
	 * @return List of all available licenses.
	 */
	List<License> readLicenses();

	/**
	 * Updates given license.
	 * 
	 * @param license
	 *            License to update.
	 * @return Updated license.
	 */
	License updateLicense(License license);

	/**
	 * Deletes the license with given id.
	 * 
	 * @param id
	 *            Id of the license to delete.
	 */
	void deleteLicense(String id);
}
