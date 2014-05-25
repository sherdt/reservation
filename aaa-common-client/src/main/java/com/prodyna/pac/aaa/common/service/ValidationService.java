package com.prodyna.pac.aaa.common.service;

import com.prodyna.pac.aaa.common.exception.ResourceInvalidException;

/**
 * Generic validation service definition.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * @param <T>
 *            Type of the resource to validate.
 * 
 */
public interface ValidationService<T> {

	/**
	 * Checks if given resource is valid and consistent to the data in the database.
	 * 
	 * @param resource
	 *            The resource to check.
	 * 
	 * @throws ResourceInvalidException
	 *             If validation fails.
	 */
	void validate(T resource) throws ResourceInvalidException;

}
