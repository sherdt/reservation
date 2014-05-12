package com.prodyna.pac.aaa.pilot;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.aaa.reservation.Reservation;

/**
 * Entity representing an pilot with its name, user name and email address. The user name has to be unique.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@Table(name = "aaa_pilot")
@NamedQueries({ @NamedQuery(name = PilotNamedQueries.SELECT_ALL_PILOTS, query = "SELECT p FROM Pilot p") })
public class Pilot implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 6634014111036721573L;

	/** Unique user name, also used as id. */
	@Id
	private String username;

	/** The full name of the pilot (e.g 'Sergej Herdt'). */
	private String name;

	/** Email address of the pilot. */
	private String email;

	/** Encrypted password of the pilot. */
	private String password;

	/** All licenses belonging to this pilot. */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "aaa_pilot_username", referencedColumnName = "username")
	private Set<License> licenses;

	/** All reservations belonging to this pilot. */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "aaa_pilot_username", referencedColumnName = "username")
	private Set<Reservation> reservations;

	/**
	 * Default constructor.
	 */
	public Pilot() {
	}

	/**
	 * Convenient constructor setting the user name of the new pilot.
	 * 
	 * @param username
	 *            User name to use for new pilot.
	 */
	public Pilot(final String username) {
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the licenses
	 */
	public Set<License> getLicenses() {
		return this.licenses;
	}

	/**
	 * @param licenses
	 *            the licenses to set
	 */
	public void setLicenses(final Set<License> licenses) {
		this.licenses = licenses;
	}

	/**
	 * @return the reservations
	 */
	public Set<Reservation> getReservations() {
		return this.reservations;
	}

	/**
	 * @param reservations
	 *            the reservations to set
	 */
	public void setReservations(final Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
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
		if (!(obj instanceof Pilot)) {
			return false;
		}
		final Pilot other = (Pilot) obj;
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Pilot [username=").append(this.username).append(", name=").append(this.name).append(", email=")
				.append(this.email).append("]");
		return builder.toString();
	}

}
