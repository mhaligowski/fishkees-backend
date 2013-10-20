package com.fishkees.backend.modules.flashcards;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.fishkees.backend.modules.flashcards.dao.FlashcardInMemoryStorage;
import com.fishkees.backend.modules.flashcards.dao.InMemoryFlashcardDao;
import com.fishkees.backend.modules.lists.ListsModule;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class FlashcardsModule extends AbstractModule {

	private FlashcardsModule() {
		
	}
	
	@Override
	protected void configure() {
		bind(FlashcardDao.class).to(InMemoryFlashcardDao.class);
	}
	
	public static FlashcardsModule simpleModule() {
		return new FlashcardsModule();
	}

	@Provides
	@Singleton
	FlashcardInMemoryStorage storage() {
		return new FlashcardInMemoryStorage(Lists.<Flashcard>newArrayList());
	}
	
}
