package com.fishkees.backend.modules.flashcards.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

public final class FlashcardFixtures {
	private FlashcardFixtures() {
	}

	public static Flashcard single() {
		TypeReference<Flashcard> ref = new TypeReference<Flashcard>() {
		};

		Flashcard result;
		try {
			result = fromJson(jsonFixture("fixtures/flashcards/single.json"),
					ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static List<Flashcard> all() {
		TypeReference<List<Flashcard>> ref = new TypeReference<List<Flashcard>>() {
		};
		List<Flashcard> result;

		try {
			result = fromJson(jsonFixture("fixtures/flashcards/all.json"), ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;

	}
}
