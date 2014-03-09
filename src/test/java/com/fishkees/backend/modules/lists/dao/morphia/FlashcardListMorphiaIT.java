package com.fishkees.backend.modules.lists.dao.morphia;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.fishkees.backend.dataaccess.mongo.MongoDbModule;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yammer.dropwizard.config.ConfigurationException;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.validation.Validator;

public class FlashcardListMorphiaIT {

	private FlashcardListDao flashcardListDao;
	private Morphia morphiaObject;

	@Before
	public void setUp() {
		MongoConfiguration mongoConf = parseConfiguration("mongoConfiguration.yml");

		Injector injector = Guice.createInjector(new MongoDbModule(mongoConf),
				new MorphiaFlashcardListDaoModule());
		flashcardListDao = injector.getInstance(FlashcardListDao.class);
		morphiaObject = injector.getInstance(Morphia.class);
	}

	@Test
	public void dao_should_be_instance_of_morphia() {
		assertTrue(flashcardListDao instanceof MorphiaFlashcardListDao);
	}

	@Test
	public void morphia_entity_should_be_registered() {
		assertTrue(morphiaObject.isMapped(MorphiaFlashcardList.class));
	}

	private MongoConfiguration parseConfiguration(String filename) {
		final ConfigurationFactory<MongoConfiguration> configurationFactory = ConfigurationFactory
				.forClass(MongoConfiguration.class, new Validator());

		URL resource = this.getClass().getResource(filename);
		try {
			final URI uri = resource.toURI();
			final File file = new File(uri);
			return configurationFactory.build(file);
		} catch (IOException | ConfigurationException | URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

}
