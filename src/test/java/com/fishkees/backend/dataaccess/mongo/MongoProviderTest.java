package com.fishkees.backend.dataaccess.mongo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;


@RunWith(MockitoJUnitRunner.class)
public class MongoProviderTest {
	
	@InjectMocks
	private MongoProvider testObj;
	
	@Mock
	private MongoConfiguration configuration;
	
	@Test
	public void should_return_mongo_client_object() {
		// given
		when(configuration.getPassword()).thenReturn("somePassword");
		when(configuration.getUsername()).thenReturn("someUsername");
		
		// when
		Mongo mongo = testObj.get();
		
		// then
		assertTrue(mongo instanceof MongoClient);
		
		verify(configuration).getHost();
		verify(configuration).getPort();
		verify(configuration).getUsername();
		verify(configuration).getPassword();
		verify(configuration).getDb();
	}

}
