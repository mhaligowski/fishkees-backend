package com.fishkees.backend.modules.lists.dao.morphia;

import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.inject.AbstractModule;

public class MorphiaFlashcardListDaoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MorphiaDaoWrapper.class).toProvider(
				MorphiaDaoWrapperProvider.class);
		bind(FlashcardListDao.class).to(MorphiaFlashcardListDao.class);
	}

}
