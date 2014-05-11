package com.prodyna.pac.aaa.common.producer;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Produces an SLF4J logger for the injecting class.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class LoggerProducer {

	/**
	 * Creates a logger for given injection point.
	 * 
	 * @param injectionPoint
	 *            Injection point to produce the {@link Logger} for.
	 * @return The logger.
	 */
	@Produces
	public Logger produceLogger(final InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
	}

}
