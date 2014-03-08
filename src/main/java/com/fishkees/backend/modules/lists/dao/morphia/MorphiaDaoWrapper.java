package com.fishkees.backend.modules.lists.dao.morphia;

import static com.fishkees.backend.dataaccess.mongo.MongoDbModule.*;

import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.Mongo;

class MorphiaDaoWrapper extends BasicDAO<MorphiaFlashcardList, ObjectId> {

	@Inject
	public MorphiaDaoWrapper(Mongo mongo, Morphia morphia,
			@Named(MONGO_DB_NAME) String dbName) {
		super(mongo, morphia, dbName);
	}

}
