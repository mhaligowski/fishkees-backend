package com.fishkees.backend.service;

import com.fishkees.backend.configuration.FishkeesConfiguration;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class FishkeesService extends Service<FishkeesConfiguration>{
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
		environment.addResource(new FlashcardListResource());
	}

}
