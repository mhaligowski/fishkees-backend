package com.fishkees.backend.modules.lists;

import javax.inject.Singleton;

import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.dao.InMemoryFlashcardListDao;
import com.google.inject.AbstractModule;

public class ListsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FlashcardListDao.class).to(InMemoryFlashcardListDao.class);
		bind(FlashcardListInMemoryStorage.class).in(Singleton.class);

	}

}
