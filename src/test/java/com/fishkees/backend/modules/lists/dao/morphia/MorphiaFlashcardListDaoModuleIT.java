package com.fishkees.backend.modules.lists.dao.morphia;

import static com.fishkees.backend.modules.lists.core.FlashcardListTestBuilder.*;
import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.configuration.ConfigurationParser;
import com.fishkees.backend.configuration.MongoConfiguration;
import com.fishkees.backend.dataaccess.morphia.MorphiaModule;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.base.Optional;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MorphiaFlashcardListDaoModuleIT {

	private Injector injector;

	private ConfigurationParser<MongoConfiguration> confParser = new ConfigurationParser<>(
			MongoConfiguration.class);

	@Before
	public void setUp() {
		final String filename = "mongoConfiguration.yml";
		final URL resource = this.getClass().getResource(filename);
		MongoConfiguration mongoConf = confParser.parseConfiguration(resource);

		injector = Guice.createInjector(new MorphiaModule(mongoConf),
				new MorphiaFlashcardListDaoModule());
	}

	@Test
	public void morphia_entity_should_be_registered() {
		// given
		@SuppressWarnings("unused")
		FlashcardListDao flashcardListDao = injector
				.getInstance(FlashcardListDao.class);

		// when
		Morphia morphiaObject = injector.getInstance(Morphia.class);

		// then
		assertTrue(morphiaObject.isMapped(MorphiaFlashcardList.class));
	}

	@Test
	public void should_store_new_entity_with_id_and_createdate() {
		// given
		FlashcardListDao flashcardListDao = injector
				.getInstance(FlashcardListDao.class);
		final FlashcardList fl = newListWithId(null).withTitle("abcd").build();

		// when
		Optional<FlashcardList> object = flashcardListDao
				.createNewFromObject(fl);

		// then
		assertTrue(object.isPresent());
		assertNotNull(object.get().getId());
		assertNotNull(object.get().getCreateDate());
		assertNotSame(object.get(), fl);
		
	}
}
