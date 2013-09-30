package com.fishkees.backend.modules.lists;

import com.fishkees.backend.modules.lists.core.FlashcardListDao;
import com.google.inject.AbstractModule;

public class ListsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FlashcardListDao.class);

	}

}
