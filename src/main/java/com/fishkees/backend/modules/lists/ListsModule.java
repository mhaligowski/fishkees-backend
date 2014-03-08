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
import com.fishkees.backend.modules.lists.dao.inmemory.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.dao.inmemory.InMemoryFlashcardListDao;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.yammer.dropwizard.json.ObjectMapperFactory;

public final class ListsModule extends AbstractModule {
	private final FixturesConfiguration config;

	private ListsModule() {
		config = null;
	}

	private ListsModule(FixturesConfiguration config) {
		this.config = config;
	}

	public static ListsModule simpleModule() {
		return new ListsModule();
	}

	public static ListsModule moduleWithFixture(FixturesConfiguration config) {
		return new ListsModule(config);
	}
	
	@Override
	protected void configure() {
		bind(FlashcardListDao.class).to(InMemoryFlashcardListDao.class);
	}

	@Provides
	@Singleton
	FlashcardListInMemoryStorage flashcardListInMemoryStorage() {
		List<FlashcardList> fixture = Lists.newArrayList();
		if (config != null) {
			fixture = loadFixture(config.getFlashcardListsPath());
		}

		return new FlashcardListInMemoryStorage(fixture);
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
