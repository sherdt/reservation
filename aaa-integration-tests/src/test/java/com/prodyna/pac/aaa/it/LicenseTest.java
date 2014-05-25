package com.prodyna.pac.aaa.it;

import java.util.Calendar;
import java.util.Locale;
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
import com.prodyna.pac.aaa.aircraft.service.AircraftTypeService;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.LicenseService;

/**
 * Tests for {@link LicenseService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@RunWith(Arquillian.class)
public class LicenseTest {

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
	 * Tests all methods of the CRUD service for {@link LicenseService}.
	 * 
	 */
	@Test
	public void crudServiceIntegrationTest() {
		final Calendar cal = Calendar.getInstance(Locale.GERMAN);
		cal.add(Calendar.DAY_OF_YEAR, 7);
		final Calendar calOneMonth = Calendar.getInstance(Locale.GERMAN);
		calOneMonth.add(Calendar.MONTH, 1);

		Assert.assertEquals(0, this.licenseService.readLicenses().size());

		final AircraftType aircraftType = this.aircraftTypeService.createAircraftType(new AircraftType("A380"));

		final String id = UUID.randomUUID().toString();
		final License license = new License(id);
		license.setExpirationDate(cal.getTime());
		license.setAircraftType(aircraftType);

		final License createdLicense = this.licenseService.createLicense(license);
		Assert.assertEquals(1, this.licenseService.readLicenses().size());
		Assert.assertEquals(cal.getTimeInMillis(), createdLicense.getExpirationDate().getTime());

		createdLicense.setExpirationDate(calOneMonth.getTime());
		final License updatedLicense = this.licenseService.updateLicense(createdLicense);
		Assert.assertEquals(1, this.licenseService.readLicenses().size());
		Assert.assertEquals(calOneMonth.getTimeInMillis(), updatedLicense.getExpirationDate().getTime());

		this.licenseService.deleteLicense(id);
		Assert.assertEquals(0, this.licenseService.readLicenses().size());
	}

}
