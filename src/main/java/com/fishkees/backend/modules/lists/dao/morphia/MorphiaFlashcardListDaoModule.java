package com.fishkees.backend.modules.lists.dao.morphia;

import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.inject.PrivateModule;

public class MorphiaFlashcardListDaoModule extends PrivateModule {

	@Override
	protected void configure() {
		bind(FlashcardListDao.class).to(MorphiaFlashcardListDao.class);
	}

}
