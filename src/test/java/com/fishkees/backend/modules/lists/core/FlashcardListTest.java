package com.fishkees.backend.modules.lists.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.fishkees.backend.modules.lists.FlashcardListFixtures;

public class FlashcardListTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy kk:mm");

	@Test
	public void serializesToJson() throws Exception {
		final FlashcardList flashcardList = flashcardList();
		assertThat("FlashcardList can be serialized to JSON",
				asJson(flashcardList),
				is(equalTo(jsonFixture("fixtures/lists/single.json"))));
	}

	@Test
	public void deserializesFromJson() throws Exception {
		FlashcardList fromJson = FlashcardListFixtures.single();
		final FlashcardList flashcardList = flashcardList();

		assertEquals(fromJson.getTitle(), flashcardList.getTitle());
		assertEquals(fromJson.getId(), flashcardList.getId());
	}
	
	private FlashcardList flashcardList() throws Exception {
		return new FlashcardList(1l, "abcd", sdf.parse("01/07/1986 12:00"));
	}
}
