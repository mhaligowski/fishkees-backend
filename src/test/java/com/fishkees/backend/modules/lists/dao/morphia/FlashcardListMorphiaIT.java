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
import com.mongodb.Mongo;

public class FlashcardListMorphiaIT {

	private Injector injector;

	private ConfigurationParser<MongoConfiguration> confParser = new ConfigurationParser<>(
			MongoConfiguration.class);

	@Before
	public void setUp() {
		final String filename = "mongoConfiguration.yml";
		final URL resource = this.getClass().getResource(filename);
		MongoConfiguration mongoConf = confParser.parseConfiguration(resource);

		injector = Guice.createInjector(new MongoDbModule(mongoConf),
				new MorphiaFlashcardListDaoModule());
	}

	@Test
	public void dao_should_be_instance_of_morphia() {
		// when
		FlashcardListDao flashcardListDao = injector.getInstance(FlashcardListDao.class);
		
		// then
		assertTrue(flashcardListDao instanceof MorphiaFlashcardListDao);
	}

	@Test
	public void morphia_entity_should_be_registered() {
		// given 
		@SuppressWarnings("unused")
		FlashcardListDao flashcardListDao = injector.getInstance(FlashcardListDao.class);

		// when
		Morphia morphiaObject = injector.getInstance(Morphia.class);
		
		//then 
		assertTrue(morphiaObject.isMapped(MorphiaFlashcardList.class));
	}

	@Test
	public void morphia_object_should_be_singleton() {
		// when
		Morphia morphia1 = injector.getInstance(Morphia.class);
		Morphia morphia2 = injector.getInstance(Morphia.class);
		
		// then
		assertSame(morphia1, morphia2);
	}
	
	@Test
	public void mongo_object_should_be_singleton() {
		// when
		Mongo mongo1 = injector.getInstance(Mongo.class);
		Mongo mongo2 = injector.getInstance(Mongo.class);
		
		// then
		assertSame(mongo1, mongo2);
	}
	
	
}
