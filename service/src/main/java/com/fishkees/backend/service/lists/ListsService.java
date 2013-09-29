package com.fishkees.backend.service.lists;

import com.fishkees.backend.service.lists.resources.ListsResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class ListsService extends Service<ListsConfiguration> {
	public static void main(String[] args) throws Exception {
		new ListsService().run(args);
	}

	@Override
	public void initialize(Bootstrap<ListsConfiguration> bootstrap) {
		bootstrap.setName("flashcardlists");
	}

	@Override
	public void run(ListsConfiguration configuration, Environment environment)
			throws Exception {
		environment.addResource(new ListsResource());
	}

}
