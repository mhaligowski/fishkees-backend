package com.fishkees.backend.modules.lists.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Test;

public class FlashcardListTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy kk:mm");

	@Test
	public void serializesToJson() throws Exception {
		final FlashcardList flashcardList = new FlashcardList(1l, "abcd",
				sdf.parse("01/07/1986 12:00"));
		assertThat("FlashcardList can be serialized to JSON",
				asJson(flashcardList),
				is(equalTo(jsonFixture("fixtures/lists/single.json"))));
	}

	@Test
	public void deserializesFromJson() throws Exception {
		String json = jsonFixture("fixtures/lists/single.json");
		FlashcardList result = fromJson(json, FlashcardList.class);

		final FlashcardList flashcardList = new FlashcardList(1l, "abcd",
				sdf.parse("01/07/1986 12:00"));

		assertThat("FlashcardList can be deserialized from JSON",
				result.getTitle(), is(equalTo(flashcardList.getTitle())));
	}
}
