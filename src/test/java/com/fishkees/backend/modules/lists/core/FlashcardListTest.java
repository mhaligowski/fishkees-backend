package com.fishkees.backend.modules.lists.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.SimpleTimeZone;

import org.junit.Test;

import com.fishkees.backend.modules.lists.FlashcardListFixtures;

public class FlashcardListTest {

	@Test
	public void serializesToJson() throws Exception {
		final FlashcardList flashcardList = flashcardList();
		assertEquals(jsonFixture("fixtures/lists/single.json"),
				asJson(flashcardList));
	}

	@Test
	public void deserializesFromJson() throws Exception {
		FlashcardList fromJson = FlashcardListFixtures.single();
		final FlashcardList flashcardList = flashcardList();

		assertEquals(fromJson.getTitle(), flashcardList.getTitle());
		assertEquals(fromJson.getId(), flashcardList.getId());
	}

	@Test
	public void deserializeWithoutIdFromJson() throws Exception {
		FlashcardList fromJson = FlashcardListFixtures.partial();
		final FlashcardList flashcardList = flashcardListWithoutId();

		assertNull(fromJson.getId());
		assertEquals(fromJson.getTitle(), flashcardList.getTitle());

	}

	private FlashcardList flashcardList() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.US);
		sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

		return new FlashcardList(1l, "abcd", sdf.parse("01/07/1986 12:00"));
	}

	private FlashcardList flashcardListWithoutId() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm", Locale.US);
		sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

		return new FlashcardList(null, "abcd", sdf.parse("01/07/1986 12:00"));
	}

}
