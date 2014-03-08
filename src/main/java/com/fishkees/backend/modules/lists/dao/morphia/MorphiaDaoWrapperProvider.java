package com.fishkees.backend.modules.lists.dao.morphia;

import javax.inject.Inject;
import javax.inject.Provider;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

class MorphiaDaoWrapperProvider implements Provider<MorphiaDaoWrapper> {

	private final Morphia morphiaObject;
	private final Datastore datastore;

	@Inject
	public MorphiaDaoWrapperProvider(Morphia morphiaObject, Datastore datastore) {
		this.morphiaObject = morphiaObject;
		this.datastore = datastore;
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
	}

}
