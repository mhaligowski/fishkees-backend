package com.fishkees.backend.dataaccess.morphia;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.configuration.ConfigurationParser;
import com.fishkees.backend.configuration.MongoConfiguration;
import com.fishkees.backend.modules.lists.dao.morphia.MorphiaFlashcardListDaoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MorphiaMapperIT {

	private Injector guiceInjector;

	@Before
	public void setUp() {
		URL configurationFile = MorphiaFlashcardListDaoModule.class
				.getResource("mongoConfiguration.yml");
		MongoConfiguration mongoConfiguration = new ConfigurationParser<>(
				MongoConfiguration.class).parseConfiguration(configurationFile);
		this.guiceInjector = Guice.createInjector(new MorphiaModule(
				mongoConfiguration));
	}

	@Test
	public void should_initalize_correctly() {
		Mapper instance = guiceInjector.getInstance(Mapper.class);
		assertNotNull(instance);
	}
}
