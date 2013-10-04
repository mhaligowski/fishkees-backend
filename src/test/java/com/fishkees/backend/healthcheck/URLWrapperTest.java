package com.fishkees.backend.healthcheck;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.net.URL;

import org.junit.Test;

public class URLWrapperTest {

	@Test
	public void testOpenReader() throws Exception {
		// given
		URLWrapper testObj = new URLWrapper();
		
		URL url = this.getClass().getResource("test.txt");
		
		// when
		BufferedReader reader = testObj.getReader(url.toString());
		
		// then
		assertNotNull(reader);
		String readLine = reader.readLine();
		assertEquals("Test", readLine);
	}

}
