package com.fishkees.backend.dataaccess.morphia;

import javax.inject.Singleton;

import org.dozer.loader.api.BeanMappingBuilder;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
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
		bindMorphiaObjects();
		bindConfiguration();
		bindMapperObjects();
	}

	private void bindMorphiaObjects() {
		bind(Morphia.class).in(Singleton.class);
		bind(Mongo.class).toProvider(MongoObjectProvider.class).in(
				Singleton.class);
		bind(Datastore.class).toProvider(DatastoreObjectProvider.class).in(
				Singleton.class);
	}

	private void bindConfiguration() {
		bind(String.class).annotatedWith(Names.named(MONGO_DB_NAME))
				.toInstance(configuration.getDb());
	}

	private void bindMapperObjects() {
		bind(Mapper.class).to(DozerMapper.class).in(Singleton.class);
		install(new FactoryModuleBuilder().implement(BeanMappingBuilder.class,
				MorphiaMappingBuilder.class).build(MapperBuilderFactory.class));
	}

	@Provides
	@Singleton
	private MongoConfiguration provideMongoConfiguration() {
		return this.configuration;
	}

}
