package com.prodyna.pac.aaa.common.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;
import com.prodyna.pac.aaa.common.exception.ExceptionalResponse;

/**
 * REST exception handler for all aircraft allocation app runtime exceptions.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Provider
public class AircraftAllocationAppRuntimeExceptionHandler implements
		ExceptionMapper<AircraftAllocationAppRuntimeException> {

	@Override
	public Response toResponse(final AircraftAllocationAppRuntimeException exception) {

		String message = exception.getMessage();
		if (message == null) {
			message = "Unfortunately the developer was too lazy to provide a nice message you could understand. Please interpret the error code yourself.";
		}

		final Response response = Response.status(exception.getErrorCode())
				.entity(new ExceptionalResponse(exception.getErrorCode(), message)).build();

		return response;
	}

}
