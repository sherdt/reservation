package com.prodyna.pac.aaa.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

/**
 * All services or methods annotated with this annotation will be checked if user is logged in and has the permission to
 * access the resources or execute the methods.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Inherited
@InterceptorBinding
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticationSecured {

	/**
	 * The method / class using this annotation can specify the name of the role the credentials should match to, to be
	 * allowed to be executed.
	 */
	@Nonbinding
	Role role() default Role.PILOT;

}
