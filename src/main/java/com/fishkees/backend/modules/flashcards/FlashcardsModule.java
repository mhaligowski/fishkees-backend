package com.fishkees.backend.modules.flashcards;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.fishkees.backend.modules.flashcards.dao.FlashcardInMemoryStorage;
import com.fishkees.backend.modules.flashcards.dao.InMemoryFlashcardDao;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.yammer.dropwizard.json.ObjectMapperFactory;

public final class FlashcardsModule extends AbstractModule {

	private final FixturesConfiguration config;

	private FlashcardsModule() {
		config = null;
	}
	
	private FlashcardsModule(FixturesConfiguration config) {
		this.config = config;
	}

	@Override
	protected void configure() {
		bind(FlashcardDao.class).to(InMemoryFlashcardDao.class);
	}

	@Provides
	@Singleton
	FlashcardInMemoryStorage storage() {
		List<Flashcard> fixtures = null;
		
		if (this.config == null) {
			fixtures = Lists.newArrayList();
		} else {
			fixtures = loadFixture(config.getFlashcardsPath());
		}
			
		return new FlashcardInMemoryStorage(fixtures);
	}

	public static FlashcardsModule simpleModule() {
		return new FlashcardsModule();
	}

	public static FlashcardsModule moduleWithFixture(
			FixturesConfiguration config) {
		return new FlashcardsModule(config);
	}

	private List<Flashcard> loadFixture(String path) {
		ObjectMapper om = new ObjectMapperFactory().build();
		TypeReference<List<Flashcard>> tr = new TypeReference<List<Flashcard>>() {
		};
		try {
			return om.readValue(new File(path), tr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
