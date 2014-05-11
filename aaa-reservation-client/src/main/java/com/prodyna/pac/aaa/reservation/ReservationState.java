package com.prodyna.pac.aaa.reservation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing the state of an reservation. The name of the reservation state has to be unique. Possible values
 * could be <code>RESERVED</code>, <code>CANCELED</code>, ... but it's totally up to the administrator to create
 * reservation states and define its names.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@Table(name = "aaa_reservation_state")
@NamedQueries({
		@NamedQuery(name = ReservationNamedQueries.SELECT_ALL_RESERVATION_STATES, query = "SELECT rs FROM ReservationState rs"),
		@NamedQuery(name = ReservationNamedQueries.SELECT_RESERVATION_STATE_BY_NAME, query = "SELECT rs FROM ReservationState rs WHERE rs.name = :reservationStateName") })
public class ReservationState implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -2429551523341366938L;

	/** Name of the reservation state. */
	@Id
	private String name;

	/**
	 * Default constructor.
	 */
	public ReservationState() {
	}

	/**
	 * Constructor setting the name.
	 * 
	 * @param name
	 *            Reservation state e.g. <code>CANCELED</code>
	 */
	public ReservationState(final String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ReservationState)) {
			return false;
		}
		final ReservationState other = (ReservationState) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ReservationState [name=").append(this.name).append("]");
		return builder.toString();
	}

}
