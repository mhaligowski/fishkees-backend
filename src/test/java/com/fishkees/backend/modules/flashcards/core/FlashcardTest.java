package com.fishkees.backend.modules.flashcards.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

public class FlashcardTest {
	private final DateTime dateTime = new DateTime("1986-07-01T12:00Z");

	@Test
	public void serializeToJson() throws Exception {
		// given
		final String expectedFromFile = jsonFixture("fixtures/flashcards/single.json");

		// when
		final String actualSerialized = asJson(flashcard());

		// then
		assertEquals(expectedFromFile, actualSerialized);
	}

	@Test
	public void deserializeFromJson() throws Exception {
		// given
		final Flashcard expected = flashcard();

		// when
		final Flashcard actualFromJson = FlashcardFixtures.single();

		// then
		assertEquals(expected.getId(), actualFromJson.getId());
		assertEquals(expected.getFlashcardListId(), actualFromJson.getFlashcardListId());
		assertEquals(expected.getBack(), actualFromJson.getBack());
		assertEquals(expected.getFront(), actualFromJson.getFront());
		assertEquals(expected.getCreateDate(), actualFromJson.getCreateDate());
		
	}

	private Flashcard flashcard() {
		return new Flashcard("someId", "flashcardListId", "front text",
				"back text", dateTime.toDate());
	}
}