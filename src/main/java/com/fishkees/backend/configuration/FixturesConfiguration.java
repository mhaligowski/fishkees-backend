package com.fishkees.backend.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class FixturesConfiguration extends Configuration {
	@JsonProperty
	private String flashcardListsPath;

	public String getFlashcardListsPath() {
		return flashcardListsPath;
	}
}
