package com.fishkees.backend.dataaccess.mongo;

import javax.inject.Singleton;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.Mongo;

public class MongoDbModule extends AbstractModule {

	private MongoConfiguration configuration;

	public MongoDbModule(MongoConfiguration configuration) {
		this.configuration = configuration;
	}

	@Override
	protected void configure() {
		bind(Mongo.class).toProvider(MongoProvider.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	private MongoConfiguration provideMongoConfiguration() {
		return this.configuration;
	}

}
