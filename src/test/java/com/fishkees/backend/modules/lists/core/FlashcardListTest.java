package com.fishkees.backend.modules.lists.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
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

	private FlashcardList flashcardList() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

		return new FlashcardList(1l, "abcd", sdf.parse("01/07/1986 12:00"));
	}
}
