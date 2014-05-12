package com.prodyna.pac.aaa.aircraft;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing the type of an aircraft. The name is used as unique an identifier (e.g. <code>A380</code>,
 * <code>737</code>, ...)
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@Table(name = "aaa_aircraft_type")
@NamedQueries({ @NamedQuery(name = AircraftNamedQueries.SELECT_ALL_AIRCRAFT_TYPES, query = "SELECT at FROM AircraftType at") })
public class AircraftType implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -5166120093592174406L;

	/** Name of the aircraft type. */
	@Id
	private String name;

	/**
	 * Default constructor.
	 */
	public AircraftType() {
	}

	/**
	 * Constructor setting the name.
	 * 
	 * @param name
	 *            Aircraft type e.g. <code>747</code>
	 */
	public AircraftType(final String name) {
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
		if (!(obj instanceof AircraftType)) {
			return false;
		}
		final AircraftType other = (AircraftType) obj;
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
		return this.name;
	}

}
