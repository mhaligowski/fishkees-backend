package com.fishkees.backend.configuration;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class FishkeesConfiguration extends Configuration {
	@Valid
	@JsonProperty
	private final FixturesConfiguration fixtures = new FixturesConfiguration();

	public FixturesConfiguration getFixturesConfiguration() {
		return fixtures;
	}
}
