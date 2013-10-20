package com.fishkees.backend.modules.flashcards;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
