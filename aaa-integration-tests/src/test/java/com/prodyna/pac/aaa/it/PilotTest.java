package com.prodyna.pac.aaa.it;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.AircraftTypeService;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.LicenseService;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;

/**
 * Tests for {@link PilotService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@RunWith(Arquillian.class)
public class PilotTest {

	/** Session bean for pilot service. */
	@Inject
	private PilotService pilotService;

	/** Session bean for license service. */
	@Inject
	private LicenseService licenseService;

	/** Session bean for aircraft type service. */
	@Inject
	private AircraftTypeService aircraftTypeService;

	/**
	 * Creates the deployment package using shrink-wrap.
	 * 
	 * @return created {@link WebArchive}.
	 */
	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war");
		webArchive.addPackages(true, "com.prodyna.pac.aaa");
		webArchive.addAsResource("persistence.xml", "META-INF/persistence.xml");
		webArchive.addAsResource("beans.xml", "META-INF/beans.xml");
		webArchive.toString(true);

		return webArchive;
	}

	/**
	 * Tests all methods of the CRUD service for {@link PilotService}.
	 * 
	 */
	@Test
	public void crudServiceIntegrationTest() {
		final Calendar calOneMonth = Calendar.getInstance(Locale.GERMAN);
		calOneMonth.add(Calendar.MONTH, 1);

		Assert.assertEquals(0, this.licenseService.readLicenses().size());

		final AircraftType aircraftType = this.aircraftTypeService.createAircraftType(new AircraftType("A380"));

		final String id = UUID.randomUUID().toString();
		final License license = new License(id);
		license.setExpirationDate(calOneMonth.getTime());
		license.setAircraftType(aircraftType);

		final License createdLicense = this.licenseService.createLicense(license);
		Assert.assertEquals(1, this.licenseService.readLicenses().size());

		Assert.assertEquals(0, this.pilotService.readPilots().size());
		final Pilot pilot = new Pilot("sherdt");
		pilot.setEmail("sergej.herdt@invalid.inv");
		pilot.setName("Sergej Herdt");
		final Set<License> licenses = new HashSet<>();
		licenses.add(createdLicense);
		pilot.setLicenses(licenses);
		pilot.setPassword("MD5:ahd823ohdn299dhaw");
		final Pilot createdPilot = this.pilotService.createPilot(pilot);
		Assert.assertEquals(1, this.pilotService.readPilots().size());

		final String newEmail = "new.mail@invalid.inv";
		createdPilot.setEmail(newEmail);
		final Pilot updatedPilot = this.pilotService.updatePilot(createdPilot);
		Assert.assertEquals(1, this.pilotService.readPilots().size());
		Assert.assertEquals(newEmail, updatedPilot.getEmail());

		this.pilotService.deletePilot(updatedPilot.getUsername());
		Assert.assertEquals(0, this.pilotService.readPilots().size());

		Assert.assertEquals(0, this.licenseService.readLicenses().size());
	}

}
