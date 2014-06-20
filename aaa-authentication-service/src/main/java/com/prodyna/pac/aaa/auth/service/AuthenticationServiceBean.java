package com.prodyna.pac.aaa.auth.service;

import javax.ejb.Stateless;

import com.prodyna.pac.aaa.common.annotation.Monitored;

/**
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class AuthenticationServiceBean implements AuthenticationService {

	@Override
	public void login(final String username, final byte[] password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void changePassword(final byte[] oldPassword, final byte[] newPassword) {
		// TODO Auto-generated method stub

	}

}
