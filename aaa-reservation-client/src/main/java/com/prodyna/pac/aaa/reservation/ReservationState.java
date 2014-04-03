package com.prodyna.pac.aaa.reservation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity representing the state of an reservation. The name of the reservation state has to be unique. Possible values
 * could be <code>RESERVED</code>, <code>CANCELED</code>, ... but it's totally up to the administrator to create
 * reservation states and define its names.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@Table(name = "reservation_state")
public class ReservationState implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -1918482213672451084L;

	/** Name of the reservation state. */
	@Id
	private String name;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
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
