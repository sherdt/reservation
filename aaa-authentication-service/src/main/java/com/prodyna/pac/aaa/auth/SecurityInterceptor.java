package com.prodyna.pac.aaa.auth;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.auth.exception.UnknownRoleException;

/**
 * Interceptor that checks the authentication injected by container request.
 */
@Interceptor
@AuthenticationSecured
@Priority(Interceptor.Priority.APPLICATION)
public class SecurityInterceptor {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Credentials object set by container. */
	@Inject
	private Credentials credentials;

	/**
	 * Checks if the intercepted method is allowed to be executed.
	 * 
	 * @param ctx
	 *            The invocation context.
	 * @return The result of the intercepted method.
	 * @throws Exception
	 *             If the operation is not allowed, or if intercepted method throws an exception.
	 */
	@AroundInvoke
	public Object checkCredentials(final InvocationContext ctx) throws Exception {
		this.logger.trace("Performing role check.");

		final AuthenticationSecured annotation = ctx.getMethod().getAnnotation(AuthenticationSecured.class);

		final Role role;
		if (annotation == null) {
			// if annotation cannot be retrieved from the method, it's set on the type (class). In this case we use the
			// default role "Pilot"
			role = Role.PILOT;
		} else {
			role = annotation.role();
		}

		// TODO implement role checking nicely some day, with roles from DB etc.

		switch (role) {
		case ADMIN: {
			this.logger.debug("The method [{}.{}] requires the admin role, check if admin is logged in.", ctx
					.getTarget().getClass().getName(), ctx.getMethod().getName());

			// for the ADMIN role, the user name has to be "admin"
			if ("admin".equals(this.credentials.getUsername())) {
				return ctx.proceed();
			} else {
				this.logger
						.debug("Operation denied. The user [{}] tried to execute a method which is only allowed to be executed as admin.",
								this.credentials.getUsername());

				throw new NotAuthorizedException(
						"You are not allowed to perform this operation. Only the admin is allowed to do that.");
			}
		}
		case PILOT: {
			this.logger.debug("The method [{}.{}] requires the pilot role, check if user is logged in.", ctx
					.getTarget().getClass().getName(), ctx.getMethod().getName());

			if (this.credentials.getUsername() == null) {
				this.logger
						.debug("Operation denied. Someone tried to execute a method which is only allowed to be executed as pilot.",
								this.credentials.getUsername());
			} else {
				return ctx.proceed();
			}
			break;
		}
		case NONE: {
			// no role required, just execute the method
			return ctx.proceed();
		}
		default:
			throw new UnknownRoleException("The developer is to blame, the role '" + role + "' is unknown.");
		}

		throw new NotAuthorizedException("You are not allowed to perform this operation, please login first.");
	}
}
