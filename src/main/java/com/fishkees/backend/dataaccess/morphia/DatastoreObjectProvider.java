package com.fishkees.backend.dataaccess.morphia;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;

import static com.fishkees.backend.dataaccess.morphia.MorphiaModule.*;

class DatastoreObjectProvider implements Provider<Datastore> {
	private final String dbName;
	private final Mongo mongo;
	private Morphia morphia;

	@Inject
	public DatastoreObjectProvider(Mongo mongo, Morphia morphia,
			@Named(MONGO_DB_NAME) String dbName) {
		this.mongo = mongo;
		this.morphia = morphia;
		this.dbName = dbName;
	}

	@Override
	public Datastore get() {
		return morphia.createDatastore(this.mongo, this.dbName);
	}

}
