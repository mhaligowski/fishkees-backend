package com.fishkees.backend.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.ConfigurationException;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.validation.Validator;

public class ConfigurationParser<C extends Configuration> {
	private final Class<C> klazz;

	public ConfigurationParser(Class<C> type) {
		this.klazz = type;
	}

	public C parseConfiguration(String filename) {
		URL resource = this.getClass().getResource(filename);

		return parseConfiguration(resource);
	}

	public C parseConfiguration(URL resource) {
		final ConfigurationFactory<C> configurationFactory = ConfigurationFactory
				.forClass(klazz, new Validator());

		try {
			return configurationFactory.build(new File(resource.toURI()));
		} catch (IOException | ConfigurationException | URISyntaxException e) {
			throw new RuntimeException(e);
		}		
	}
}
