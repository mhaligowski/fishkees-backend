package com.fishkees.backend.healthcheck;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.yammer.metrics.core.HealthCheck.Result;


@RunWith(MockitoJUnitRunner.class)
public class PingHealthCheckTest {
	
	@Test
	public void should_return_unhealthy_when_cannot_connect_to_the_external_server() throws IOException {
		// given
		URLWrapper urlWrapper = mock(URLWrapper.class);
		String urlString = "###";
		PingHealthCheck testObj = new PingHealthCheck(urlString, urlWrapper);
		when(urlWrapper.getReader(urlString)).thenThrow(new MalformedURLException());
		
		// when
		Result result = testObj.check();
		
		// then
		assertFalse(result.isHealthy());
		verify(urlWrapper).getReader(urlString);
	}
	
	@Test
	public void should_return_healthy_when_can_connect_to_external_server() throws Exception {
		// given
		String urlString = "http://www.google.com";
		
		BufferedReader bufferedReader = mock(BufferedReader.class);
		when(bufferedReader.readLine()).thenReturn(null);
		
		URLWrapper urlWrapper = mock(URLWrapper.class);
		when(urlWrapper.getReader(urlString)).thenReturn(bufferedReader);
		
		PingHealthCheck testObj = new PingHealthCheck(urlString, urlWrapper);
		
		// when
		Result result = testObj.check();
		
		// then
		assertTrue(result.isHealthy());
	}

}
