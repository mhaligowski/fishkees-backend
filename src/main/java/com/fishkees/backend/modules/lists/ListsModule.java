package com.fishkees.backend.modules.lists;

import javax.inject.Singleton;

import com.fishkees.backend.modules.lists.core.InMemoryFlashcardListDao;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.inject.AbstractModule;

public class ListsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FlashcardListDao.class).to(InMemoryFlashcardListDao.class).in(
				Singleton.class);
		;

	}

}
