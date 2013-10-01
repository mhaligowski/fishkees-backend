package com.fishkees.backend.configuration;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.json.ObjectMapperFactory;
import com.yammer.dropwizard.validation.Validator;

public class FishkeesConfigurationTest {

	@Test
	public void testConfigurationLoading() throws Exception {
		FishkeesConfiguration testObj = parseConfiguration("sampleConfiguration.yml");
		assertNotNull(testObj);
		assertEquals(12345, testObj.getHttpConfiguration().getPort());
		assertEquals(54321, testObj.getHttpConfiguration().getAdminPort());
		assertEquals("../fixtures/flashcardlists.json", testObj
				.getFixturesConfiguration().getFlashcardListsPath());
	}

	private FishkeesConfiguration parseConfiguration(String filename)
			throws Exception {
		final ObjectMapperFactory omf = new ObjectMapperFactory();
		final ConfigurationFactory<FishkeesConfiguration> configurationFactory = ConfigurationFactory
				.forClass(FishkeesConfiguration.class, new Validator(), omf);

		URL resource = this.getClass().getResource(filename);
		return configurationFactory.build(new File(resource.toURI()));
	}

}
