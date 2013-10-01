package com.fishkees.backend.modules.lists;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.dao.InMemoryFlashcardListDao;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.yammer.dropwizard.json.ObjectMapperFactory;

public class ListsModule extends AbstractModule {
	private FixturesConfiguration config;

	public ListsModule() {

	}

	public ListsModule(FixturesConfiguration config) {
		this.config = config;
	}

	@Override
	protected void configure() {
		bind(FlashcardListDao.class).to(InMemoryFlashcardListDao.class);

	}

	@Provides
	@Singleton
	FlashcardListInMemoryStorage flashcardListInMemoryStorage() {
		FlashcardListInMemoryStorage storage = new FlashcardListInMemoryStorage();

		if (config != null) {
			List<FlashcardList> fixture = loadFixture(config
					.getFlashcardListsPath());
			for (FlashcardList flashcardList : fixture) {
				storage.put(flashcardList.getId(), flashcardList);
			}
		}

		return storage;
	}

	private List<FlashcardList> loadFixture(String path) {
		ObjectMapper om = new ObjectMapperFactory().build();
		TypeReference<List<FlashcardList>> tr = new TypeReference<List<FlashcardList>>() {
		};
		try {
			return om.readValue(new File(path), tr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
