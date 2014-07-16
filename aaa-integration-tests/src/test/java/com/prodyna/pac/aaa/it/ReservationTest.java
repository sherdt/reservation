package com.prodyna.pac.aaa.it;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.reservation.Reservation;
import com.prodyna.pac.aaa.reservation.ReservationState;
import com.prodyna.pac.aaa.reservation.service.ReservationService;
import com.prodyna.pac.aaa.reservation.service.ReservationStateService;

/**
 * Tests for {@link ReservationStateService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@RunWith(Arquillian.class)
public class ReservationTest {

	/** Session bean for aircraft type service. */
	@Inject
	private AircraftTypeService aircraftTypeService;

	/** Session bean for aircraft service. */
	@Inject
	private AircraftService aircraftService;

	/** Session bean for reservation state service. */
	@Inject
	private ReservationStateService reservationStateService;

	/** Session bean for reservation service. */
	@Inject
	private ReservationService reservationService;

	/**
	 * Creates the deployment package using shrink-wrap.
	 * 
	 * @return created {@link WebArchive}.
	 */
	@Deployment
	public static WebArchive createDeployment() {
		final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test-reservation.war");
		webArchive.addPackages(true, "com.prodyna.pac.aaa");
		webArchive.addAsResource("persistence.xml", "META-INF/persistence.xml");
		webArchive.addAsResource("beans.xml", "META-INF/beans.xml");
		webArchive.toString(true);

		return webArchive;
	}

	/**
	 * Tests all methods of the CRUD service for {@link ReservationStateService}.
	 * 
	 * @throws EntityNotFoundException
	 *             Should not be thrown in this test.
	 * 
	 */
	@Test
	public void reservationStateCrudServiceIntegrationTest() throws EntityNotFoundException {

		Assert.assertEquals(0, this.reservationStateService.readReservationStates().size());

		final ReservationState reservationState = new ReservationState();
		reservationState.setName("NEW");
		this.reservationStateService.createReservationState(reservationState);
		Assert.assertEquals(1, this.reservationStateService.readReservationStates().size());

		reservationState.setName("RESERVED");
		this.reservationStateService.updateReservationState(reservationState);
		Assert.assertEquals(2, this.reservationStateService.readReservationStates().size());

		this.reservationStateService.deleteReservationState("NEW");
		final List<ReservationState> readReservationStates = this.reservationStateService.readReservationStates();
		Assert.assertEquals(1, readReservationStates.size());

		Assert.assertEquals("RESERVED", readReservationStates.get(0).getName());

		final ReservationState readReservationState = this.reservationStateService.readReservationState("RESERVED");
		Assert.assertEquals(readReservationStates.get(0), readReservationState);

		this.reservationStateService.deleteReservationState("RESERVED");
		Assert.assertEquals(0, this.reservationStateService.readReservationStates().size());
	}

	/**
	 * Tests all methods of the CRUD service for {@link ReservationService}.
	 * 
	 * @throws EntityNotFoundException
	 *             Should not be thrown in this test.
	 * 
	 */
	@Test
	public void reservationCrudServiceIntegrationTest() throws EntityNotFoundException {
		Assert.assertEquals(0, this.reservationService.readReservations().size());

		final Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 05, 01, 15, 15, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		final Date startDate = calendar.getTime();

		calendar.add(Calendar.HOUR_OF_DAY, 8);
		final Date endDate = calendar.getTime();

		// create an aircraft type
		final AircraftType aircraftType = new AircraftType("A 380");
		this.aircraftTypeService.createAircraftType(aircraftType);
		Assert.assertEquals(1, this.aircraftTypeService.readAircraftTypes().size());

		// create an aircraft
		final Aircraft aircraft = new Aircraft("ABC");
		aircraft.setAircraftType(aircraftType);
		this.aircraftService.createAircraft(aircraft);
		Assert.assertEquals(1, this.aircraftService.readAircrafts().size());

		final Reservation reservation = new Reservation();
		reservation.setAircraft(aircraft);
		reservation.setStartDate(startDate);
		reservation.setEndDate(endDate);
		final ReservationState reservationState = new ReservationState("RESERVED");
		this.reservationStateService.createReservationState(reservationState);
		reservation.setReservationState(reservationState);

		// test the creation of an reservation
		this.reservationService.createReservation(reservation);
		Assert.assertEquals(1, this.reservationService.readReservations().size());

		final Reservation createdreservation = this.reservationService.readReservations().get(0);
		Assert.assertNotNull(createdreservation.getId());
		Assert.assertEquals(aircraft.getTailSign(), createdreservation.getAircraft().getTailSign());

		// test retrieve reservation by id
		final Reservation retrievedReservation = this.reservationService.readReservation(createdreservation.getId());
		Assert.assertNotNull(retrievedReservation.getId());
		Assert.assertEquals(aircraft.getTailSign(), retrievedReservation.getAircraft().getTailSign());

		// test reservation update
		retrievedReservation.setEndDate(new Date());
		final Reservation updateReservation = this.reservationService.updateReservation(retrievedReservation);
		Assert.assertEquals(aircraft.getTailSign(), updateReservation.getAircraft().getTailSign());
		Assert.assertNotEquals(endDate, updateReservation.getEndDate());

		// test the deletion of an reservation
		this.reservationService.deleteReservation(retrievedReservation.getId());
		Assert.assertEquals(0, this.reservationService.readReservations().size());
	}

}
