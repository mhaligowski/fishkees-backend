package com.fishkees.backend.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.configuration.FishkeesConfiguration;
import com.fishkees.backend.healthcheck.PingHealthCheck;
import com.fishkees.backend.modules.flashcards.resources.FlashcardResource;
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
	private FilterBuilder filterBuilder1;

	@After
	public void tearDown() {
		verifyNoMoreInteractions(configuration, environment, bootstrap,
				filterBuilder1);
	}

	@Test
	public void should_connect_all_the_resources_and_tasks() throws Exception {
		// given
		when(environment.addFilter(eq(CrossOriginFilter.class), anyString()))
				.thenReturn(filterBuilder1);

		// when
		testObj.run(configuration, environment);

		// then
		verify(environment).addFilter(eq(CrossOriginFilter.class), anyString());
		verify(filterBuilder1).setInitParam(
				eq(CrossOriginFilter.ALLOWED_METHODS_PARAM),
				eq("POST,GET,PUT,DELETE"));
		
		ArgumentCaptor<Object> ac = ArgumentCaptor.forClass(Object.class);
		verify(environment, times(2)).addResource(ac.capture());
		assertTrue(ac.getAllValues().get(0) instanceof FlashcardListResource);
		assertTrue(ac.getAllValues().get(1) instanceof FlashcardResource);
		
		// verify
		verify(environment).addTask(any(ResetStorageTask.class));

		verify(configuration).getFixturesConfiguration();
		verify(environment).addHealthCheck(any(PingHealthCheck.class));
	}

	@Test
	public void should_give_proper_names() throws Exception {
		// given
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

		// when
		testObj.initialize(bootstrap);

		// then
		verify(bootstrap).setName(argument.capture());
		assertEquals("fishkees", argument.getValue());
	}
	
}
