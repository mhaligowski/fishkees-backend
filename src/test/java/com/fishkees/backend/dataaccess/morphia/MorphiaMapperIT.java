package com.fishkees.backend.dataaccess.morphia;

import static org.junit.Assert.*;

import java.net.URL;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.configuration.ConfigurationParser;
import com.fishkees.backend.configuration.MongoConfiguration;
import com.fishkees.backend.dataaccess.morphia.entity.StubFishkeesEntity;
import com.fishkees.backend.dataaccess.morphia.entity.StubMorphiaEntity;
import com.google.common.base.Strings;
import com.google.inject.Guice;

public class MorphiaMapperIT {

	private Mapper testObj;

	@Before
	public void setUp() {
		MongoConfiguration mongoConfiguration = getMongoConfiguration();
		testObj = Guice.createInjector(new MorphiaModule(mongoConfiguration)).getInstance(Mapper.class);
		testObj.register(StubMorphiaEntity.class, StubFishkeesEntity.class);
	}

	@Test
	public void should_map_from_morphia_to_fishkees() {
		// given
		final String fieldValue = "someFieldValue";
		final String objectId = ObjectId.get().toString();
		StubMorphiaEntity morphiaEntity = getMorphiaEntity(objectId, fieldValue);

		// when
		StubFishkeesEntity result = testObj.map(morphiaEntity, StubFishkeesEntity.class);

		// then
		assertEquals(objectId, result.getId());
		assertEquals(fieldValue, result.getField());
	}

	@Test
	public void should_map_from_fishkees_to_morphia() {
		// given
		final String fieldValue = "someFieldValue";
		final String objectId = ObjectId.get().toString();
		StubFishkeesEntity morphiaEntity = getFishkeesEntity(objectId, fieldValue);

		// when
		StubMorphiaEntity result = testObj.map(morphiaEntity, StubMorphiaEntity.class);

		// then
		assertEquals(objectId, result.getId().toString());
		assertEquals(fieldValue, result.getField());
	}

	@Test
	public void should_map_object_id_to_string() {
		// given
		String expected = Strings.repeat("abcd", 6);
		ObjectId objectId = new ObjectId(expected);

		// when
		String actual = testObj.map(objectId);

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void should_map_string_to_objectId() {
		// given
		String value = Strings.repeat("abcd", 6);

		// when
		ObjectId actual = testObj.map(value);

		// then
		assertEquals(value, actual.toString());
	}

	private StubMorphiaEntity getMorphiaEntity(String objectId, String fieldValue) {
		StubMorphiaEntity entity1 = new StubMorphiaEntity();
		entity1.setId(new ObjectId(objectId));
		entity1.setField(fieldValue);

		return entity1;
	}

	private StubFishkeesEntity getFishkeesEntity(String objectId, String fieldValue) {
		StubFishkeesEntity entity1 = new StubFishkeesEntity();
		entity1.setId(objectId);
		entity1.setField(fieldValue);

		return entity1;
	}

	private MongoConfiguration getMongoConfiguration() {
		URL configurationFile = MorphiaModule.class.getResource("mongoConfiguration.yml");
		MongoConfiguration mongoConfiguration = new ConfigurationParser<>(MongoConfiguration.class)
				.parseConfiguration(configurationFile);
		return mongoConfiguration;
	}
}
