package com.prodyna.pac.aaa.common.producer;

import java.lang.management.ManagementFactory;

import javax.enterprise.inject.Produces;
import javax.management.MBeanServer;

/**
 * Produces the {@link MBeanServer} using the {@link ManagementFactory}. With help of this producer one can use CDI @Inject
 * to inject an instance of the mbean server.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class MBeanServerProducer {

	/**
	 * Produces the {@link MBeanServer} using the {@link ManagementFactory}.
	 * 
	 * @return The platform MBean server created by the {@link ManagementFactory}.
	 */
	@Produces
	public MBeanServer produceMBeanServer() {
		return ManagementFactory.getPlatformMBeanServer();
	}
}