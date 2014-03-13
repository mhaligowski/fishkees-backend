package com.fishkees.backend.dataaccess.morphia;

import javax.inject.Singleton;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.mongodb.Mongo;

public class MorphiaModule extends AbstractModule {
	public static final String MONGO_DB_NAME = "MongoDbName";

	private MongoConfiguration configuration;

	public MorphiaModule(MongoConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure() {
		bind(Mongo.class).toProvider(MongoObjectProvider.class).in(Singleton.class);
		bind(String.class).annotatedWith(Names.named(MONGO_DB_NAME))
				.toInstance(configuration.getDb());
		bind(Morphia.class).in(Singleton.class);
		bind(Datastore.class).toProvider(DatastoreObjectProvider.class).in(
				Singleton.class);
	}

	@Provides
	@Singleton
	private MongoConfiguration provideMongoConfiguration() {
		return this.configuration;
	}

}