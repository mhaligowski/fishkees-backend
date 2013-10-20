package com.fishkees.backend.modules.flashcards;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import com.fishkees.backend.configuration.FixturesConfiguration;
import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardInMemoryStorage;
import com.fishkees.backend.modules.lists.ListsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FlashcardsIntegrationTest {

	@Test
	public void testStorageIsSingleton() {
		// given
		Injector injector = Guice.createInjector(ListsModule.simpleModule(),
				FlashcardsModule.simpleModule());

		// when
		FlashcardInMemoryStorage instance1 = injector
				.getInstance(FlashcardInMemoryStorage.class);
		FlashcardInMemoryStorage instance2 = injector
				.getInstance(FlashcardInMemoryStorage.class);

		// then
		assertSame(instance1, instance2);
	}

	@Test
	public void testStorageFixtureLoading() {
		// given
		FixturesConfiguration config = mock(FixturesConfiguration.class);
		when(config.getFlashcardsPath()).thenReturn(
				"src/test/resources/fixtures/flashcards/all.json");
		Injector testObj = Guice.createInjector(ListsModule.simpleModule(),
				FlashcardsModule.moduleWithFixture(config));

		// when
		List<Flashcard> actual = testObj.getInstance(
				FlashcardInMemoryStorage.class).all();

		// then
		assertEquals(3, actual.size());
		assertEquals("someId3", actual.get(0).getId());
		assertEquals("someId1", actual.get(1).getId());
		assertEquals("someId2", actual.get(2).getId());
	}

}
