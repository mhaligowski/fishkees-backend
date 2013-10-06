package com.fishkees.backend.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.configuration.FishkeesConfiguration;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;
import com.fishkees.backend.task.ResetStorageTask;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.FilterBuilder;

@RunWith(MockitoJUnitRunner.class)
public class FishkeesServiceTest {
	private final FishkeesService testObj = new FishkeesService();

	@Mock
	private FishkeesConfiguration configuration;

	@Mock
	private Environment environment;

	@Mock
	private Bootstrap<FishkeesConfiguration> bootstrap;

	@Mock
	private FilterBuilder filterBuilder;

	@Test
	public void testRun() throws Exception {
		// given
		when(environment.addFilter(eq(CrossOriginFilter.class), anyString()))
				.thenReturn(filterBuilder);

		// when
		testObj.run(configuration, environment);

		// then
		verify(environment).addFilter(eq(CrossOriginFilter.class), anyString());
		verify(filterBuilder)
				.setInitParam(eq("exposedHeaders"), eq("Location"));
		verify(environment).addResource(any(FlashcardListResource.class));
		verify(environment).addTask(any(ResetStorageTask.class));
	}

	@Test
	public void testInitialization() throws Exception {
		// given
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

		// when
		testObj.initialize(bootstrap);

		// then
		verify(bootstrap).setName(argument.capture());
		assertEquals("fishkees", argument.getValue());
	}

}
