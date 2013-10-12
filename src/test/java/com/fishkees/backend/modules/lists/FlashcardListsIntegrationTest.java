package com.fishkees.backend.modules.lists;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FlashcardListsIntegrationTest {
	@Test
	public void testGettingAllLists() {
		// when
		Injector testObj = Guice.createInjector(ListsModule.simpleModule());
		loadFixtures(testObj);
		FlashcardListResource resource = testObj
				.getInstance(FlashcardListResource.class);
		List<FlashcardList> flashcardLists = resource.findAll();

		assertEquals(2, flashcardLists.size());
		assertEquals(new Long(2L), flashcardLists.get(0).getId());
		assertEquals(new Long(1L), flashcardLists.get(1).getId());

		assertEquals("Russian for intermediate", flashcardLists.get(0)
				.getTitle());
		assertEquals("Spanish for beginners", flashcardLists.get(1).getTitle());
	}

	@Test
	public void testStorageIsSingleton() {
		Injector testObj = Guice.createInjector(ListsModule.simpleModule());
		FlashcardListInMemoryStorage instance1 = testObj
				.getInstance(FlashcardListInMemoryStorage.class);
		FlashcardListInMemoryStorage instance2 = testObj
				.getInstance(FlashcardListInMemoryStorage.class);

		assertSame(instance1, instance2);
	}

	@Test
	public void testStorageFixtureLoading() {
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
		assertEquals(new Long(1L), actual.get(1).getId());
		assertEquals(new Long(2L), actual.get(0).getId());

		assertEquals("Russian for intermediate", actual.get(0).getTitle());
		assertEquals("Spanish for beginners", actual.get(1).getTitle());

	}

	@Test(expected = RuntimeException.class)
	public void testStorageFixtureLoading_nonExisting() {
		FixturesConfiguration config = mock(FixturesConfiguration.class);
		when(config.getFlashcardListsPath()).thenReturn("nonExistingFile");
		Injector testObj = Guice.createInjector(ListsModule
				.moduleWithFixture(config));

		// when
		testObj.getInstance(FlashcardListInMemoryStorage.class);
	}

	private void loadFixtures(Injector injector) {
		FlashcardListInMemoryStorage storage = injector
				.getInstance(FlashcardListInMemoryStorage.class);

		for (FlashcardList fl : FlashcardListFixtures.all()) {
			storage.put(fl.getId().toString(), fl);
		}
	}
}
