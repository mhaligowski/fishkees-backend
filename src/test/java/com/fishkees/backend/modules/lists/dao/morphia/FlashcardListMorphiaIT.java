package com.fishkees.backend.modules.lists.dao.morphia;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.configuration.ConfigurationParser;
import com.fishkees.backend.configuration.MongoConfiguration;
import com.fishkees.backend.dataaccess.mongo.MongoDbModule;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FlashcardListMorphiaIT {

	private FlashcardListDao flashcardListDao;
	private Morphia morphiaObject;

	private ConfigurationParser<MongoConfiguration> confParser = new ConfigurationParser<>(
			MongoConfiguration.class);

	@Before
	public void setUp() {
		final String filename = "mongoConfiguration.yml";
		final URL resource = this.getClass().getResource(filename);
		MongoConfiguration mongoConf = confParser.parseConfiguration(resource);

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

}
