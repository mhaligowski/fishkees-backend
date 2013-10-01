package com.fishkees.backend.healthcheck;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class HealthChecksModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("url")).toInstance(
				"http://www.google.com");
		bind(URLWrapper.class);
		bind(PingHealthCheck.class);
	}

}
