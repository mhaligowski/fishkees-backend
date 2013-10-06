package com.fishkees.backend.modules.lists;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.dao.InMemoryFlashcardListDao;
import com.fishkees.backend.task.ResetStorageTask;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.yammer.dropwizard.json.ObjectMapperFactory;

public final class ListsModule extends AbstractModule {
	private FixturesConfiguration config;

	private ListsModule() {
		
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
		bind(ResetStorageTask.class);

	}

	@Provides
	@Named("storageMap")
	Map<Long, FlashcardList> newMap() {
		return Maps.newHashMap();
	}

	@Provides
	@Singleton
	FlashcardListInMemoryStorage flashcardListInMemoryStorage(
			@Named("storageMap") Map<Long, FlashcardList> map) {
		FlashcardListInMemoryStorage storage = new FlashcardListInMemoryStorage(
				map);

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
