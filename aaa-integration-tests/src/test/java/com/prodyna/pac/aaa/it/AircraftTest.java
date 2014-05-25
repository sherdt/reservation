package com.prodyna.pac.aaa.it;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.service.AircraftService;
import com.prodyna.pac.aaa.aircraft.service.AircraftTypeService;
import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppException;
import com.prodyna.pac.aaa.common.exception.DeleteException;

/**
 * Tests for {@link AircraftTypeService} and {@link AircraftService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@RunWith(Arquillian.class)
public class AircraftTest {

	/** Session bean for aircraft type service. */
	@Inject
	private AircraftTypeService aircraftTypeService;

	/** Session bean for aircraft service. */
	@Inject
	private AircraftService aircraftService;

	/**
	 * Creates the deployment using shrinkwrap.
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
	 * Tests all methods of the crud service for {@link AircraftType}.
	 * 
	 * @throws DeleteException
	 *             If deletion failed.
	 * @throws AircraftAllocationAppException
	 *             Should not be thrown in this test.
	 */
	@Test
	public void crudServiceIntegrationTest() throws AircraftAllocationAppException {

		Assert.assertEquals(0, this.aircraftTypeService.readAircraftTypes().size());

		final AircraftType aircraftType = new AircraftType();
		aircraftType.setName("A380");
		final AircraftType createdAircraftType = this.aircraftTypeService.createAircraftType(aircraftType);
		Assert.assertNotNull(createdAircraftType.getName());
		Assert.assertEquals(1, this.aircraftTypeService.readAircraftTypes().size());

		AircraftType aircraftType2 = new AircraftType();
		aircraftType2.setName("747");
		aircraftType2 = this.aircraftTypeService.createAircraftType(aircraftType2);
		Assert.assertNotNull(createdAircraftType.getName());
		Assert.assertEquals(2, this.aircraftTypeService.readAircraftTypes().size());

		final AircraftType retrievedAircraftType = this.aircraftTypeService.readAircraftType("747");
		Assert.assertEquals(aircraftType2, retrievedAircraftType);

		final Aircraft aircraft = new Aircraft("D-ASJ-47");
		aircraft.setAircraftType(aircraftType);
		final Aircraft createdAircraft = this.aircraftService.createAircraft(aircraft);
		Assert.assertEquals(aircraft.getTailSign(), createdAircraft.getTailSign());

		final Aircraft retrievedAircraft = this.aircraftService.readAircraft("D-ASJ-47");
		Assert.assertEquals(retrievedAircraft, createdAircraft);

		createdAircraft.setAircraftType(aircraftType2);
		final Aircraft updatedAircraft = this.aircraftService.updateAircraft(createdAircraft);
		Assert.assertEquals("747", updatedAircraft.getAircraftType().getName());

		aircraftType2.setName("007");
		final AircraftType updatedAircraftType = this.aircraftTypeService.updateAircraftType(aircraftType2);
		Assert.assertEquals("007", updatedAircraftType.getName());

		this.aircraftService.deleteAircraft(updatedAircraft.getTailSign());
		Assert.assertEquals(0, this.aircraftService.readAircrafts().size());

		this.aircraftTypeService.deleteAircraftType(aircraftType.getName());
		Assert.assertEquals(2, this.aircraftTypeService.readAircraftTypes().size());

		this.aircraftTypeService.deleteAircraftType(aircraftType2.getName());
		Assert.assertEquals(1, this.aircraftTypeService.readAircraftTypes().size());

	}

}
