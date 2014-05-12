package com.prodyna.pac.aaa.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the version of the web application.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("version")
public class Version {

	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Version.class);

	/**
	 * @return The Version of the reservation application.
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public JsonObject listVersion() {
		String version = "undefined";
		final InputStream resourceAsStream = Version.class
				.getResourceAsStream("/META-INF/maven/com.prodyna.pac.reservation/aaa-web/pom.properties");

		if (resourceAsStream != null) {
			final Properties properties = new Properties();
			try {
				properties.load(resourceAsStream);
				version = properties.getProperty("version", version);
			} catch (final IOException e) {
				LOGGER.error("Could not load pom.properties to retrieve the version.", e);
			} finally {
				try {
					resourceAsStream.close();
				} catch (final IOException e) {
					LOGGER.error("Could not close input stream to pom.properties.", e);
				}
			}
		}

		return Json.createObjectBuilder().add("version", version).build();
	}
}
