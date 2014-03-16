package com.fishkees.backend.modules.lists.dao.morphia;

import javax.inject.Inject;
import javax.inject.Provider;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.dataaccess.morphia.Mapper;
import com.fishkees.backend.modules.lists.core.FlashcardList;

class MorphiaDaoWrapperProvider implements Provider<MorphiaDaoWrapper> {

	private final Morphia morphiaObject;
	private final Datastore datastore;
	private final Mapper mapper;

	@Inject
	public MorphiaDaoWrapperProvider(Morphia morphiaObject,
			Datastore datastore, Mapper mapper) {
		this.morphiaObject = morphiaObject;
		this.datastore = datastore;
		this.mapper = mapper;
	}

	@Override
	public MorphiaDaoWrapper get() {
		mapFlashcardListIfNotMapped();

		return new MorphiaDaoWrapper(datastore);
	}

	private void mapFlashcardListIfNotMapped() {
		if (!morphiaObject.isMapped(MorphiaFlashcardList.class)) {
			morphiaObject.map(MorphiaFlashcardList.class);
		}
		mapper.register(MorphiaFlashcardList.class, FlashcardList.class);
	}

}
