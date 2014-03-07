package com.fishkees.backend.modules.lists;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.dataaccess.inmemory.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.inmemory.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FlashcardListsIntegrationTest {
	private static final String ID1 = "someNiceId1";
	private static final String ID2 = "someNiceId2";

	@Test
	public void should_return_objects_after_loading() {
		// when
		Injector testObj = Guice.createInjector(ListsModule.simpleModule());
		loadFixtures(testObj);
		FlashcardListResource resource = testObj
				.getInstance(FlashcardListResource.class);
		List<FlashcardList> flashcardLists = resource.findAll();

		assertEquals(2, flashcardLists.size());
		assertEquals(ID1, flashcardLists.get(0).getId());
		assertEquals(ID2, flashcardLists.get(1).getId());

		assertEquals("Russian for intermediate", flashcardLists.get(1)
				.getTitle());
		assertEquals("Spanish for beginners", flashcardLists.get(0).getTitle());
	}

	@Test
	public void should_return_the_same_storage_each_time() {
		Injector testObj = Guice.createInjector(ListsModule.simpleModule());
		KeyValueStore<String, FlashcardList> instance1 = testObj
				.getInstance(FlashcardListInMemoryStorage.class);
		KeyValueStore<String, FlashcardList> instance2 = testObj
				.getInstance(FlashcardListInMemoryStorage.class);

		assertSame(instance1, instance2);
	}

	@Test
	public void should_load_fixture_when_loading_storage_with_fixture() {
		// given
		FixturesConfiguration config = mock(FixturesConfiguration.class);
		when(config.getFlashcardListsPath()).thenReturn(
				"src/test/resources/fixtures/lists/all.json");
		Injector testObj = Guice.createInjector(ListsModule
				.moduleWithFixture(config));

		// when
		List<FlashcardList> actual = testObj.getInstance(
				FlashcardListInMemoryStorage.class).all();

		// then
		assertEquals(2, actual.size());
		assertEquals(ID1, actual.get(0).getId());
		assertEquals(ID2, actual.get(1).getId());

		assertEquals("Russian for intermediate", actual.get(1).getTitle());
		assertEquals("Spanish for beginners", actual.get(0).getTitle());

	}

	@Test(expected = RuntimeException.class)
	public void should_throw_exception_when_not_finding_fixture() {
		FixturesConfiguration config = mock(FixturesConfiguration.class);
		when(config.getFlashcardListsPath()).thenReturn("nonExistingFile");
		Injector testObj = Guice.createInjector(ListsModule
				.moduleWithFixture(config));

		// when
		testObj.getInstance(FlashcardListInMemoryStorage.class);
	}

	private void loadFixtures(Injector injector) {
		KeyValueStore<String, FlashcardList> storage = injector
				.getInstance(FlashcardListInMemoryStorage.class);

		for (FlashcardList fl : FlashcardListFixtures.all()) {
			storage.put(fl.getId().toString(), fl);
		}
	}
}
