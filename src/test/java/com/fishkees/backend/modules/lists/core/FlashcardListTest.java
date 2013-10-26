package com.fishkees.backend.modules.lists.core;

import static com.fishkees.backend.modules.lists.core.FlashcardListTestBuilder.*;
import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fishkees.backend.modules.lists.FlashcardListFixtures;

public class FlashcardListTest {
	private final DateTime dateTime = new DateTime("1986-07-01T12:00Z");

	@Test
	public void should_serialize_to_expected_file() throws Exception {
		final String flashcardList = asJson(flashcardList());
		final String fixture = jsonFixture("fixtures/lists/single.json");
		assertEquals(flashcardList, fixture);
	}

	@Test
	public void should_deserialize_from_file_to_expected_object() throws Exception {
		FlashcardList fromJson = FlashcardListFixtures.single();
		final FlashcardList flashcardList = flashcardList();

		assertEquals(fromJson.getTitle(), flashcardList.getTitle());
		assertEquals(fromJson.getId(), flashcardList.getId());
		assertEquals(fromJson.getCreateDate(), flashcardList.getCreateDate());
	}

	@Test
	public void deserializeWithoutIdFromJson() throws Exception {
		final FlashcardList fromJson = FlashcardListFixtures.partial();
		final FlashcardList flashcardList = flashcardListWithoutId();

		assertNull(fromJson.getId());
		assertEquals(fromJson.getTitle(), flashcardList.getTitle());
		assertEquals(fromJson.getCreateDate(), flashcardList.getCreateDate());

	}

	private FlashcardList flashcardList() throws Exception {
		return newListWithId("someId1").withTitle("abcd")
				.withCreateDate(dateTime.toDate()).build();
	}

	private FlashcardList flashcardListWithoutId() throws Exception {
		return newListWithId(null).withTitle("abcd")
				.withCreateDate(dateTime.toDate()).build();
	}

}
