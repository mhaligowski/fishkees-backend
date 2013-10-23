package com.fishkees.backend.task;

import com.google.inject.AbstractModule;

public class TasksModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ResetStorageTask.class);
	}

}
