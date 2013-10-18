package com.fishkees.backend.modules.flashcards;

import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.fishkees.backend.modules.flashcards.dao.InMemoryFlashcardDao;
import com.google.inject.AbstractModule;

public class FlashcardsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FlashcardDao.class).to(InMemoryFlashcardDao.class);
	}

}
