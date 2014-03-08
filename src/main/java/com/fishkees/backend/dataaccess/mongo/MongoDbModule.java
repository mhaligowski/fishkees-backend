package com.fishkees.backend.dataaccess.mongo;

import javax.inject.Singleton;

import org.mongodb.morphia.Morphia;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.mongodb.Mongo;

public class MongoDbModule extends AbstractModule {
	public static final String MONGO_DB_NAME = "MongoDbName";

	private MongoConfiguration configuration;

	public MongoDbModule(MongoConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure() {
		bind(Mongo.class).toProvider(MongoProvider.class).in(Singleton.class);
		bind(Morphia.class).toProvider(MorphiaObjectProvider.class).in(
				Singleton.class);
		bind(String.class).annotatedWith(Names.named(MONGO_DB_NAME))
				.toInstance(configuration.getDb());
	}

	@Provides
	@Singleton
	private MongoConfiguration provideMongoConfiguration() {
		return this.configuration;
	}

}
