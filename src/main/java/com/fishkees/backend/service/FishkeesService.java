package com.fishkees.backend.service;

import java.util.List;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.fishkees.backend.configuration.FishkeesConfiguration;
import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.healthcheck.HealthChecksModule;
import com.fishkees.backend.healthcheck.PingHealthCheck;
import com.fishkees.backend.modules.flashcards.FlashcardsModule;
import com.fishkees.backend.modules.flashcards.resources.FlashcardResource;
import com.fishkees.backend.modules.lists.ListsModule;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;
import com.fishkees.backend.task.ResetStorageTask;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class FishkeesService extends Service<FishkeesConfiguration> {
	private static final String APPLICATION_NAME = "fishkees";

	public static void main(String[] args) throws Exception {
		new FishkeesService().run(args);
	}

	@Override
	public void initialize(Bootstrap<FishkeesConfiguration> bootstrap) {
		bootstrap.setName(APPLICATION_NAME);
	}

	@Override
	public void run(FishkeesConfiguration configuration, Environment environment)
			throws Exception {
		environment.addFilter(CrossOriginFilter.class, "/*").setInitParam(
				CrossOriginFilter.ALLOWED_METHODS_PARAM, "POST,GET,PUT,DELETE");

		Injector injector = setInjector(configuration);
		FlashcardListResource flashcardListResource = injector
				.getInstance(FlashcardListResource.class);
		FlashcardResource flashcardResource = injector
				.getInstance(FlashcardResource.class);
		
		environment.addResource(flashcardListResource);
		environment.addResource(flashcardResource);

		environment.addHealthCheck(injector.getInstance(PingHealthCheck.class));
		environment.addTask(injector.getInstance(ResetStorageTask.class));
	}

	private Injector setInjector(FishkeesConfiguration config) {
		FixturesConfiguration fixConf = config.getFixturesConfiguration();

		List<AbstractModule> modules = Lists.newLinkedList();
		modules.add(ListsModule.moduleWithFixture(fixConf));
		modules.add(FlashcardsModule.moduleWithFixture(fixConf));
		modules.add(new HealthChecksModule());
		return Guice.createInjector(modules);
	}
}
