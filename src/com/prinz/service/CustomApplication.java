package com.prinz.service;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class CustomApplication extends ResourceConfig {
	public CustomApplication() {
		packages("com.prinz.service");
		register(LoggingFilter.class);

		//Register Auth Filter here
		register(AuthenticationFilter.class);
	}
}