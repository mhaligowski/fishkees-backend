package com.fishkees.backend.modules.lists;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FlashcardListsIntegrationTest {
	private Injector testObj;

	@Before
	public void setUp() throws Exception {
		this.testObj = Guice.createInjector(new ListsModule());
		loadFixtures();
	}

	@Test
	public void testGettingAllLists() {
		// when
		FlashcardListResource resource = testObj
				.getInstance(FlashcardListResource.class);
		List<FlashcardList> flashcardLists = resource.getFlashcardLists();

		assertEquals(2, flashcardLists.size());
		assertEquals(new Long(1L), flashcardLists.get(0).getId());
		assertEquals(new Long(2L), flashcardLists.get(1).getId());

		assertEquals("Spanish for beginners", flashcardLists.get(0).getTitle());
		assertEquals("Russian for intermediate", flashcardLists.get(1)
				.getTitle());
	}

	@Test
	public void testStorageIsSingleton() {
		FlashcardListInMemoryStorage instance1 = testObj
				.getInstance(FlashcardListInMemoryStorage.class);
		FlashcardListInMemoryStorage instance2 = testObj
				.getInstance(FlashcardListInMemoryStorage.class);

		assertSame(instance1, instance2);
	}

	private void loadFixtures() {
		FlashcardListInMemoryStorage storage = testObj
				.getInstance(FlashcardListInMemoryStorage.class);

		for (FlashcardList fl : FlashcardListFixtures.all()) {
			storage.put(fl.getId(), fl);
		}
	}
}
