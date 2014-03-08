package com.fishkees.backend.modules.lists.dao.morphia;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

class MorphiaDaoWrapper extends BasicDAO<MorphiaFlashcardList, ObjectId> {

	@Inject
	public MorphiaDaoWrapper(Datastore ds) {
		super(ds);
	}

}
