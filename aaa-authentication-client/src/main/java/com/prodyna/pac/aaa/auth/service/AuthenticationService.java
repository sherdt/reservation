package com.prodyna.pac.aaa.auth.service;

/**
 * Authentication service definition.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface AuthenticationService {

	void login(String username, byte[] password);

	void logout();

	void changePassword(byte[] oldPassword, byte[] newPassword);

}
