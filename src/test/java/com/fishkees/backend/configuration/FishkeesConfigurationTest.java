package com.fishkees.backend.configuration;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class FishkeesConfigurationTest {

	private final ConfigurationParser<FishkeesConfiguration> confParser = new ConfigurationParser<>(
			FishkeesConfiguration.class);

	private FishkeesConfiguration testObj;

	@Before
	public void setUp() {
		URL resource = this.getClass().getResource("sampleConfiguration.yml");
		testObj = confParser.parseConfiguration(resource);
	}

	@Test
	public void should_load_configuration_properly() throws Exception {
		assertNotNull(testObj);
		assertEquals(12345, testObj.getHttpConfiguration().getPort());
		assertEquals(54321, testObj.getHttpConfiguration().getAdminPort());
		assertEquals("../fixtures/flashcards.json", testObj
				.getFixturesConfiguration().getFlashcardsPath());
		assertEquals("../fixtures/flashcardlists.json", testObj
				.getFixturesConfiguration().getFlashcardListsPath());
	}

}
